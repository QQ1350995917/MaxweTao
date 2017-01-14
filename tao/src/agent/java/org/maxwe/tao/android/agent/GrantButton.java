package org.maxwe.tao.android.agent;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.mate.TrunkModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;

/**
 * Created by Pengwei Ding on 2017-01-13 12:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GrantButton extends Button implements View.OnClickListener {
    private AgentModel agentModel;

    public GrantButton(Context context) {
        super(context);
        this.init();
    }

    public GrantButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public GrantButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.setOnClickListener(this);
    }

    public void setAgentEntity(AgentModel agentEntity) {
        this.agentModel = agentEntity;
        if (agentEntity.getAgentEntity().getReach() != 1) {
            this.setText(R.string.string_grant_new_agent);
        } else {
            this.setText(agentEntity.getAgentEntity().getCodeStatusString());
        }
    }

    @Override
    public void onClick(View v) {
        if (this.agentModel == null) {
            return;
        }
        if (this.agentModel.getAgentEntity().getReach() != 1) {
            this.setClickable(false);
            onRequestGrantAgent(this.agentModel);
        } else {
            onTradeCode(this.agentModel);
        }
    }

    private void onRequestGrantSuccess() {
        this.agentModel.getAgentEntity().setReach(1);
        this.setText(this.agentModel.getAgentEntity().getCodeStatusString());
        this.setClickable(true);
    }

    private void onRequestGrantFail() {
        this.setClickable(true);
    }

    private void onRequestGrantAgent(final AgentModel agentEntity) {
        try {
            SessionModel session = SharedPreferencesUtils.getSession(this.getContext());
            TrunkModel trunkModel = new TrunkModel(session, agentEntity.getMark());
            trunkModel.setSign(session.getEncryptSing());
            String url = GrantButton.this.getContext().getString(R.string.string_url_domain) + GrantButton.this.getContext().getString(R.string.string_url_mate_grant);
            NetworkManager.requestByPost(url, trunkModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
//                    BranchModel responseModel = JSON.parseObject(result, BranchModel.class);
                    onRequestGrantSuccess();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(GrantButton.this.getContext());
                    Toast.makeText(GrantButton.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                    onRequestGrantFail();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(GrantButton.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    onRequestGrantFail();
                }

            });
        } catch (Exception e) {
            this.setClickable(true);
            e.printStackTrace();
            Toast.makeText(GrantButton.this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void onTradeCode(AgentModel agentEntity) {
        Intent intent = new Intent(this.getContext(), TradeActivity.class);
        intent.putExtra(Constants.KEY_INTENT_AGENT, agentEntity);
        this.getContext().startActivity(intent);
    }
}
