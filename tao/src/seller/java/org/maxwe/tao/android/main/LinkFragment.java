package org.maxwe.tao.android.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.AuthorActivity;
import org.maxwe.tao.android.activity.AuthorWebView;
import org.maxwe.tao.android.activity.BrandActivity;
import org.maxwe.tao.android.api.AuthorizeEntity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.goods.TaoPwdRequestModel;
import org.maxwe.tao.android.index.IndexFragmentGoodsDetailActivity;
import org.maxwe.tao.android.index.TaoPwdEntity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

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

    @ViewInject(R.id.bt_act_link_action)
    private Button bt_act_link_action;

    private TaoPwdEntity taoPwdEntity;

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
            this.et_frg_link_content.setText(resultString);
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


    @Event(value = R.id.bt_act_link_action, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        this.url = et_frg_link_content.getText().toString();
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
                        Intent intent = new Intent(LinkFragment.this.getContext(), AuthorActivity.class);
                        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                        LinkFragment.this.startActivityForResult(intent, LinkFragment.this.CODE_REQUEST_AUTHOR);
                    } else {
                        SharedPreferencesUtils.saveCurrentKeeperId(LinkFragment.this.getContext(), (String.valueOf(dataMap.get("shopKeeperId"))));
                        Position currentPP = SharedPreferencesUtils.getCurrentPP(LinkFragment.this.getContext());
                        if (currentPP == null || TextUtils.isEmpty(currentPP.getId()) || TextUtils.isEmpty(currentPP.getSiteId())) {
                            Toast.makeText(LinkFragment.this.getContext(), "请选择推广位", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LinkFragment.this.getContext(), BrandActivity.class);
                            LinkFragment.this.startActivityForResult(intent, LinkFragment.CODE_REQUEST_BRAND);
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
                requestTaoPwd();
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



    private void onResponseTaoPwdSuccess(String taoPwd) {
        this.bt_act_link_action.setClickable(true);
        this.taoPwdEntity = new TaoPwdEntity("淘宝送惊喜",
                "下单淘口令：" + taoPwd,
                "价格：元",
                "复制淘口令，打开手机淘宝即可下单");
        String text = this.taoPwdEntity.getTitle() + "\r\n"
                + this.taoPwdEntity.getPrice() + "\r\n"
                + this.taoPwdEntity.getPwd() + "\r\n"
                + this.taoPwdEntity.getDesc() + "\r\n";
        this.et_frg_link_content.setText(text);

        new ShareAction(LinkFragment.this.getActivity()).withTitle(this.taoPwdEntity.getPwd())
                .withText(this.taoPwdEntity.getTitle())
                .withTargetUrl(this.url)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }


    private void requestTaoPwd() {
        onRequestTaoPwd();
        String currentKeeperId = SharedPreferencesUtils.getCurrentKeeperId(this.getContext());
        Position currentPP = SharedPreferencesUtils.getCurrentPP(this.getContext());
        TaoPwdRequestModel taoPwdRequestModel = new TaoPwdRequestModel();

        taoPwdRequestModel.setText("淘宝送惊喜");
        taoPwdRequestModel.setUrl(this.et_frg_link_content.getText().toString() + "&pid=mm_" + currentKeeperId + "_" + currentPP.getSiteId() + "_" + currentPP.getId());
        taoPwdRequestModel.setUser_id(Long.parseLong(currentKeeperId));

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_tao_pwd);
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
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
                    SharedPreferencesUtils.clearSession(LinkFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(LinkFragment.this.getContext());
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
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
