package org.maxwe.tao.android.main;


import android.view.View;
import android.widget.LinearLayout;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2016-12-30 17:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_trade)
public class TradeActivity extends BaseActivity {

    @ViewInject(R.id.ll_act_trade_search_result_list)
    private LinearLayout ll_act_trade_search_result_list;

    @Event(value = R.id.bt_act_trade_back, type = View.OnClickListener.class)
    private void onBackToMainAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Event(value = R.id.bt_act_trade_search_action, type = View.OnClickListener.class)
    private void onSearchAction(View view) {
        this.ll_act_trade_search_result_list.setVisibility(View.VISIBLE);
    }
}
