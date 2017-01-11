package org.maxwe.tao.android.code;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Pengwei Ding on 2017-01-11 17:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_gen_code)
public class GenCodeActivity extends BaseActivity {
    public static final int RESULT_CODE_GEN_ACT_CODE_OK = 10;
    public static final int RESULT_CODE_GEN_ACT_CODE_ERROR = 11;

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


    @Event(value = R.id.et_act_gen_code_confirm, type = View.OnClickListener.class)
    private void onGenCodeConfirmAction(View view) {
        try {
            SessionModel sessionModel = SharedPreferencesUtils.getSession(this);
            sessionModel.setSign(sessionModel.getEncryptSing());

            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_trade_grant);
            NetworkManager.requestByPost(url, null, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(GenCodeActivity.this);
                    Intent intent = new Intent(GenCodeActivity.this, LoginActivity.class);
                    GenCodeActivity.this.startActivity(intent);
                    GenCodeActivity.this.finish();
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
                    SharedPreferencesUtils.clearSession(GenCodeActivity.this);
                    Intent intent = new Intent(GenCodeActivity.this, LoginActivity.class);
                    GenCodeActivity.this.startActivity(intent);
                    GenCodeActivity.this.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }


}
