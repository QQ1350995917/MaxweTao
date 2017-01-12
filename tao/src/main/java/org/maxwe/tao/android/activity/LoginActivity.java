package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.LoginModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.main.MainActivity;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 15:33.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private static final int REQUEST_CODE_REGISTER = 0;
    private static final int REQUEST_CODE_LOST_PASSWORD = 1;
    public static final int RESPONSE_CODE_FAIL = 0;
    public static final int RESPONSE_CODE_SUCCESS = 1;
    @ViewInject(R.id.et_act_login_cellphone)
    private EditText et_act_login_cellphone;
    @ViewInject(R.id.et_act_login_password)
    private EditText et_act_login_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.et_act_login_cellphone.setText(SharedPreferencesUtils.getLastLoginCellphone(this));
    }

    @Event(value = R.id.bt_act_to_register, type = View.OnClickListener.class)
    private void onToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Event(value = R.id.bt_act_login_lost_password, type = View.OnClickListener.class)
    private void onToLostPassword(View view) {
        Intent intent = new Intent(this, LostActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_LOST_PASSWORD);
    }

    @Event(value = R.id.bt_act_login_action, type = View.OnClickListener.class)
    private void onLoginAction(View view) {
        String cellphone = et_act_login_cellphone.getText().toString();
        String password = et_act_login_password.getText().toString();
        if (TextUtils.isEmpty(cellphone) || !CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(this, this.getString(R.string.string_input_account_password), Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferencesUtils.saveLastLoginCellphone(this, cellphone);
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_login);
        LoginModel loginModel = new LoginModel(cellphone, password);
        loginModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        NetworkManager.requestByPost(url, loginModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                SessionModel responseModel = JSON.parseObject(result, SessionModel.class);
                onLoginSuccessCallback(responseModel);
            }

            @Override
            public void onAccessBad(String result) {
                super.onAccessBad(result);
                Toast.makeText(LoginActivity.this, R.string.string_toast_account_login_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LoginActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                if (resultCode == RESPONSE_CODE_SUCCESS) {
                    onLoginSuccessCallback((SessionModel) data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                }
                break;
            case REQUEST_CODE_LOST_PASSWORD:
                if (resultCode == RESPONSE_CODE_SUCCESS) {
                    onLoginSuccessCallback((SessionModel) data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                }
                break;
            default:
                break;
        }
    }

    private void onLoginSuccessCallback(SessionModel sessionModel) {
        SharedPreferencesUtils.saveSession(this, sessionModel);
        this.toMainActivity();
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
