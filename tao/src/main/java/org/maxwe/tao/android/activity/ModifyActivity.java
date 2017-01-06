package org.maxwe.tao.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.agent.AgentEntity;
import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
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
    private void onModifyBackAction(View view){
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(LoginActivity.RESPONSE_CODE_FAIL);
        this.finish();
    }

    @Event(value = R.id.bt_act_modify, type = View.OnClickListener.class)
    private void onModifyAction(View view) {
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

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String cellphone = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);
        AgentEntity agentEntity = new AgentEntity(cellphone, null, this.getResources().getInteger(R.integer.type_id));
        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
        agentEntityInter.setT(key);
        agentEntityInter.setOrdPassword(oldPassword);
        agentEntityInter.setNewPassword(newPassword);
        NetworkManager.requestModifyPassword(agentEntityInter, new NetworkManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.T, response.getData());
                    ModifyActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
                    ModifyActivity.this.finish();
                    return;
                }
                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()){
                    Toast.makeText(ModifyActivity.this, R.string.string_toast_old_password_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
                    Toast.makeText(ModifyActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(ModifyActivity.this, R.string.string_toast_reset_password_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable exception, Object agentEntity) {
                Toast.makeText(ModifyActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
