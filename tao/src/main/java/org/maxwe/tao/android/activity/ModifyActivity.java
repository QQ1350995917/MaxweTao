package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.account.model.ModifyModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_modify)
public class ModifyActivity extends BaseActivity {
    @ViewInject(R.id.et_act_modify_old_password)
    private EditText et_act_modify_old_password;
    @ViewInject(R.id.et_act_modify_new_password)
    private EditText et_act_modify_new_password;
    @ViewInject(R.id.et_act_modify_new_password_confirm)
    private EditText et_act_modify_new_password_confirm;

    @Event(value = R.id.bt_act_modify_back, type = View.OnClickListener.class)
    private void onModifyBackAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(LoginActivity.RESPONSE_CODE_FAIL);
        this.finish();
    }

    @Event(value = R.id.bt_act_modify, type = View.OnClickListener.class)
    private void onModifyAction(final View view) {
        String oldPassword = et_act_modify_old_password.getText().toString();
        String newPassword = et_act_modify_new_password.getText().toString();
        String newPasswordConfirm = et_act_modify_new_password_confirm.getText().toString();

        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(this, this.getString(R.string.string_input_account_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, this.getString(R.string.string_input_new_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(newPasswordConfirm)) {
            Toast.makeText(this, this.getString(R.string.string_input_new_password_confirm), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.equals(newPassword, newPasswordConfirm)) {
            Toast.makeText(this, this.getString(R.string.string_toast_password_different), Toast.LENGTH_SHORT).show();
            return;
        }

        view.setClickable(false);
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_password);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
        ModifyModel modifyModel = new ModifyModel(sessionModel, oldPassword, newPassword);
        modifyModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        try {
            modifyModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPost(url, modifyModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    TokenModel responseModel = JSON.parseObject(result, TokenModel.class);
                    SharedPreferencesUtils.saveSession(ModifyActivity.this,responseModel);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.KEY_INTENT_SESSION, responseModel);
                    ModifyActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                    ModifyActivity.this.finish();
                }

                @Override
                public void onAccessBad(String result) {
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_old_password_error, Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(ModifyActivity.this);
                    SharedPreferencesUtils.clearAuthor(ModifyActivity.this);
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }

                @Override
                public void onOther(int code, String result) {
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_reset_password_error, Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            view.setClickable(true);
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

}
