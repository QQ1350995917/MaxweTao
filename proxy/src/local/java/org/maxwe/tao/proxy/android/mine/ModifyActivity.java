package org.maxwe.tao.proxy.android.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.maxwe.tao.proxy.android.BaseActivity;
import org.maxwe.tao.proxy.android.R;
import org.maxwe.tao.proxy.android.utils.CellPhoneUtils;
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
    }

}
