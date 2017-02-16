package org.maxwe.tao.android.mine;

import android.content.Intent;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.AuthorActivity;
import org.maxwe.tao.android.activity.AuthorWebView;
import org.maxwe.tao.android.activity.BrandActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.main.MainActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-12-24 10:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {
    private static final int CODE_REQUEST_AUTHOR = 0;

    @Event(value = R.id.bt_frg_mine_promotion, type = View.OnClickListener.class)
    private void onPromotionAction(View view) {
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
                        Intent intent = new Intent(MineFragment.this.getContext(), AuthorActivity.class);
                        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                        MineFragment.this.startActivityForResult(intent, MineFragment.this.CODE_REQUEST_AUTHOR);
                        Toast.makeText(MineFragment.this.getContext(), "没有登录", Toast.LENGTH_SHORT).show();
                    } else if (dataMap.containsKey(AuthorWebView.KEY_TB_TOKEN_)) {
                        Intent intent = new Intent(MineFragment.this.getContext(), BrandActivity.class);
                        MineFragment.this.startActivity(intent);
                        SharedPreferencesUtils.saveCurrentKeeperId(MineFragment.this.getContext(), (String.valueOf(dataMap.get("shopKeeperId"))));
                    } else {
                        Toast.makeText(MineFragment.this.getContext(), "其他情况", Toast.LENGTH_SHORT).show();
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

    @Event(value = R.id.bt_frg_mine_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this.getActivity(), ModifyActivity.class);
        this.getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_frg_mine_newbie, type = View.OnClickListener.class)
    private void onNewbieAction(View view) {
        Toast.makeText(MineFragment.this.getActivity(), "教程开发中，敬请关注...", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_frg_mine_about_us, type = View.OnClickListener.class)
    private void onAboutUsAction(View view) {
        Toast.makeText(MineFragment.this.getActivity(), "开发中，敬请关注...", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_frg_mine_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {
        try {
            SessionModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
            sessionModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPost(url, sessionModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_REQUEST_AUTHOR:
                if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                    onPromotionAction(null);
                }
                break;
            default:
                break;
        }

    }
}
