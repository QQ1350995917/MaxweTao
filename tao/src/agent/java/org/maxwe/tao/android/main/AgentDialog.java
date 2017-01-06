package org.maxwe.tao.android.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.agent.AgentEntity;
import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.agent.AgentManager;
import org.maxwe.tao.android.agent.TradeAgentModel;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-05 14:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentDialog extends Dialog implements View.OnClickListener {




    public AgentDialog(Context context) {
        super(context);
        this.initView();
    }

    public AgentDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.initView();
    }

    protected AgentDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.initView();
    }

    protected void initView() {
//        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.activity_grant_dialog);
    }

    public void onGrantConfirmAction(String cellphone) {

        SharedPreferences sharedPreferences = AgentDialog.this.getContext().getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String accountCellphone = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);

        AgentEntity agentEntity = new AgentEntity(accountCellphone,null,AgentDialog.this.getContext().getResources().getInteger(R.integer.type_id));
        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
        agentEntityInter.setT(key);
        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
        tradeAgentModel.setTradeCode(1);
        AgentEntity authorizedAgent = new AgentEntity(cellphone,null,-1);
        tradeAgentModel.setAuthorizedAgent(authorizedAgent);
        AgentManager.requestGrant(tradeAgentModel, new AgentManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_no_exist, Toast.LENGTH_SHORT).show();
                }

                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_REPEAT.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_repeat, Toast.LENGTH_SHORT).show();
                }

                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_success, Toast.LENGTH_SHORT).show();
                    AgentDialog.this.dismiss();
                    return;
                }

                Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable exception, AgentEntity agentEntity) {
                Toast.makeText(AgentDialog.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
