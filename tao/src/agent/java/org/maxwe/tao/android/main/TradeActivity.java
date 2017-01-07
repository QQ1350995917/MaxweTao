package org.maxwe.tao.android.main;


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
import org.maxwe.tao.android.agent.AgentEntity;
import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.agent.TradeAgentModel;
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

    @ViewInject(R.id.et_act_trade_search_content)
    private EditText et_act_trade_search_content;
    @ViewInject(R.id.bt_act_trade_search_action)
    private Button bt_act_trade_search_action;

    @ViewInject(R.id.pb_act_trade_progress)
    private ProgressBar pb_act_trade_progress;

    @ViewInject(R.id.tv_act_trade_search_result)
    private TextView tv_act_trade_search_result;

    @ViewInject(R.id.ll_act_trade_search_result_list)
    private LinearLayout ll_act_trade_search_result_list;

    @ViewInject(R.id.tv_act_trade_search_result_cellphone)
    private TextView tv_act_trade_search_result_cellphone;
    @ViewInject(R.id.tv_act_trade_search_result_grant_status)
    private TextView tv_act_trade_search_result_grant_status;
    @ViewInject(R.id.et_act_trade_code_number)
    private EditText et_act_trade_code_number;
    @ViewInject(R.id.tv_act_trade_code_action)
    private Button tv_act_trade_code_action;
    @ViewInject(R.id.tv_act_trade_trade_result)
    private TextView tv_act_trade_trade_result;

    private AgentEntity currentAuthorizedAgent = null;

    @Event(value = R.id.bt_act_trade_back, type = View.OnClickListener.class)
    private void onBackToMainAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void onSearchGoing(){
        this.bt_act_trade_search_action.setClickable(false);
        this.pb_act_trade_progress.setVisibility(View.VISIBLE);
        this.tv_act_trade_search_result.setVisibility(View.GONE);
        this.tv_act_trade_trade_result.setVisibility(View.GONE);
    }

    private void onSearchSuccessResult(AgentEntity authorizedAgent) {
        this.bt_act_trade_search_action.setClickable(true);
        this.currentAuthorizedAgent = authorizedAgent;
        this.pb_act_trade_progress.setVisibility(View.GONE);
        this.tv_act_trade_search_result.setVisibility(View.GONE);
        this.ll_act_trade_search_result_list.setVisibility(View.VISIBLE);
        this.tv_act_trade_trade_result.setVisibility(View.GONE);

        this.et_act_trade_code_number.setText(null);
        this.et_act_trade_code_number.setVisibility(View.VISIBLE);
        this.tv_act_trade_code_action.setVisibility(View.VISIBLE);

        this.tv_act_trade_search_result_cellphone.setText("电话号码：" + authorizedAgent.getCellphone());
        this.tv_act_trade_search_result_grant_status.setText("授权状态：" + authorizedAgent.getSpendCodes() + "/" + authorizedAgent.getLeftCodes() + "/" + authorizedAgent.getHaveCodes());
    }

    private void onSearchErrorResult(String result) {
        this.bt_act_trade_search_action.setClickable(true);
        this.currentAuthorizedAgent = null;
        this.ll_act_trade_search_result_list.setVisibility(View.GONE);
        this.pb_act_trade_progress.setVisibility(View.GONE);
        this.tv_act_trade_search_result.setVisibility(View.VISIBLE);
        this.tv_act_trade_search_result.setText(result);
        this.tv_act_trade_trade_result.setVisibility(View.GONE);
    }

    private void onTradeGoing() {
        this.bt_act_trade_search_action.setClickable(false);
        this.tv_act_trade_code_action.setClickable(false);
        this.tv_act_trade_trade_result.setVisibility(View.GONE);
        this.pb_act_trade_progress.setVisibility(View.VISIBLE);
    }

    private void onTradeSuccess(AgentEntity authorizedAgent) {
        this.bt_act_trade_search_action.setClickable(true);
        this.tv_act_trade_trade_result.setVisibility(View.GONE);
        this.pb_act_trade_progress.setVisibility(View.GONE);
        this.tv_act_trade_search_result_cellphone.setText("电话:" + authorizedAgent.getCellphone());
        this.tv_act_trade_search_result_grant_status.setText("码量(转让/剩余/共有):" + authorizedAgent.getSpendCodes() + "/" + authorizedAgent.getLeftCodes() + "/" + authorizedAgent.getHaveCodes());

        this.et_act_trade_code_number.setVisibility(View.GONE);
        this.tv_act_trade_code_action.setVisibility(View.GONE);
    }

    private void onTradeFail(String result) {
        this.bt_act_trade_search_action.setClickable(true);
        this.tv_act_trade_code_action.setClickable(true);
        this.tv_act_trade_trade_result.setVisibility(View.VISIBLE);
        this.tv_act_trade_trade_result.setText(result);
        this.pb_act_trade_progress.setVisibility(View.GONE);
    }

    @Event(value = R.id.bt_act_trade_search_action, type = View.OnClickListener.class)
    private void onSearchAction(View view) {
        String cellphone = this.et_act_trade_search_content.getText().toString();
        if (!CellPhoneUtils.isCellphone(cellphone)) {
            Toast.makeText(this, R.string.string_right_cellphone, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String grantAccount = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
        if (TextUtils.equals(cellphone, grantAccount)) {
            Toast.makeText(this, R.string.string_grant_self, Toast.LENGTH_SHORT).show();
            return;
        }

        this.onSearchGoing();

        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);
        AgentEntity agentEntity = new AgentEntity(grantAccount, null, this.getResources().getInteger(R.integer.type_id));
        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
        agentEntityInter.setT(key);
        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
        AgentEntity authorizedAgent = new AgentEntity(cellphone, null, -1);
        tradeAgentModel.setAuthorizedAgent(authorizedAgent);
        NetworkManager.requestAgentBus(tradeAgentModel, new NetworkManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()) {
                    Toast.makeText(TradeActivity.this, R.string.string_params_error, Toast.LENGTH_SHORT).show();
                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_params_error));
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()) {
                    Toast.makeText(TradeActivity.this, R.string.string_result_empty, Toast.LENGTH_SHORT).show();
                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_result_empty));
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()) {
                    Toast.makeText(TradeActivity.this, R.string.string_agent_other, Toast.LENGTH_SHORT).show();
                    onSearchErrorResult(TradeActivity.this.getString(R.string.string_agent_other));
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
                    TradeAgentModel responseAgentModel = JSON.parseObject(response.getData(), TradeAgentModel.class);
                    AgentEntity authorizedAgent = responseAgentModel.getAuthorizedAgent();
                    onSearchSuccessResult(authorizedAgent);
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
                    Toast.makeText(TradeActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(TradeActivity.this, R.string.string_agents_no_data, Toast.LENGTH_SHORT).show();
                onSearchErrorResult(TradeActivity.this.getString(R.string.string_toast_network_error));
            }

            @Override
            public void onError(Throwable exception, Object agentEntity) {
                Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                onSearchErrorResult(TradeActivity.this.getString(R.string.string_toast_network_error));
            }
        });
    }

    @Event(value = R.id.tv_act_trade_code_action, type = View.OnClickListener.class)
    private void onTradeAction(View view) {
        String number = this.et_act_trade_code_number.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, R.string.string_input_grant_code_number, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Pattern.compile("^[-\\+]?[\\d]*$").matcher(number).matches()) {
            Toast.makeText(this, R.string.string_input_grant_code_number_integer, Toast.LENGTH_SHORT).show();
            return;
        }

        if (this.currentAuthorizedAgent == null) {
            Toast.makeText(this, R.string.string_input_grant_data_error, Toast.LENGTH_SHORT).show();
        }

        this.onTradeGoing();

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String accountCellphone = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);
        AgentEntity agentEntity = new AgentEntity(accountCellphone, null, this.getResources().getInteger(R.integer.type_id));
        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
        agentEntityInter.setT(key);
        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
        tradeAgentModel.setTradeCode(Integer.parseInt(number));
        tradeAgentModel.setAuthorizedAgent(this.currentAuthorizedAgent);

        NetworkManager.requestTrade(tradeAgentModel, new NetworkManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()) {
                    Toast.makeText(TradeActivity.this, R.string.string_params_error, Toast.LENGTH_SHORT).show();
                    onTradeFail(TradeActivity.this.getString(R.string.string_params_error));
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()) {
                    Toast.makeText(TradeActivity.this, R.string.string_agent_code_no_enough, Toast.LENGTH_SHORT).show();
                    onTradeFail(TradeActivity.this.getString(R.string.string_agent_code_no_enough));
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
                    TradeAgentModel responseAgentModel = JSON.parseObject(response.getData(), TradeAgentModel.class);
                    AgentEntity authorizedAgent = responseAgentModel.getAuthorizedAgent();
                    onTradeSuccess(authorizedAgent);
                    return;
                }

                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
                    Toast.makeText(TradeActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(TradeActivity.this, R.string.string_input_trade_fail, Toast.LENGTH_SHORT).show();
                onTradeFail(TradeActivity.this.getString(R.string.string_input_trade_fail));
            }

            @Override
            public void onError(Throwable exception, Object agentEntity) {
                Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                onTradeFail(TradeActivity.this.getString(R.string.string_toast_network_error));
            }
        });
    }
}
