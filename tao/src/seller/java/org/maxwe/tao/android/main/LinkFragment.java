package org.maxwe.tao.android.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import org.maxwe.tao.android.activity.AuthorActivity;
import org.maxwe.tao.android.activity.AuthorWebView;
import org.maxwe.tao.android.activity.BrandActivity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.goods.TaoConvertRequestModel;
import org.maxwe.tao.android.goods.TaoConvertResponseModel;
import org.maxwe.tao.android.goods.TaoPwdRequestModel;
import org.maxwe.tao.android.index.TaoPwdEntity;
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

    @ViewInject(R.id.tv_frg_link_convert_short)
    private TextView tv_frg_link_convert_short;
    @ViewInject(R.id.tv_frg_link_convert_pwd)
    private TextView tv_frg_link_convert_pwd;
    @ViewInject(R.id.tv_frg_link_convert_qr)
    private SimpleDraweeView tv_frg_link_convert_qr;

    @ViewInject(R.id.et_frg_link_short_copy)
    private TextView et_frg_link_short_copy;
    @ViewInject(R.id.et_frg_link_pwd_copy)
    private TextView et_frg_link_pwd_copy;

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


    @Event(value = R.id.et_frg_link_short_copy, type = View.OnClickListener.class)
    private void onLinkShortCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tv_frg_link_convert_short.getText());
        Toast.makeText(this.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.et_frg_link_pwd_copy, type = View.OnClickListener.class)
    private void onLinkPwdCopyAction(View view) {
        ClipboardManager cm = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tv_frg_link_convert_pwd.getText());
        Toast.makeText(this.getContext(), "复制成功", Toast.LENGTH_SHORT).show();
    }


    @Event(value = R.id.bt_act_link_action, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        this.url = et_frg_link_content.getText().toString();
        Position currentPP = SharedPreferencesUtils.getCurrentPP(this.getContext());
        TaoConvertRequestModel taoConvertRequestModel = new TaoConvertRequestModel();

        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);

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
                    TaoConvertResponseModel taoConvertResponseModel = JSON.parseObject(result, TaoConvertResponseModel.class);
                    tv_frg_link_convert_short.setText(taoConvertResponseModel.getShortLinkUrl());
                    tv_frg_link_convert_pwd.setText(taoConvertResponseModel.getTaoToken());
                    tv_frg_link_convert_qr.setImageURI(taoConvertResponseModel.getQrCodeUrl());

                    et_frg_link_short_copy.setVisibility(View.VISIBLE);
                    et_frg_link_pwd_copy.setVisibility(View.VISIBLE);
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
                    Toast.makeText(LinkFragment.this.getContext(),"淘宝登录超时",Toast.LENGTH_SHORT).show();
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




//        Position currentPP = SharedPreferencesUtils.getCurrentPP(this.getContext());
//        RequestParams requestParams = new RequestParams("http://pub.alimama.com/urltrans/urltrans.json?" +
//                "siteid=" + currentPP.getSiteId() + "&" +
//                "adzoneid=" + currentPP.getId() + "&" +
//                "promotionURL=" + this.url + "&" +
//                "t=" + System.currentTimeMillis()+"&" +
//                "_input_charset=utf-8"
//        );
//        CookieManager cookieManager = CookieManager.getInstance();
//        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
//        requestParams.addHeader("Cookie", CookieStr);
//        requestParams.addHeader("Content-type","application/json");
//        requestParams.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
//        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Map rootMap = JSON.parseObject(result, Map.class);
//                Map<String, String> dataMap = (Map<String, String>) rootMap.get(AuthorWebView.KEY_DATA);
//                if (dataMap != null) {
//                    String shortLinkUrl = dataMap.get("shortLinkUrl").toString();
//                    String pwd = dataMap.get("taoToken").toString();
//                    String qrCodeUrl = dataMap.get("qrCodeUrl").toString();
//                    tv_frg_link_convert_short.setText(shortLinkUrl);
//                    tv_frg_link_convert_pwd.setText(pwd);
//                    tv_frg_link_convert_qr.setImageURI(Uri.parse(qrCodeUrl));
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });


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
//
//        RequestParams requestParams = new RequestParams(AuthorWebView.URL_LOGIN_MESSAGE);
//        CookieManager cookieManager = CookieManager.getInstance();
//        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
//        requestParams.addHeader("Cookie", CookieStr);
//        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Map rootMap = JSON.parseObject(result, Map.class);
//                Map<String, String> dataMap = (Map<String, String>) rootMap.get(AuthorWebView.KEY_DATA);
//                if (dataMap != null) {
//                    if (dataMap.containsKey(AuthorWebView.KEY_NO_LOGIN)) {
//                        Intent intent = new Intent(LinkFragment.this.getContext(), AuthorActivity.class);
//                        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
//                        LinkFragment.this.startActivityForResult(intent, LinkFragment.this.CODE_REQUEST_AUTHOR);
//                    } else {
//                        SharedPreferencesUtils.saveCurrentKeeperId(LinkFragment.this.getContext(), (String.valueOf(dataMap.get("shopKeeperId"))));
//                        Position currentPP = SharedPreferencesUtils.getCurrentPP(LinkFragment.this.getContext());
//                        if (currentPP == null || TextUtils.isEmpty(currentPP.getId()) || TextUtils.isEmpty(currentPP.getSiteId())) {
//                            Toast.makeText(LinkFragment.this.getContext(), "请选择推广位", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LinkFragment.this.getContext(), BrandActivity.class);
//                            LinkFragment.this.startActivityForResult(intent, LinkFragment.CODE_REQUEST_BRAND);
//                        } else {
//                            requestTaoPwd();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });