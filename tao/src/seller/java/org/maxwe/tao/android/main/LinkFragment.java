package org.maxwe.tao.android.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.author.AuthorActivity;
import org.maxwe.tao.android.author.BrandActivity;
import org.maxwe.tao.android.goods.GoodsConvertResponseModel;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.maxwe.tao.android.goods.TaoConvertRequestModel;
import org.maxwe.tao.android.goods.TaoConvertResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-02 15:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_link)
public class LinkFragment extends BaseFragment {
    private static final int CODE_REQUEST_AUTHOR = 0;
    private static final int CODE_REQUEST_BRAND = 1;

    @ViewInject(R.id.et_frg_link_content)
    private EditText et_frg_link_content;

    @ViewInject(R.id.tv_frg_link_convert_result)
    private Button tv_frg_link_convert_result;
    @ViewInject(R.id.ll_frg_link_result_action)
    private LinearLayout ll_frg_link_result_action;
    @ViewInject(R.id.tv_frg_link_convert_qr)
    private SimpleDraweeView tv_frg_link_convert_qr;
    @ViewInject(R.id.bt_frg_link_convert_copy)
    private Button bt_frg_link_convert_copy;
    @ViewInject(R.id.bt_frg_link_convert_share)
    private Button bt_frg_link_convert_share;

    @ViewInject(R.id.bt_act_link_action)
    private Button bt_act_link_action;

    private String url = null;

