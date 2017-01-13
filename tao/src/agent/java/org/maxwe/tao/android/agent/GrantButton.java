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
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.mate.TrunkModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;

/**
 * Created by Pengwei Ding on 2017-01-13 12:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GrantButton extends Button implements View.OnClickListener {
    private AgentEntity agentEntity;

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

    public void setAgentEntity(AgentEntity agentEntity) {
        this.agentEntity = agentEntity;
        if (agentEntity.getReach() != 1) {
            this.setText(R.string.string_grant_new_agent);
        } else {
            this.setText(agentEntity.getCodeStatusString());
        }
    }

    @Override
    public void onClick(View v) {
        if (this.agentEntity == null) {
            return;
        }
        if (this.agentEntity.getReach() != 1) {
            onRequestGrantAgent(this.agentEntity);
        } else {
            onTradeCode(this.agentEntity);
        }
    }

    private void onRequestGrantSuccess() {
        this.agentEntity.setReach(1);
        this.setText(agentEntity.getCodeStatusString());
    }

    private void onRequestGrantAgent(final AgentEntity agentEntity) {
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
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(GrantButton.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(GrantButton.this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void onTradeCode(AgentEntity agentEntity) {
        Intent intent = new Intent(this.getContext(), TradeActivity.class);
        intent.putExtra(Constants.KEY_INTENT_AGENT, agentEntity);
        this.getContext().startActivity(intent);
    }
}
