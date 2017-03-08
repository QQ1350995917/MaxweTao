package org.maxwe.tao.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.AccountSignOutRequestModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.account.user.ActiveModel;
import org.maxwe.tao.android.account.user.UserActiveRequestModel;
import org.maxwe.tao.android.account.user.UserActiveResponseModel;
import org.maxwe.tao.android.account.user.UserEntity;
import org.maxwe.tao.android.account.user.UserMineRequestModel;
import org.maxwe.tao.android.account.user.UserMineResponseModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-03 20:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 验证激活信息的界面
 */
@ContentView(R.layout.activity_access)
public class AccessActivity extends BaseActivity {
    //标题以及提示信息
    @ViewInject(R.id.tv_act_access_title)
    private TextView tv_act_access_title;
    //请求数据界面
    @ViewInject(R.id.pb_act_access_progress)
    private ProgressBar pb_act_access_progress;
    //登录超时界面
    @ViewInject(R.id.bt_act_access_goto_login)
    private Button bt_act_access_goto_login;
    //输入激活码界面
    @ViewInject(R.id.ll_act_access_status_accessing)
    private LinearLayout ll_act_access_status_accessing;
    //输入激活码界面的输入框
    @ViewInject(R.id.et_act_access_act_code)
    private EditText et_act_access_act_code;
    //输入密码
    @ViewInject(R.id.et_act_access_act_password)
    private EditText et_act_access_act_password;

    private UserEntity userEntity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        this.onRequestMyInfo();
    }

    private void onRequestingView() {
        this.tv_act_access_title.setText(R.string.string_waiting);
        this.pb_act_access_progress.setVisibility(View.VISIBLE);
        this.bt_act_access_goto_login.setVisibility(View.GONE);
        this.ll_act_access_status_accessing.setVisibility(View.GONE);
    }

    private void onResponseLoginTimeout() {
        this.tv_act_access_title.setText(R.string.string_toast_timeout);
        this.pb_act_access_progress.setVisibility(View.GONE);
        this.bt_act_access_goto_login.setVisibility(View.VISIBLE);
        this.ll_act_access_status_accessing.setVisibility(View.GONE);
    }

    private void onResponseWaitingAct(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.onResponseReAct(AccessActivity.this.getString(R.string.string_accessing));
    }

    private void onResponseReAct(String text) {
        this.tv_act_access_title.setText(text);
        this.pb_act_access_progress.setVisibility(View.GONE);
        this.bt_act_access_goto_login.setVisibility(View.GONE);
        this.ll_act_access_status_accessing.setVisibility(View.VISIBLE);
    }

    private void onResponseUserSuccess(UserEntity userEntity) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_INTENT_SESSION, userEntity);
        AccessActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
        AccessActivity.this.finish();
    }

    private void onResponseActSuccess(long actTime) {
        this.userEntity.setActTime(actTime);
        onResponseUserSuccess(this.userEntity);
    }

    private void onRequestMyInfo() {
        try {
            onRequestingView();
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_mine);
            TokenModel session = SharedPreferencesUtils.getSession(this);
            UserMineRequestModel requestModel = new UserMineRequestModel(session);
            requestModel.setSign(session.getEncryptSing());
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    UserMineResponseModel requestModel = JSON.parseObject(result, UserMineResponseModel.class);
                    if (requestModel.getCode() == ResponseModel.RC_SUCCESS) {
                        if (requestModel.getUser().getActCode() == null) {
                            onResponseWaitingAct(requestModel.getUser());
                        } else {
                            onResponseUserSuccess(requestModel.getUser());
                        }
                    } else if (requestModel.getCode() == ResponseModel.RC_TIMEOUT){
                        SharedPreferencesUtils.clearSession(AccessActivity.this);
                        SharedPreferencesUtils.clearAuthor(AccessActivity.this);
                    }
                    Toast.makeText(AccessActivity.this, requestModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(AccessActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onResponseReAct(AccessActivity.this.getString(R.string.string_toast_network_error));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_access_action, type = View.OnClickListener.class)
    private void onRequestActive(View view) {
        String actCode = et_act_access_act_code.getText().toString();
        if (TextUtils.isEmpty(actCode) || actCode.length() != 8) {
            Toast.makeText(AccessActivity.this, R.string.string_input_your_act_code, Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_act_access_act_password.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 12) {
            Toast.makeText(AccessActivity.this, R.string.string_input_account_password, Toast.LENGTH_SHORT).show();
            return;
        }

        onRequestingView();

        try {
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_active);
            TokenModel session = SharedPreferencesUtils.getSession(this);
            UserActiveRequestModel requestModel = new UserActiveRequestModel(session,password, actCode);
            requestModel.setSign(session.getEncryptSing());
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    UserActiveResponseModel responseModel = JSON.parseObject(result, UserActiveResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS){
                        onResponseActSuccess(responseModel.getActTime());
                    }
                    Toast.makeText(AccessActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(AccessActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onResponseReAct(AccessActivity.this.getString(R.string.string_toast_network_error));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_access_logout, type = View.OnClickListener.class)
    private void onLogoutAction(View view) {
        try {
            TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
            AccountSignOutRequestModel requestModel = new AccountSignOutRequestModel(sessionModel);
            requestModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(AccessActivity.this);
                    SharedPreferencesUtils.clearAuthor(AccessActivity.this);
                    onReopenLaterAction(null);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(AccessActivity.this);
                    SharedPreferencesUtils.clearAuthor(AccessActivity.this);
                    onReopenLaterAction(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_access_goto_login, type = View.OnClickListener.class)
    private void onReLoginAction(View view) {
        AccessActivity.this.setResult(MainActivity.REQUEST_CODE_LOGIN_TIME_OUT);
        AccessActivity.this.finish();
    }

    @Event(value = R.id.bt_act_access_later, type = View.OnClickListener.class)
    private void onReopenLaterAction(View view) {
        AccessActivity.this.setResult(LoginActivity.RESPONSE_CODE_FAIL);
        AccessActivity.this.finish();
    }
}