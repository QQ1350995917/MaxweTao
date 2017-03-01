package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.android.support.ValidationCode;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.RegisterModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.meta.SMSModel;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 13:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @ViewInject(R.id.et_act_register_cellphone)
    private EditText et_act_register_cellphone;
    @ViewInject(R.id.et_act_register_cellphone_code_vcode)
    private EditText et_act_register_cellphone_code_vcode;
    @ViewInject(R.id.bt_act_cellphone_code_get_vcode)
    private ValidationCode bt_act_cellphone_code_get_vcode;
    @ViewInject(R.id.et_act_register_cellphone_code)
    private EditText et_act_register_cellphone_code;
    @ViewInject(R.id.et_act_register_password)
    private EditText et_act_register_password;
    @ViewInject(R.id.et_act_register_password_confirm)
    private EditText et_act_register_password_confirm;
    @ViewInject(R.id.bt_act_cellphone_code)
    private Button bt_act_cellphone_code;

    private int DELAY = 60;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what > 0) {
                RegisterActivity.this.bt_act_cellphone_code.setText(msg.what + RegisterActivity.this.getString(R.string.string_get_cellphone_code_delay));
            } else {
                RegisterActivity.this.bt_act_cellphone_code.setClickable(true);
                RegisterActivity.this.bt_act_cellphone_code.setText(RegisterActivity.this.getString(R.string.string_get_cellphone_code));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.bt_act_register_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(LoginActivity.RESPONSE_CODE_FAIL);
        this.finish();
    }


    @Event(value = R.id.bt_act_cellphone_code_get_vcode, type = View.OnClickListener.class)
    private void onGetVCodeAction(View view) {
        bt_act_cellphone_code_get_vcode.refresh();
    }

    @Event(value = R.id.bt_act_cellphone_code, type = View.OnClickListener.class)
    private void onCellphoneCodeAction(final View view) {
        String cellphone = et_act_register_cellphone.getText().toString();
        if (TextUtils.isEmpty(cellphone) || !CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bt_act_cellphone_code_get_vcode.isEqualsIgnoreCase(et_act_register_cellphone_code_vcode.getText().toString())){
            Toast.makeText(this, "图形验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_meta_smsCode);
        SMSModel smsModel = new SMSModel(cellphone);
        NetworkManager.requestByPost(url, smsModel, new NetworkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(RegisterActivity.this,R.string.string_toast_cellphone_code_send,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
        this.bt_act_cellphone_code.setClickable(false);
        final int delay = DELAY;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = delay; i >= 0; i--) {
                    handler.sendEmptyMessage(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Event(value = R.id.bt_act_register, type = View.OnClickListener.class)
    private void onRegisterAction(final View view) {
        String cellphone = et_act_register_cellphone.getText().toString();
        String cellphoneCode = et_act_register_cellphone_code.getText().toString();
        String password = et_act_register_password.getText().toString();
        String passwordConfirm = et_act_register_password_confirm.getText().toString();
        if (TextUtils.isEmpty(cellphone) || !CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(cellphoneCode)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone_code), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(this, this.getString(R.string.string_toast_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            Toast.makeText(this, this.getString(R.string.string_toast_password_confirm), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.equals(password, passwordConfirm)) {
            Toast.makeText(this, this.getString(R.string.string_toast_password_different), Toast.LENGTH_SHORT).show();
            return;
        }
        view.setClickable(false);
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_register);
        RegisterModel registerModel = new RegisterModel(cellphone,cellphoneCode,password);
        registerModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        NetworkManager.requestByPost(url, registerModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                TokenModel responseModel = JSON.parseObject(result, TokenModel.class);
                SharedPreferencesUtils.saveSession(RegisterActivity.this,responseModel);
                Intent intent = new Intent();
                intent.putExtra(Constants.KEY_INTENT_SESSION, responseModel);
                RegisterActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                RegisterActivity.this.finish();
                return;
            }

            @Override
            public void onRepeat(String result) {
                Toast.makeText(RegisterActivity.this, R.string.string_toast_cellphone_repeat, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                view.setClickable(true);
            }

            @Override
            public void onOther(int code, String result) {
                Toast.makeText(RegisterActivity.this, R.string.string_toast_register_error, Toast.LENGTH_SHORT).show();
                view.setClickable(true);
            }
        });
    }
}
