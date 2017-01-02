package org.maxwe.tao.android.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Pengwei Ding on 2016-12-30 17:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_agent)
public class AgentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = R.id.bt_act_agent_back, type = View.OnClickListener.class)
    public void onBackToMainAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
