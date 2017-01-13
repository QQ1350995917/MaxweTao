package org.maxwe.tao.android.agent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.AgentApplication;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.mate.TrunkModel;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-12 16:19.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 寻找上级代理
 */
@ContentView(R.layout.activity_trunk)
public class TrunkActivity extends BaseActivity {

    @ViewInject(R.id.tv_act_trunk_no_data)
    private TextView tv_act_trunk_no_data;

    @ViewInject(R.id.ll_act_trunk_before_status)
    private LinearLayout ll_act_trunk_before_status;

    @ViewInject(R.id.et_act_trunk_leader_mark)
    private EditText et_act_trunk_leader_mark;
    @ViewInject(R.id.et_act_trunk_password)
    private EditText et_act_trunk_password;

    @ViewInject(R.id.ll_act_trunk_after_status)
    private LinearLayout ll_act_trunk_after_status;

    @ViewInject(R.id.tv_act_trunk_leader_id)
    private TextView tv_act_trunk_leader_id;
    @ViewInject(R.id.tv_act_trunk_leader_level)
    private TextView tv_act_trunk_leader_level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 数据异常
        if (AgentApplication.currentAgentEntity == null){
            showException();
        }
        // 已经加入成功
        if (AgentApplication.currentAgentEntity != null && AgentApplication.currentAgentEntity.getReach() == 1) {
            showLeaderInfo();
        }
        // 已经申请，但没有审核通过
        if (AgentApplication.currentAgentEntity != null && AgentApplication.currentAgentEntity.getReach() != 1 && !TextUtils.isEmpty(AgentApplication.currentAgentEntity.getpMark())) {
            AgentEntity agentEntity = new AgentEntity();
            agentEntity.setMark(AgentApplication.currentAgentEntity.getpMark());
            agentEntity.setReachTime(AgentApplication.currentAgentEntity.getReachTime());
            showReaching(agentEntity);
        }
        // 没有申请
        if (AgentApplication.currentAgentEntity != null && AgentApplication.currentAgentEntity.getReach() != 1 && TextUtils.isEmpty(AgentApplication.currentAgentEntity.getpMark())){
            showRequestLeader();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Event(value = R.id.bt_act_trunk_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

    private void showException(){
        this.tv_act_trunk_no_data.setVisibility(View.VISIBLE);
        this.ll_act_trunk_before_status.setVisibility(View.GONE);
        this.ll_act_trunk_after_status.setVisibility(View.GONE);
    }

    private void showLeaderInfo() {
        this.tv_act_trunk_no_data.setVisibility(View.GONE);
        this.ll_act_trunk_before_status.setVisibility(View.GONE);
        this.ll_act_trunk_after_status.setVisibility(View.VISIBLE);
        this.tv_act_trunk_leader_id.setText(this.getString(R.string.string_ID) + AgentApplication.currentAgentEntity.getMark());
        this.tv_act_trunk_leader_level.setText(this.getString(R.string.string_level) + AgentApplication.currentAgentEntity.getLevelId());
    }

    private void showReaching(AgentEntity agentEntity){
        AgentApplication.currentAgentEntity.setReach(0);
        AgentApplication.currentAgentEntity.setpMark(agentEntity.getMark());
        AgentApplication.currentAgentEntity.setReachTime(agentEntity.getReachTime());
        this.tv_act_trunk_no_data.setVisibility(View.GONE);
        this.ll_act_trunk_before_status.setVisibility(View.GONE);
        this.ll_act_trunk_after_status.setVisibility(View.VISIBLE);
        this.tv_act_trunk_leader_id.setText(this.getString(R.string.string_ID) + agentEntity.getMark());
        this.tv_act_trunk_leader_level.setText(R.string.string_reaching);
    }

    private void showRequestLeader() {
        this.tv_act_trunk_no_data.setVisibility(View.GONE);
        this.ll_act_trunk_before_status.setVisibility(View.VISIBLE);
        this.ll_act_trunk_after_status.setVisibility(View.GONE);
    }

    @Event(value = R.id.et_act_trunk_action, type = View.OnClickListener.class)
    private void onBegConfirmAction(final View view) {
        String leaderMark = et_act_trunk_leader_mark.getText().toString();
        String password = et_act_trunk_password.getText().toString();
        if (TextUtils.isEmpty(leaderMark)) {
            Toast.makeText(this, R.string.string_leader_mark, Toast.LENGTH_SHORT).show();
            return;
        }

        SessionModel session = SharedPreferencesUtils.getSession(this);
        if (TextUtils.equals(session.getMark(), leaderMark)) {
            Toast.makeText(this, R.string.string_leader_myself, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.string_input_account_password, Toast.LENGTH_SHORT).show();
            return;
        }

        view.setClickable(false);
        try {
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_mate_beg);
            session.setSign(session.getEncryptSing());
            TrunkModel trunkModel = new TrunkModel(session, leaderMark);
            trunkModel.setSign(session.getEncryptSing());
            NetworkManager.requestByPost(url, trunkModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    TrunkModel responseModel = JSON.parseObject(result, TrunkModel.class);
                    showReaching(responseModel.getAgentEntity());
                    view.setClickable(true);
                }

                @Override
                public void onEmptyResult(String result) {
                    Toast.makeText(TrunkActivity.this, R.string.string_no_mark, Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }

                @Override
                public void onLoginTimeout(String result) {
                    Toast.makeText(TrunkActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.clearSession(TrunkActivity.this);
                    view.setClickable(true);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(TrunkActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                }

                @Override
                public void onOther(int code, String result) {
                    if (code == IResponse.ResultCode.RC_ACCESS_BAD_2.getCode()){
                        Toast.makeText(TrunkActivity.this, R.string.string_the_mark_no_reach, Toast.LENGTH_SHORT).show();
                    }
                    view.setClickable(true);
                }
            });
        } catch (Exception e) {
            view.setClickable(true);
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }

    }

}
