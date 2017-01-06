package org.maxwe.tao.android.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.agent.AgentEntity;
import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.agent.AgentManager;
import org.maxwe.tao.android.response.Response;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_PROXY = 0;
    private static final int REQUEST_CODE_TRADE = 1;
    private static final int REQUEST_CODE_MODIFY_PASSWORD = 3;

    @Event(value = R.id.bt_act_main_my_proxy, type = View.OnClickListener.class)
    private void onMyProxyAction(View view) {
        Intent intent = new Intent(this, GrantActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_PROXY);
    }

    @Event(value = R.id.bt_act_main_proxy_code_trade, type = View.OnClickListener.class)
    private void onAccessCodeTradeAction(View view) {
        Intent intent = new Intent(this, org.maxwe.tao.android.main.TradeActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_TRADE);
    }

    @Event(value = R.id.bt_act_main_modify_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        this.startActivityForResult(intent,REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_act_main_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {
        final SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        AgentEntityInter agentEntityInter = new AgentEntityInter();
        agentEntityInter.setT(sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT,null));
        AgentManager.requestLogout(agentEntityInter, new AgentManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                SharedPreferences.Editor remove = edit.remove(Constants.KEY_SHARD_T_CONTENT);
                remove.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }

            @Override
            public void onError(Throwable exception, AgentEntity agentEntity) {
                Toast.makeText(MainActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor edit = sharedPreferences.edit();
                SharedPreferences.Editor remove = edit.remove(Constants.KEY_SHARD_T_CONTENT);
                remove.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PROXY:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_TRADE:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_MODIFY_PASSWORD:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onModifyPasswordSuccessCallback(data.getStringExtra(Constants.T));
                }
                break;
            default:
                break;
        }
    }

    private void onModifyPasswordSuccessCallback(String token){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.KEY_SHARD_T_CONTENT, token);
        edit.commit();
    }
}
