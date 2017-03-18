package org.maxwe.tao.android.index;

import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.common.BrandActivity;
import org.maxwe.tao.android.goods.alimama.AuctionEntity;
import org.maxwe.tao.android.goods.alimama.AuctionRequestModel;
import org.maxwe.tao.android.goods.alimama.AuctionResponseModel;
import org.maxwe.tao.android.goods.alimama.GoodsEntity;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.maxwe.tao.android.utils.StringUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

/**
 * Created by Pengwei Ding on 2017-02-12 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_goods_detail)
public class GoodsDetailActivity extends BaseActivity {
    public static final String KEY_GOODS = "goodsEntity";
    private static final int CODE_REQUEST_AUTHOR = 0;
    private static final int CODE_REQUEST_BRAND = 1;
    @ViewInject(R.id.sv_act_goods_detail_container)
    private ScrollView sv_act_goods_detail_container;
    @ViewInject(R.id.iv_act_goods_detail_image)
    private SimpleDraweeView iv_act_goods_detail_image;
    @ViewInject(R.id.tv_act_goods_detail_title)
    private TextView tv_act_goods_detail_title;
    @ViewInject(R.id.tv_act_goods_detail_nick)
    private TextView tv_act_goods_detail_nick;
    @ViewInject(R.id.tv_act_goods_detail_brokerage)
    private TextView tv_act_goods_detail_brokerage;
    @ViewInject(R.id.tv_act_goods_detail_brokerage_got)
    private TextView tv_act_goods_detail_brokerage_got;

    @ViewInject(R.id.iv_act_goods_detail_coupon_info)
    private TextView iv_act_goods_detail_coupon_info;
    @ViewInject(R.id.iv_act_goods_detail_coupon_price)
    private TextView iv_act_goods_detail_coupon_price;
    @ViewInject(R.id.iv_act_goods_detail_coupon_price_label)
    private TextView iv_act_goods_detail_coupon_price_label;

    @ViewInject(R.id.tv_act_goods_detail_final_price)
    private TextView tv_act_goods_detail_final_price;
    @ViewInject(R.id.tv_act_goods_detail_price)
    private TextView tv_act_goods_detail_price;
    @ViewInject(R.id.tv_act_goods_detail_sale)
    private TextView tv_act_goods_detail_sale;

    @ViewInject(R.id.tv_act_goods_detail_coupon_counter)
    private TextView tv_act_goods_detail_coupon_counter;

    @ViewInject(R.id.tv_act_goods_detail_coupon_get)
    private TextView tv_act_goods_detail_coupon_get;

    @ViewInject(R.id.tv_act_goods_detail_coupon_left)
    private TextView tv_act_goods_detail_coupon_left;

    @ViewInject(R.id.tv_act_goods_detail_coupon_time_line)
    private TextView tv_act_goods_detail_coupon_time_line;

    @ViewInject(R.id.bt_act_goods_detail_get_link)
    private Button bt_act_goods_detail_get_link;
    @ViewInject(R.id.tv_act_goods_detail_get_link_result)
    private TextView tv_act_goods_detail_get_link_result;
    @ViewInject(R.id.ll_act_goods_detail_space_holder)
    private LinearLayout ll_act_goods_detail_space_holder;
    @ViewInject(R.id.ll_act_goods_detail_action_holder)
    private LinearLayout ll_act_goods_detail_action_holder;

    @ViewInject(R.id.ll_act_goods_detail_share_panel)
    private LinearLayout ll_act_goods_detail_share_panel;
    @ViewInject(R.id.iv_act_goods_detail_share_weibo)

    private GoodsEntity goodsEntity = null;
    private AuctionEntity convertEntity;

    private Tencent instance;
    private IUiListener shareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Toast.makeText(GoodsDetailActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(GoodsDetailActivity.this, " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(GoodsDetailActivity.this, " 分享取消啦", Toast.LENGTH_SHORT).show();
        }
    };

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.instance = Tencent.createInstance(SellerApplication.SHARE_QQ_KEY, this);
        this.iwxapi = WXAPIFactory.createWXAPI(this, SellerApplication.SHARE_WEI_XIN_KEY);
        this.iwxapi.registerApp(SellerApplication.SHARE_WEI_XIN_KEY);

        this.goodsEntity = (GoodsEntity) this.getIntent().getExtras().get(KEY_GOODS);
        this.iv_act_goods_detail_image.setImageURI(Uri.parse(goodsEntity.getPictUrl()));
        this.tv_act_goods_detail_title.setText(goodsEntity.getTitle());
        this.tv_act_goods_detail_nick.setText(goodsEntity.getNick());
        this.tv_act_goods_detail_brokerage.setText(goodsEntity.getHightestBrokage() + "%");
        this.tv_act_goods_detail_brokerage_got.setText("赚" + new DecimalFormat("###.00").format((goodsEntity.getZkPrice() - goodsEntity.getCouponAmount()) * goodsEntity.getHightestBrokage() / 100) + "元");
        if (goodsEntity.getCouponAmount() > 0) {
            iv_act_goods_detail_coupon_info.setVisibility(View.VISIBLE);
            iv_act_goods_detail_coupon_info.setText(goodsEntity.getCouponInfo());
            iv_act_goods_detail_coupon_price.setVisibility(View.VISIBLE);
            iv_act_goods_detail_coupon_price.setText(goodsEntity.getCouponAmount() + "元 ");
            iv_act_goods_detail_coupon_price_label.setVisibility(View.VISIBLE);
        }
        this.tv_act_goods_detail_final_price.setText("￥" + (goodsEntity.getZkPrice() - goodsEntity.getCouponAmount()));
        this.tv_act_goods_detail_price.setText("￥" + (goodsEntity.getZkPrice()));
        this.tv_act_goods_detail_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        this.tv_act_goods_detail_sale.setText("月销:" + goodsEntity.getBiz30day());
        this.tv_act_goods_detail_coupon_counter.setText(goodsEntity.getCouponTotalCount() + "");
        this.tv_act_goods_detail_coupon_get.setText((goodsEntity.getCouponTotalCount() - goodsEntity.getCouponLeftCount()) + "");
        this.tv_act_goods_detail_coupon_left.setText(goodsEntity.getCouponLeftCount() + "");
        this.tv_act_goods_detail_coupon_time_line.setText(goodsEntity.getCouponEffectiveEndTime());

        saveBitmapFromUrl(goodsEntity.getPictUrl());
    }

    @Event(value = R.id.bt_act_goods_detail_get_link, type = View.OnClickListener.class)
    private void onTaoPwdAction(View view) {
        AuthorActivity.requestTaoLoginStatus(this, new AuthorActivity.TaoLoginStatusCallback() {
            @Override
            public void onNeedLoginCallback() {
                Intent intent = new Intent(GoodsDetailActivity.this, AuthorActivity.class);
                intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                GoodsDetailActivity.this.startActivityForResult(intent, GoodsDetailActivity.this.CODE_REQUEST_AUTHOR);
            }

            @Override
            public void onNeedBrandCallback() {
                Toast.makeText(GoodsDetailActivity.this, "请选择推广位", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GoodsDetailActivity.this, BrandActivity.class);
                GoodsDetailActivity.this.startActivityForResult(intent, GoodsDetailActivity.this.CODE_REQUEST_BRAND);
            }

            @Override
            public void onNeedOkCallback() {
                requestTaoPwd();
            }

            @Override
            public void onNeedErrorCallback() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.CODE_REQUEST_AUTHOR) {
            if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                onTaoPwdAction(null);
            } else {
                Toast.makeText(GoodsDetailActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.CODE_REQUEST_BRAND) {
            if (resultCode == BrandActivity.CODE_RESULT_SUCCESS) {
                requestTaoPwd();
            } else if (resultCode == BrandActivity.CODE_RESULT_FAIL) {
                Toast.makeText(GoodsDetailActivity.this, "您尚未请选择推广位,请您选择推广位", Toast.LENGTH_SHORT).show();
            }
        }
        this.instance.onActivityResultData(requestCode, resultCode, data, shareListener);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Event(value = R.id.bt_act_goods_detail_back, type = View.OnClickListener.class)
    private void onModifyBackAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    private void onRequestTaoPwd() {
        this.bt_act_goods_detail_get_link.setClickable(false);
    }

    private void onResponseTaoPwdError() {
        this.bt_act_goods_detail_get_link.setClickable(true);
    }

    /**
     * 长按复制淘口令
     *
     * @param view
     * @return
     */
    @Event(value = R.id.tv_act_goods_detail_get_link_result, type = View.OnLongClickListener.class)
    private boolean onLinkPwdCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (view.getTag() != null) {
            cm.setText(tv_act_goods_detail_get_link_result.getTag().toString());
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * 复制全部文案
     *
     * @param view
     * @return
     */
    @Event(value = R.id.bt_act_goods_detail_copy, type = View.OnClickListener.class)
    private void onCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tv_act_goods_detail_get_link_result.getText());
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();    //为Intent设置Action属性
        ComponentName componentName = new ComponentName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
        intent.setComponent(componentName);
        startActivity(intent);
    }


    /**
     * 显示分享面板
     *
     * @param view
     * @return
     */
    @Event(value = R.id.bt_act_goods_detail_share, type = View.OnClickListener.class)
    private void onShowSharePanelAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(this.tv_act_goods_detail_get_link_result.getText());
        this.ll_act_goods_detail_share_panel.setVisibility(View.VISIBLE);

    }

    @Event(value = R.id.tv_act_goods_detail_share_cancel, type = View.OnClickListener.class)
    private void onDismissSharePanelAction(View view) {
        this.ll_act_goods_detail_share_panel.setVisibility(View.GONE);
    }

    @Event(value = R.id.iv_act_goods_detail_share_weibo, type = View.OnClickListener.class)
    private void onShareWeibo(View view) {

    }

    /**
     * 微信
     *
     * @param view
     */
    @Event(value = R.id.iv_act_goods_detail_share_weixin, type = View.OnClickListener.class)
    private void onShareWeixin(View view) {
        shareToWeiChat(false);
    }

    /**
     * 微信朋友圈
     *
     * @param view
     */
    @Event(value = R.id.iv_act_goods_detail_share_weixin_zone, type = View.OnClickListener.class)
    private void onShareWeixinZone(View view) {
        shareToWeiChat(true);
    }

    /**
     * QQ
     *
     * @param view
     */
    @Event(value = R.id.iv_act_goods_detail_share_qq, type = View.OnClickListener.class)
    private void onShareQQ(View view) {
        shareToQQ(false);
    }

    /**
     * QQ空间
     *
     * @param view
     */
    @Event(value = R.id.iv_act_goods_detail_share_qq_zone, type = View.OnClickListener.class)
    private void onShareQQZone(View view) {
        shareToQQ(true);
    }


    private void onResponseTaoConvertSuccess(AuctionEntity aliConvertEntity) {
        this.bt_act_goods_detail_get_link.setClickable(false);
        this.bt_act_goods_detail_get_link.setVisibility(View.GONE);
        this.tv_act_goods_detail_get_link_result.setVisibility(View.VISIBLE);
        this.ll_act_goods_detail_space_holder.setVisibility(View.VISIBLE);
        this.ll_act_goods_detail_action_holder.setVisibility(View.VISIBLE);
        if (aliConvertEntity != null) {
            this.convertEntity = aliConvertEntity;
            String copyText =
                    BaseActivity.getEMOJIStringByUnicode(0x270C) + goodsEntity.getTitle() + "\r\n" +
                            "【领券】" + this.goodsEntity.getCouponAmount() + "\r\n" +
                            "【价格】￥" + (goodsEntity.getZkPrice() - goodsEntity.getCouponAmount()) + "\r\n" +
                            BaseActivity.getEMOJIStringByUnicode(0x1F446) + "长按复制后打开" +
                            BaseActivity.getEMOJIStringByUnicode(0x1F4F1) + "淘宝" +
                            BaseActivity.getEMOJIStringByUnicode(0x1F449) +
                            (TextUtils.isEmpty(aliConvertEntity.getCouponLinkTaoToken()) ?
                                    aliConvertEntity.getTaoToken() : aliConvertEntity.getCouponLinkTaoToken()) +
                            BaseActivity.getEMOJIStringByUnicode(0x1F448) + "\r\n";
            tv_act_goods_detail_get_link_result.setText(copyText);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    sv_act_goods_detail_container.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            if (TextUtils.isEmpty(aliConvertEntity.getCouponLinkTaoToken())) {
                tv_act_goods_detail_get_link_result.setTag(aliConvertEntity.getTaoToken());
            } else {
                tv_act_goods_detail_get_link_result.setTag(aliConvertEntity.getCouponLinkTaoToken());
            }
        } else {
            tv_act_goods_detail_get_link_result.setText("该链接不被支持或已经下架");
        }
    }

    /**
     * 申请转链
     */
    private void requestTaoPwd() {
        onRequestTaoPwd();

        Position currentPP = SharedPreferencesUtils.getCurrentPP(this);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);

        AuctionRequestModel aliConvertRequestModel = new AuctionRequestModel();

        aliConvertRequestModel.setCookie(cookie);
        aliConvertRequestModel.setSiteid(Long.parseLong(currentPP.getSiteId()));
        aliConvertRequestModel.setAdzoneid(Long.parseLong(currentPP.getId()));
        aliConvertRequestModel.setAuctionid(goodsEntity.getAuctionId());

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_ali_goods_convert);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
        aliConvertRequestModel.setT(sessionModel.getT());
        aliConvertRequestModel.setId(sessionModel.getId());
        aliConvertRequestModel.setCellphone(sessionModel.getCellphone());
        aliConvertRequestModel.setApt(sessionModel.getApt());
        try {
            aliConvertRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, aliConvertRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    AuctionResponseModel responseModel = JSON.parseObject(result, AuctionResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        onResponseTaoConvertSuccess(responseModel.getAuction());
                    }
                    Toast.makeText(GoodsDetailActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(GoodsDetailActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onResponseTaoPwdError();
                }
            });
        } catch (Exception e) {
            onResponseTaoPwdError();
            Toast.makeText(GoodsDetailActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void shareToQQ(boolean isQQZone) {
        final Bundle params = new Bundle();
        if (isQQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        }

        params.putString(QQShare.SHARE_TO_QQ_TITLE, this.goodsEntity.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, tv_act_goods_detail_get_link_result.getText().toString());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, this.goodsEntity.getAuctionUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, this.goodsEntity.getPictUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "淘妈咪");
        if (isQQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        instance.shareToQQ(this, params, shareListener);
    }

    //http://www.cnblogs.com/juji-baleite/p/5812745.html
    public void shareToWeiChat(boolean isWeiChatZone) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;//不要透明通道
        options.inPremultiplied = false;//不允许透明度
        Bitmap sourceBitmap = BitmapFactory.decodeFile(GoodsDetailActivity.this.getFilesDir() + File.separator + "cache.jpg",options);
        WXImageObject wxImageObject = new WXImageObject(sourceBitmap);
        wxImageObject.setImagePath(GoodsDetailActivity.this.getFilesDir() + File.separator + "cache.jpg");
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxImageObject);
//        Toast.makeText(this,(wxImageObject.imageData.length / 1024) + "K",Toast.LENGTH_SHORT).show();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = wxMediaMessage;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = isWeiChatZone ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);

    }


    public static void getFrescoCacheBitmap(final Handler handler, Uri uri, Context context) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    handler.sendEmptyMessage(0);
                    return;
                }
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                handler.sendEmptyMessage(0);
            }
        }, CallerThreadExecutor.getInstance());
    }

    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bitmap.copyPixelsToBuffer(buf);
        byte[] byteArray = buf.array();
        return byteArray;
    }

    private void saveBitmapFromUrl(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    URL pictureUrl = new URL(url);
                    InputStream in = pictureUrl.openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    String pictureName = GoodsDetailActivity.this.getFilesDir() + File.separator + "cache.jpg";
                    File file = new File(pictureName);
                    FileOutputStream out;
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,85, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
