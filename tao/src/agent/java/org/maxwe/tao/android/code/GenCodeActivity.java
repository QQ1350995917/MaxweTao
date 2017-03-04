package org.maxwe.tao.android.code;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.trade.GrantRequestModel;
import org.maxwe.tao.android.trade.GrantResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-11 17:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_gen_code)
public class GenCodeActivity extends BaseActivity {
    public static final int RESULT_CODE_GEN_ACT_CODE_OK = 10;
    public static final int RESULT_CODE_GEN_ACT_CODE_ERROR = 11;

    @ViewInject(R.id.et_act_gen_code_password)
    private EditText et_act_gen_code_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Event(value = R.id.et_act_gen_code_cancel, type = View.OnClickListener.class)
    private void onGenCodeCancelAction(View view) {
        Intent intent = new Intent();
        this.setResult(RESULT_CODE_GEN_ACT_CODE_ERROR, intent);
        this.finish();
    }

    private void resetPasswordInput(){
        this.et_act_gen_code_password.setText(null);
    }


    @Event(value = R.id.et_act_gen_code_confirm, type = View.OnClickListener.class)
    private void onGenCodeConfirmAction(final View view) {
        String password = this.et_act_gen_code_password.getText().toString();
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 12){
            Toast.makeText(GenCodeActivity.this,R.string.string_input_account_password,Toast.LENGTH_SHORT).show();
            return;
        }
        view.setClickable(false);
        try {
            TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
            GrantRequestModel tradeModel = new GrantRequestModel(sessionModel);
            tradeModel.setAuthenticatePassword(password);
            tradeModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_trade_grant);
            NetworkManager.requestByPost(url, tradeModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    Intent intent = new Intent();
                    GrantResponseModel tradeModel = JSON.parseObject(result, GrantResponseModel.class);
                    intent.putExtra(Constants.KEY_INTENT_SESSION, tradeModel);
                    GenCodeActivity.this.setResult(RESULT_CODE_GEN_ACT_CODE_OK, intent);
                    GenCodeActivity.this.finish();
                }

                @Override
                public void onParamsError(String result) {
                    Toast.makeText(GenCodeActivity.this,R.string.string_toast_password_different,Toast.LENGTH_SHORT).show();
                    resetPasswordInput();
                    view.setClickable(true);
                }

                @Override
                public void onAccessBad(String result) {
                    Toast.makeText(GenCodeActivity.this,R.string.string_gen_act_code_forbidden,Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(GenCodeActivity.this);
                    Intent intent = new Intent(GenCodeActivity.this, LoginActivity.class);
                    GenCodeActivity.this.startActivity(intent);
                    GenCodeActivity.this.finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Intent intent = new Intent(GenCodeActivity.this, LoginActivity.class);
                    GenCodeActivity.this.startActivity(intent);
                    GenCodeActivity.this.finish();
                }

                @Override
                public void onOther(int code, String result) {
                    if (code == IResponse.ResultCode.RC_ACCESS_BAD_2.getCode()){
                        Toast.makeText(GenCodeActivity.this,R.string.string_agent_code_no_enough,Toast.LENGTH_SHORT).show();
                    }
                    view.setClickable(true);
                }
            });
        } catch (Exception e) {
            view.setClickable(true);
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }


}
