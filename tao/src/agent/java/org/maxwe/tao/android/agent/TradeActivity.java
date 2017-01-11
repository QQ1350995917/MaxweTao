package org.maxwe.tao.android.agent;


import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.regex.Pattern;

/**
 * Created by Pengwei Ding on 2016-12-30 17:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_trade)
public class TradeActivity extends BaseActivity {


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


//        if (TextUtils.equals(cellphone, grantAccount)) {
//            Toast.makeText(this, R.string.string_grant_self, Toast.LENGTH_SHORT).show();
//            return;
//        }


//        AgentEntity agentEntity = new AgentEntity(grantAccount, null, this.getResources().getInteger(R.integer.type_id));
//        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
//        agentEntityInter.setT(key);
//        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
//        AgentEntity authorizedAgent = new AgentEntity(cellphone, null, -1);
//        tradeAgentModel.setAuthorizedAgent(authorizedAgent);
//        NetworkManager.requestAgentBus(tradeAgentModel, new NetworkManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()) {
//                    Toast.makeText(TradeActivity.this, R.string.string_params_error, Toast.LENGTH_SHORT).show();
//                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_params_error));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()) {
//                    Toast.makeText(TradeActivity.this, R.string.string_result_empty, Toast.LENGTH_SHORT).show();
//                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_result_empty));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()) {
//                    Toast.makeText(TradeActivity.this, R.string.string_agent_other, Toast.LENGTH_SHORT).show();
//                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_agent_other));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
//                    TradeAgentModel responseAgentModel = JSON.parseObject(response.getData(), TradeAgentModel.class);
//                    AgentEntity authorizedAgent = responseAgentModel.getAuthorizedAgent();
//                    onSearchSuccessResult(authorizedAgent);
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
//                    Toast.makeText(TradeActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Toast.makeText(TradeActivity.this, R.string.string_agents_no_data, Toast.LENGTH_SHORT).show();
//                onSearchErrorResult(TradeActivity.this.getString(R.string.string_toast_network_error));
//            }
//
//            @Override
//            public void onError(Throwable exception, Object agentEntity) {
//                Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
//                onSearchErrorResult(TradeActivity.this.getString(R.string.string_toast_network_error));
//            }
//        });
    }

//    @Event(value = R.id.tv_act_trade_code_action, type = View.OnClickListener.class)
    private void onTradeAction(View view) {
//        String number = this.et_act_trade_code_number.getText().toString();
//        if (TextUtils.isEmpty(number)) {
//            Toast.makeText(this, R.string.string_input_grant_code_number, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!Pattern.compile("^[-\\+]?[\\d]*$").matcher(number).matches()) {
//            Toast.makeText(this, R.string.string_input_grant_code_number_integer, Toast.LENGTH_SHORT).show();
//            return;
//        }

//        if (this.currentAuthorizedAgent == null) {
//            Toast.makeText(this, R.string.string_input_grant_data_error, Toast.LENGTH_SHORT).show();
//        }

//        AgentEntity agentEntity = new AgentEntity(accountCellphone, null, this.getResources().getInteger(R.integer.type_id));
//        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
//        agentEntityInter.setT(key);
//        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
//        tradeAgentModel.setTradeCode(Integer.parseInt(number));
//        tradeAgentModel.setAuthorizedAgent(this.currentAuthorizedAgent);
//
//        NetworkManager.requestTrade(tradeAgentModel, new NetworkManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()) {
//                    Toast.makeText(TradeActivity.this, R.string.string_params_error, Toast.LENGTH_SHORT).show();
//                    onTradeFail(TradeActivity.this.getString(R.string.string_params_error));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()) {
//                    Toast.makeText(TradeActivity.this, R.string.string_agent_code_no_enough, Toast.LENGTH_SHORT).show();
//                    onTradeFail(TradeActivity.this.getString(R.string.string_agent_code_no_enough));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
//                    TradeAgentModel responseAgentModel = JSON.parseObject(response.getData(), TradeAgentModel.class);
//                    AgentEntity authorizedAgent = responseAgentModel.getAuthorizedAgent();
//                    onTradeSuccess(authorizedAgent);
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
//                    Toast.makeText(TradeActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Toast.makeText(TradeActivity.this, R.string.string_input_trade_fail, Toast.LENGTH_SHORT).show();
//                onTradeFail(TradeActivity.this.getString(R.string.string_input_trade_fail));
//            }
//
//            @Override
//            public void onError(Throwable exception, Object agentEntity) {
//                Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
//                onTradeFail(TradeActivity.this.getString(R.string.string_toast_network_error));
//            }
//        });
    }
}