    @Event(value = R.id.et_frg_link_paste, type = View.OnClickListener.class)
    private void paste(View view) {
        ClipboardManager systemService = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (!systemService.hasPrimaryClip()) {
            Toast.makeText(this.getContext(), "粘贴板为空", Toast.LENGTH_SHORT).show();
        } else {
            ClipData clipData = systemService.getPrimaryClip();
            int count = clipData.getItemCount();
            String resultString = "";
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(this.getContext());
                resultString += str;
            }
            if (resultString.startsWith("http")) {
                this.et_frg_link_content.setText(resultString);
            } else {
                Toast.makeText(this.getContext(),"您粘贴板上的内容不符合转链要求",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Event(value = R.id.tv_frg_link_convert_result, type = View.OnLongClickListener.class)
    private boolean onLinkPwdCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (view.getTag() != null) {
            cm.setText(tv_frg_link_convert_result.getTag().toString());
            Toast.makeText(this.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Event(value = R.id.bt_frg_link_convert_copy, type = View.OnClickListener.class)
    private void onLinkAllCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tv_frg_link_convert_result.getText());
        Toast.makeText(this.getContext(), "复制成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_VIEW);    //为Intent设置Action属性
        intent.setData(Uri.parse(this.url)); //为Intent设置DATA属性
        startActivity(intent);
    }

    @Event(value = R.id.bt_frg_link_convert_share, type = View.OnClickListener.class)
    private void onLinkShareAction(View view) {
        new ShareAction(LinkFragment.this.getActivity()).withTitle(BaseActivity.getEMOJIStringByUnicode(0x1F4E7))
                .withText(tv_frg_link_convert_result.getText().toString())
                .withTargetUrl(this.url)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }


    @Event(value = R.id.bt_act_link_action, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        this.url = et_frg_link_content.getText().toString();
        if (TextUtils.isEmpty(this.url)) {
            Toast.makeText(LinkFragment.this.getContext(), "请输入要转换的链接", Toast.LENGTH_SHORT).show();
        } else {
            AuthorActivity.requestTaoLoginStatus(this.getContext(), new AuthorActivity.TaoLoginStatusCallback() {
                @Override
                public void onNeedLoginCallback() {
                    Intent intent = new Intent(LinkFragment.this.getContext(), AuthorActivity.class);
                    intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                    LinkFragment.this.startActivityForResult(intent, LinkFragment.this.CODE_REQUEST_AUTHOR);
                }

                @Override
                public void onNeedBrandCallback() {
                    Intent intent = new Intent(LinkFragment.this.getContext(), BrandActivity.class);
                    LinkFragment.this.startActivityForResult(intent, LinkFragment.CODE_REQUEST_BRAND);
                }

                @Override
                public void onNeedOkCallback() {
                    requestTaoConvert();
                }

                @Override
                public void onNeedErrorCallback() {

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.CODE_REQUEST_AUTHOR) {
            if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                onLinkConvertAction(null);
            } else {
                Toast.makeText(this.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.CODE_REQUEST_BRAND) {
            if (resultCode == BrandActivity.CODE_RESULT_SUCCESS) {
                requestTaoConvert();
            } else if (resultCode == BrandActivity.CODE_RESULT_FAIL) {
                Toast.makeText(this.getContext(), "您尚未请选择推广位,申请失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onRequestTaoPwd() {
        this.bt_act_link_action.setClickable(false);
    }

    private void onResponseTaoPwdError() {
        this.bt_act_link_action.setClickable(true);
    }

    private void onResponseTaoConvertSuccess(GoodsConvertResponseModel goodsConvertResponseModel) {
        this.bt_act_link_action.setClickable(true);
        if (goodsConvertResponseModel != null) {
            TaoConvertResponseModel convert = goodsConvertResponseModel.getConvert();
            GoodsEntity goodsEntity = goodsConvertResponseModel.getGoodsEntity();
            if (convert != null && goodsEntity != null) {
                if (!TextUtils.isEmpty(convert.getShortLinkUrl()) &&
                        !TextUtils.isEmpty(convert.getTaoToken())) {
                    tv_frg_link_convert_result.setVisibility(View.VISIBLE);
                    ll_frg_link_result_action.setVisibility(View.VISIBLE);
                    String copyText =
                            BaseActivity.getEMOJIStringByUnicode(0x270C) + goodsEntity.getTitle() + "\r\n" +
                                    "【价格】￥" + goodsEntity.getZk_final_price() + "\r\n" +
                                    BaseActivity.getEMOJIStringByUnicode(0x1F446) + "长按复制后打开" +
                                    BaseActivity.getEMOJIStringByUnicode(0x1F4F1) + "淘宝" +
                                    BaseActivity.getEMOJIStringByUnicode(0x1F449) +
                                    convert.getTaoToken() +
                                    BaseActivity.getEMOJIStringByUnicode(0x1F448) + "\r\n";
                    tv_frg_link_convert_result.setText(copyText);
                    tv_frg_link_convert_result.setTag(convert.getTaoToken());
                } else {
                    tv_frg_link_convert_result.setText("该链接不被支持或已经下架");
                }
            } else {
                tv_frg_link_convert_result.setText("该链接不被支持或已经下架");
            }
        } else {
            tv_frg_link_convert_result.setText("该链接不被支持或已经下架");
        }
    }


    private void requestTaoConvert() {
        Position currentPP = SharedPreferencesUtils.getCurrentPP(this.getContext());
        TaoConvertRequestModel taoConvertRequestModel = new TaoConvertRequestModel();

        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);

        taoConvertRequestModel.setCookie(cookie);
        taoConvertRequestModel.setSiteid(currentPP.getSiteId());
        taoConvertRequestModel.setAdzoneid(currentPP.getId());
        taoConvertRequestModel.setPromotionURL(this.url);

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_tao_convert);
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
        taoConvertRequestModel.setT(sessionModel.getT());
        taoConvertRequestModel.setMark(sessionModel.getMark());
        taoConvertRequestModel.setCellphone(sessionModel.getCellphone());
        taoConvertRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        try {
            taoConvertRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPost(url, taoConvertRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    GoodsConvertResponseModel goodsConvertResponseModel = JSON.parseObject(result, GoodsConvertResponseModel.class);
                    onResponseTaoConvertSuccess(goodsConvertResponseModel);
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(LinkFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(LinkFragment.this.getContext());
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmptyResult(String result) {
                    super.onEmptyResult(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onResponseTaoPwdError();
                }

                @Override
                public void onOther(int code, String result) {
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_reset_password_error, Toast.LENGTH_SHORT).show();
                    onResponseTaoPwdError();
                }
            });
        } catch (Exception e) {
            onResponseTaoPwdError();
            Toast.makeText(this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
