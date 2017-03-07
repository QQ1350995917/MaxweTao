package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.AccountModifyRequestModel;
import org.maxwe.tao.android.account.model.AccountModifyResponseModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.response.ResponseModel;
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
        AccountModifyRequestModel modifyModel = new AccountModifyRequestModel(sessionModel, oldPassword, newPassword);
        try {
            modifyModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, modifyModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    AccountModifyResponseModel responseModel = JSON.parseObject(result, AccountModifyResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        SharedPreferencesUtils.saveSession(ModifyActivity.this, responseModel.getToken());
                        Intent intent = new Intent();
                        intent.putExtra(Constants.KEY_INTENT_SESSION, responseModel);
                        ModifyActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                        ModifyActivity.this.finish();
                    }
                    Toast.makeText(ModifyActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
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
