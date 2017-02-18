package org.maxwe.tao.android.index;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.AuthorActivity;
import org.maxwe.tao.android.activity.AuthorWebView;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.BrandActivity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.maxwe.tao.android.goods.TaoPwdRequestModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-12 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_goods_detail)
public class IndexFragmentGoodsDetailActivity extends BaseActivity {
    public static final String KEY_GOODS = "goodsEntity";
    private static final int CODE_REQUEST_AUTHOR = 0;
    private static final int CODE_REQUEST_BRAND = 1;
    @ViewInject(R.id.iv_act_goods_detail_image)
    private SimpleDraweeView iv_act_goods_detail_image;
    @ViewInject(R.id.tv_act_goods_detail_title)
    private TextView tv_act_goods_detail_title;
    @ViewInject(R.id.tv_act_goods_detail_brokerage)
    private TextView tv_act_goods_detail_brokerage;
    @ViewInject(R.id.tv_act_goods_detail_brokerage_got)
    private TextView tv_act_goods_detail_brokerage_got;
    @ViewInject(R.id.tv_act_goods_detail_final_price)
    private TextView tv_act_goods_detail_final_price;
    @ViewInject(R.id.tv_act_goods_detail_sale)
    private TextView tv_act_goods_detail_sale;
    @ViewInject(R.id.bt_act_goods_detail_get_link)
    private Button bt_act_goods_detail_get_link;
    @ViewInject(R.id.tv_act_goods_detail_get_link_result)
    private TextView tv_act_goods_detail_get_link_result;
    @ViewInject(R.id.ll_act_goods_detail_space_holder)
    private LinearLayout ll_act_goods_detail_space_holder;
    @ViewInject(R.id.ll_act_goods_detail_action_holder)
    private LinearLayout ll_act_goods_detail_action_holder;

