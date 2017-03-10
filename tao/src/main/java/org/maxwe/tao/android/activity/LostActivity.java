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
import org.maxwe.tao.android.account.model.AccountLostRequestModel;
import org.maxwe.tao.android.account.model.AccountLostResponseModel;
import org.maxwe.tao.android.meta.SMSCodeRequestModel;
import org.maxwe.tao.android.meta.SMSCodeResponseModel;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 15:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_lost)
public class LostActivity extends BaseActivity {
    @ViewInject(R.id.et_act_lost_cellphone)
    private EditText et_act_lost_cellphone;
    @ViewInject(R.id.et_act_lost_cellphone_code_vcode)
    private EditText et_act_lost_cellphone_code_vcode;
    @ViewInject(R.id.bt_act_cellphone_code_get_vcode)
    private ValidationCode bt_act_cellphone_code_get_vcode;
    @ViewInject(R.id.et_act_lost_cellphone_code)
    private EditText et_act_lost_cellphone_code;
    @ViewInject(R.id.et_act_lost_password)
    private EditText et_act_lost_password;
    @ViewInject(R.id.et_act_lost_password_confirm)
    private EditText et_act_lost_password_confirm;
    @ViewInject(R.id.bt_act_cellphone_code)
    private Button bt_act_cellphone_code;

    private int DELAY = 60;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what > 0) {
                LostActivity.this.bt_act_cellphone_code.setText(msg.what + LostActivity.this.getString(R.string.string_get_cellphone_code_delay));
            } else {
                LostActivity.this.bt_act_cellphone_code.setClickable(true);
                LostActivity.this.bt_act_cellphone_code.setText(LostActivity.this.getString(R.string.string_get_cellphone_code));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.bt_act_lost_back, type = View.OnClickListener.class)
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
    private void onGetVCode(View view) {
        bt_act_cellphone_code_get_vcode.refresh();
    }

    @Event(value = R.id.bt_act_cellphone_code, type = View.OnClickListener.class)
    private void onCellphoneCodeAction(final View view) {
        String cellphone = et_act_lost_cellphone.getText().toString();
        if (TextUtils.isEmpty(cellphone) || !CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bt_act_cellphone_code_get_vcode.isEqualsIgnoreCase(et_act_lost_cellphone_code_vcode.getText().toString())) {
            Toast.makeText(this, "图形验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_meta_smsCode);
        SMSCodeRequestModel smsModel = new SMSCodeRequestModel(cellphone, this.getResources().getInteger(R.integer.integer_app_type));
        NetworkManager.requestByPostNew(url, smsModel, new NetworkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                SMSCodeResponseModel responseModel = JSON.parseObject(result, SMSCodeResponseModel.class);
                Toast.makeText(LostActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LostActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
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

    @Event(value = R.id.bt_act_lost, type = View.OnClickListener.class)
    private void onLostAction(final View view) {
        String cellphone = et_act_lost_cellphone.getText().toString();
        String cellphoneCode = et_act_lost_cellphone_code.getText().toString();
        String password = et_act_lost_password.getText().toString();
        String passwordConfirm = et_act_lost_password_confirm.getText().toString();
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
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_lost);
        AccountLostRequestModel registerModel = new AccountLostRequestModel(cellphone, cellphoneCode, password, this.getResources().getInteger(R.integer.integer_app_type));
        NetworkManager.requestByPostNew(url, registerModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                AccountLostResponseModel responseModel = JSON.parseObject(result, AccountLostResponseModel.class);
                if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                    SharedPreferencesUtils.saveSession(LostActivity.this, responseModel.getToken());
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_INTENT_SESSION, responseModel);
                    LostActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                    LostActivity.this.finish();
                }
                Toast.makeText(LostActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                view.setClickable(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LostActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                view.setClickable(true);
            }
        });
    }
}
