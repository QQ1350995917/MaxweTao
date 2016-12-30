package org.maxwe.tao.proxy.android.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.maxwe.tao.proxy.android.BaseActivity;
import org.maxwe.tao.proxy.android.R;
import org.maxwe.tao.proxy.android.main.MainActivity;
import org.maxwe.tao.proxy.android.utils.CellPhoneUtils;
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
    public static final int RESPONSE_CODE_SUCCESS = 0;
    @ViewInject(R.id.et_act_login_cellphone)
    private EditText et_act_login_cellphone;
    @ViewInject(R.id.et_act_login_password)
    private EditText et_act_login_password;


    @Event(value = R.id.bt_act_to_register, type = View.OnClickListener.class)
    private void onToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Event(value = R.id.bt_act_login_lost_password, type = View.OnClickListener.class)
    private void onToLostPassword(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
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

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, this.getString(R.string.string_input_account_password), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_REGISTER:
                if (resultCode == RESPONSE_CODE_SUCCESS) {
                    onRegisterSuccessCallback();
                }
                break;
            case REQUEST_CODE_LOST_PASSWORD:
                if (resultCode == RESPONSE_CODE_SUCCESS) {
                    onLostPasswordSuccessCallback();
                }
                break;
            default:
                break;
        }
    }

    private void onRegisterSuccessCallback() {
        this.toMainActivity();
    }

    private void onLostPasswordSuccessCallback() {
        this.toMainActivity();
    }

    private void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
