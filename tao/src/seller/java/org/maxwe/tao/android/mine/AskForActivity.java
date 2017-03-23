package org.maxwe.tao.android.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Pengwei Ding on 2017-03-23 23:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 申请佣金的理由页面
 */
@ContentView(R.layout.activity_ask_for)
public class AskForActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.bt_act_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }
}
