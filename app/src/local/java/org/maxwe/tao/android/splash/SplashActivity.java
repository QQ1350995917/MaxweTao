package org.maxwe.tao.android.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.main.MainActivity;

/**
 * Created by Pengwei Ding on 2016-12-23 10:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (isLogin()) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        } else {
//            Intent intent = new Intent(this, LoginActivity.class);
//            Intent intent = new Intent(this, RegisterActivity.class);
//            Intent intent = new Intent(this, PasswordActivity.class);
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }

    private boolean isLogin() {
        return false;
    }
}
