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

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.account.model.RegisterModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.meta.SMSModel;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.maxwe.tao.android.R;
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
    @ViewInject(R.id.et_act_lost_cellphone_code)
    private EditText et_act_lost_cellphone_code;
    @ViewInject(R.id.et_act_lost_password)
    private EditText et_act_lost_password;
    @ViewInject(R.id.et_act_lost_password_confirm)
    private EditText et_act_lost_password_confirm;
    @ViewInject(R.id.bt_act_cellphone_code)
    private Button bt_act_cellphone_code;

    private String cellphoneOfGetCode = null;
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

    @Event(value = R.id.bt_act_cellphone_code, type = View.OnClickListener.class)
    private void onCellphoneCodeAction(View view) {
        String cellphone = et_act_lost_cellphone.getText().toString();
        if (TextUtils.isEmpty(cellphone) || !CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone), Toast.LENGTH_SHORT).show();
            return;
        }

        this.cellphoneOfGetCode = cellphone;
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_meta_smsCode);
        SMSModel smsModel = new SMSModel(this.cellphoneOfGetCode);
        NetworkManager.requestByPost(url, smsModel, new NetworkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(LostActivity.this,R.string.string_toast_cellphone_code_send,Toast.LENGTH_SHORT).show();
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
    private void onLostAction(View view) {
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

        if (!TextUtils.equals(cellphone, cellphoneOfGetCode)) {
            Toast.makeText(this, this.getString(R.string.string_toast_cellphone_different), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)  || password.length() < 6) {
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


        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_lost);
        RegisterModel registerModel = new RegisterModel(this.cellphoneOfGetCode,cellphoneCode,password);
        NetworkManager.requestByPost(url, registerModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                SessionModel responseModel = JSON.parseObject(result, SessionModel.class);
                Intent intent = new Intent();
                intent.putExtra(Constants.KEY_INTENT_SESSION, responseModel);
                LostActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                LostActivity.this.finish();
                return;
            }

            @Override
            public void onAccessBad(String result) {
                Toast.makeText(LostActivity.this, R.string.string_toast_cellphone_no, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LostActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOther(int code, String result) {
                Toast.makeText(LostActivity.this, R.string.string_toast_reset_password_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
