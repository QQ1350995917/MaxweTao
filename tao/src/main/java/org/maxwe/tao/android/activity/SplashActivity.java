package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.main.MainActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final int DELAY = 2000;
    private static final int WHAT_0 = 0;
    private static final int WHAT_1 = 1;
    private static final int WHAT_2 = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_0) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
                return;
            }

            if (msg.what == WHAT_1) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
                return;
            }

            if (msg.what == WHAT_2) {
                return;
            }
        }
    };

    @ViewInject(R.id.tv_act_splash_version)
    private TextView tv_act_splash_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TokenModel session = SharedPreferencesUtils.getSession(this);
        if (session == null || session.getId() == 0) {
            SharedPreferencesUtils.clearAuthor(this);
            SharedPreferencesUtils.clearSession(this);
            this.handler.sendEmptyMessageDelayed(WHAT_0, DELAY);
        } else {
            this.handler.sendEmptyMessageDelayed(WHAT_1, DELAY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.tv_act_splash_version.setText(this.getVersionName());
    }
}
