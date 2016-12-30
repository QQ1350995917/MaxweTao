package org.maxwe.tao.proxy.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.maxwe.tao.proxy.android.main.AgentActivity;
import org.maxwe.tao.proxy.android.main.MainActivity;

/**
 * Created by Pengwei Ding on 2016-12-30 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SplashActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent(this, LoginActivity.class);
//        Intent intent = new Intent(this, LostActivity.class);
//        Intent intent = new Intent(this, ModifyActivity.class);
//        Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, AgentActivity.class);
        this.startActivity(intent);
    }
}
