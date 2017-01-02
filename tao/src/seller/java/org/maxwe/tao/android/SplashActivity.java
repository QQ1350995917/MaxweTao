package org.maxwe.tao.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.main.MainActivity;
import org.xutils.view.annotation.ContentView;

/**
 * Created by Pengwei Ding on 2016-12-30 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final int DELAY = 1000;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);
        if (TextUtils.isEmpty(key)) {
            this.handler.sendEmptyMessageDelayed(WHAT_0, DELAY);
        } else {
            this.handler.sendEmptyMessageDelayed(WHAT_1, DELAY);
        }

    }
}
