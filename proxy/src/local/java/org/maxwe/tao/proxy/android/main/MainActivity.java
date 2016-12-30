package org.maxwe.tao.proxy.android.main;

import android.content.Intent;
import android.view.View;

import org.maxwe.tao.proxy.android.BaseActivity;
import org.maxwe.tao.proxy.android.R;
import org.maxwe.tao.proxy.android.mine.ModifyActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_PROXY = 0;
    private static final int REQUEST_CODE_TRADE = 1;
    private static final int REQUEST_CODE_MODIFY_PASSWORD = 3;

    @Event(value = R.id.bt_act_main_my_proxy, type = View.OnClickListener.class)
    private void onMyProxyAction(View view) {
        Intent intent = new Intent(this, AgentActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_PROXY);
    }

    @Event(value = R.id.bt_act_main_proxy_code_trade, type = View.OnClickListener.class)
    private void onAccessCodeTradeAction(View view) {
        Intent intent = new Intent(this, TradeActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_TRADE);
    }

    @Event(value = R.id.bt_act_main_modify_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_act_main_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