    private GoodsEntity goodsEntity = null;
    private TaoPwdEntity taoPwdEntity = null;


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(IndexFragmentGoodsDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(IndexFragmentGoodsDetailActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(IndexFragmentGoodsDetailActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.goodsEntity = (GoodsEntity) this.getIntent().getExtras().get(KEY_GOODS);
        this.iv_act_goods_detail_image.setImageURI(Uri.parse(goodsEntity.getPict_url()));
        this.tv_act_goods_detail_title.setText(goodsEntity.getTitle());
        this.tv_act_goods_detail_brokerage.setText("18%");
        this.tv_act_goods_detail_brokerage_got.setText(" 赚" + new DecimalFormat("###.00").format(Float.parseFloat(goodsEntity.getZk_final_price()) * 18 / 100) + "元");
        this.tv_act_goods_detail_final_price.setText("￥" + goodsEntity.getZk_final_price());
        this.tv_act_goods_detail_sale.setText("月销:" + goodsEntity.getVolume());
    }

    @Event(value = R.id.bt_act_goods_detail_get_link, type = View.OnClickListener.class)
    private void onModifyGetLinkAction(View view) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_LOGIN_MESSAGE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, String> dataMap = (Map<String, String>) rootMap.get(AuthorWebView.KEY_DATA);
                if (dataMap != null) {
                    if (dataMap.containsKey(AuthorWebView.KEY_NO_LOGIN)) {
                        Intent intent = new Intent(IndexFragmentGoodsDetailActivity.this, AuthorActivity.class);
                        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                        IndexFragmentGoodsDetailActivity.this.startActivityForResult(intent, IndexFragmentGoodsDetailActivity.this.CODE_REQUEST_AUTHOR);
                    } else {
                        SharedPreferencesUtils.saveCurrentKeeperId(IndexFragmentGoodsDetailActivity.this, (String.valueOf(dataMap.get("shopKeeperId"))));
                        Position currentPP = SharedPreferencesUtils.getCurrentPP(IndexFragmentGoodsDetailActivity.this);
                        if (currentPP == null || TextUtils.isEmpty(currentPP.getId()) || TextUtils.isEmpty(currentPP.getSiteId())) {
                            Toast.makeText(IndexFragmentGoodsDetailActivity.this, "请选择推广位", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(IndexFragmentGoodsDetailActivity.this, BrandActivity.class);
                            IndexFragmentGoodsDetailActivity.this.startActivityForResult(intent, IndexFragmentGoodsDetailActivity.this.CODE_REQUEST_BRAND);
                        } else {
                            requestTaoPwd();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.CODE_REQUEST_AUTHOR) {
            if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                onModifyGetLinkAction(null);
            } else {
                Toast.makeText(IndexFragmentGoodsDetailActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.CODE_REQUEST_BRAND) {
            if (resultCode == BrandActivity.CODE_RESULT_SUCCESS) {
                requestTaoPwd();
            } else if (resultCode == BrandActivity.CODE_RESULT_FAIL) {
                Toast.makeText(IndexFragmentGoodsDetailActivity.this, "您尚未请选择推广位,申请失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Event(value = R.id.bt_act_goods_detail_copy, type = View.OnClickListener.class)
    private void onCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(this.tv_act_goods_detail_get_link_result.getText());
        Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
    }

    @Event(value = R.id.bt_act_goods_detail_share, type = View.OnClickListener.class)
    private void onShareAction(View view) {
        new ShareAction(IndexFragmentGoodsDetailActivity.this).withTitle(this.taoPwdEntity.getPwd())
                .withText(this.taoPwdEntity.getTitle())
                .withTargetUrl(this.goodsEntity.getItem_url())
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
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

    private void onResponseTaoPwdSuccess(String taoPwd) {
        this.bt_act_goods_detail_get_link.setClickable(false);
        this.bt_act_goods_detail_get_link.setVisibility(View.GONE);
        this.tv_act_goods_detail_get_link_result.setVisibility(View.VISIBLE);
        this.ll_act_goods_detail_space_holder.setVisibility(View.VISIBLE);
        this.ll_act_goods_detail_action_holder.setVisibility(View.VISIBLE);
        this.taoPwdEntity = new TaoPwdEntity(IndexFragmentGoodsDetailActivity.this.goodsEntity.getTitle(),
                "下单淘口令：" + taoPwd,
                "价格：" + goodsEntity.getZk_final_price() + "元",
                "复制淘口令，打开手机淘宝即可下单");
        String text = this.taoPwdEntity.getTitle() + "\r\n"
                + this.taoPwdEntity.getPrice() + "\r\n"
                + this.taoPwdEntity.getPwd() + "\r\n"
                + this.taoPwdEntity.getDesc() + "\r\n";
        this.tv_act_goods_detail_get_link_result.setText(text);
    }

    private void requestTaoPwd() {
        onRequestTaoPwd();
        String currentKeeperId = SharedPreferencesUtils.getCurrentKeeperId(this);
        Position currentPP = SharedPreferencesUtils.getCurrentPP(this);
        TaoPwdRequestModel taoPwdRequestModel = new TaoPwdRequestModel();

        taoPwdRequestModel.setText(goodsEntity.getTitle());
        taoPwdRequestModel.setUrl(goodsEntity.getItem_url() + "&pid=mm_" + currentKeeperId + "_" + currentPP.getSiteId() + "_" + currentPP.getId());
        taoPwdRequestModel.setUser_id(Long.parseLong(currentKeeperId));

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_tao_pwd);
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this);
        taoPwdRequestModel.setT(sessionModel.getT());
        taoPwdRequestModel.setMark(sessionModel.getMark());
        taoPwdRequestModel.setCellphone(sessionModel.getCellphone());
        taoPwdRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        try {
            taoPwdRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPost(url, taoPwdRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    onResponseTaoPwdSuccess(result);
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(IndexFragmentGoodsDetailActivity.this);
                    SharedPreferencesUtils.clearAuthor(IndexFragmentGoodsDetailActivity.this);
                    Toast.makeText(IndexFragmentGoodsDetailActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(IndexFragmentGoodsDetailActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onResponseTaoPwdError();
                }

                @Override
                public void onOther(int code, String result) {
                    Toast.makeText(IndexFragmentGoodsDetailActivity.this, R.string.string_toast_reset_password_error, Toast.LENGTH_SHORT).show();
                    onResponseTaoPwdError();
                }
            });
        } catch (Exception e) {
            onResponseTaoPwdError();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        Bitmap bitmap = null;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
