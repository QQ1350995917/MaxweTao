package org.maxwe.tao.android.agent;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.agent.AgentModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.mate.GrantBranchRequestModel;
import org.maxwe.tao.android.mate.GrantBranchResponseModel;
import org.maxwe.tao.android.mate.MateModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;

/**
 * Created by Pengwei Ding on 2017-01-13 12:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GrantButton extends Button implements View.OnClickListener {
    private MateModel mateModel;

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

    public void setMateModel(MateModel mateModel) {
        this.mateModel = mateModel;
        if (mateModel.getAgent().getReach() != 1) {
            this.setText(R.string.string_grant_new_agent);
        } else {
            this.setText(mateModel.getAgent().getCodeStatusString());
        }
    }

    @Override
    public void onClick(View v) {
        if (this.mateModel == null || this.mateModel.getAgent() == null) {
            return;
        }
        if (this.mateModel.getAgent().getReach() != 1) {
            this.setClickable(false);
            onRequestGrantAgent(this.mateModel.getAgent());
        } else {
            Intent intent = new Intent(this.getContext(), TradeActivity.class);
            intent.putExtra(Constants.KEY_INTENT_AGENT, mateModel);
            this.getContext().startActivity(intent);
        }
    }

    private void onRequestGrantSuccess() {
        this.mateModel.getAgent().setReach(1);
        this.setText(this.mateModel.getAgent().getCodeStatusString());
        this.setClickable(true);
    }

    private void onRequestGrantFail() {
        this.setClickable(true);
    }

    private void onRequestGrantAgent(final AgentEntity agentEntity) {
        try {
            TokenModel session = SharedPreferencesUtils.getSession(this.getContext());
            GrantBranchRequestModel trunkModel = new GrantBranchRequestModel(session, agentEntity.getId());
            trunkModel.setSign(session.getEncryptSing());
            String url = GrantButton.this.getContext().getString(R.string.string_url_domain) + GrantButton.this.getContext().getString(R.string.string_url_trade_grant);
            NetworkManager.requestByPost(url, trunkModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
//                    GrantBranchResponseModel responseModel = JSON.parseObject(result, GrantBranchResponseModel.class);
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
}
