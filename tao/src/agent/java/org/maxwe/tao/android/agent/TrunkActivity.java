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
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.mate.BranchBegRequestModel;
import org.maxwe.tao.android.mate.BranchBegResponseModel;
import org.maxwe.tao.android.mate.MateModel;
import org.maxwe.tao.android.mate.TrunkInfoRequestModel;
import org.maxwe.tao.android.mate.TrunkInfoResponseModel;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.w3c.dom.Text;
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
    @ViewInject(R.id.et_act_trunk_my_wechat)
    private EditText et_act_trunk_my_wechat;
    @ViewInject(R.id.et_act_trunk_password)
    private EditText et_act_trunk_password;

    @ViewInject(R.id.ll_act_trunk_after_status)
    private LinearLayout ll_act_trunk_after_status;

    @ViewInject(R.id.tv_act_trunk_leader_grant_status)
    private TextView tv_act_trunk_leader_grant_status;
    @ViewInject(R.id.tv_act_trunk_leader_id)
    private TextView tv_act_trunk_leader_id;
    @ViewInject(R.id.tv_act_trunk_leader_level)
    private TextView tv_act_trunk_leader_level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 数据异常
        if (AgentApplication.currentAgentModel == null) {
            showException();
        } else {
            onRequestTrunkAgentInfo();
        }
    }


    private void onRequestTrunkAgentInfo() {
        try {
            TokenModel tokenModel = SharedPreferencesUtils.getSession(this);
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_mate_leader);
            tokenModel.setSign(tokenModel.getEncryptSing());
            TrunkInfoRequestModel trunkModel = new TrunkInfoRequestModel(tokenModel);
            trunkModel.setSign(tokenModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, trunkModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    TrunkInfoResponseModel responseModel = JSON.parseObject(result, TrunkInfoResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS){
                        showTrunkInfoView(responseModel.getTrunk(),responseModel.getBranch());
                    }
                    Toast.makeText(TrunkActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(TrunkActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
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

    private void showException() {
        this.tv_act_trunk_no_data.setVisibility(View.VISIBLE);
        this.ll_act_trunk_before_status.setVisibility(View.GONE);
        this.ll_act_trunk_after_status.setVisibility(View.GONE);
    }

    private void showTrunkInfoView(MateModel trunkMateModel, AgentEntity branchAgent) {
        this.tv_act_trunk_no_data.setVisibility(View.GONE);
        this.ll_act_trunk_before_status.setVisibility(View.GONE);
        this.ll_act_trunk_after_status.setVisibility(View.VISIBLE);

        this.tv_act_trunk_leader_grant_status.setText("状态：" + (branchAgent.getReach() == 1 ? "已经批准":"未批准"));
        this.tv_act_trunk_leader_id.setText(this.getString(R.string.string_ID) + trunkMateModel.getAgent().getId());
        this.tv_act_trunk_leader_level.setText(this.getString(R.string.string_level) + trunkMateModel.getLevel().getName());
    }

    private void showUnTrunkView() {
        this.tv_act_trunk_no_data.setVisibility(View.GONE);
        this.ll_act_trunk_before_status.setVisibility(View.VISIBLE);
        this.ll_act_trunk_after_status.setVisibility(View.GONE);
    }

    @Event(value = R.id.et_act_trunk_action, type = View.OnClickListener.class)
    private void onBegConfirmAction(final View view) {
        String leaderId = et_act_trunk_leader_mark.getText().toString();
        String password = et_act_trunk_password.getText().toString();
        if (TextUtils.isEmpty(leaderId)) {
            Toast.makeText(this, R.string.string_leader_mark, Toast.LENGTH_SHORT).show();
            return;
        }

        TokenModel session = SharedPreferencesUtils.getSession(this);
        if (TextUtils.equals(session.getId() + "", leaderId)) {
            Toast.makeText(this, R.string.string_leader_myself, Toast.LENGTH_SHORT).show();
            return;
        }

        String weChat = et_act_trunk_my_wechat.getText().toString();
        if (TextUtils.isEmpty(weChat)) {
            Toast.makeText(this, R.string.string_leader_myself_wechat, Toast.LENGTH_SHORT).show();
            return;
        }

        if (weChat.length() < 1 || weChat.length() > 36){
            Toast.makeText(this, "微信号码不合格", Toast.LENGTH_SHORT).show();
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
            BranchBegRequestModel branchBegRequestModel = new BranchBegRequestModel(session, Integer.parseInt(leaderId), weChat);
            branchBegRequestModel.setAuthenticatePassword(password);
            branchBegRequestModel.setSign(session.getEncryptSing());
            branchBegRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
            NetworkManager.requestByPostNew(url, branchBegRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    BranchBegResponseModel responseModel = JSON.parseObject(result, BranchBegResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS){
                        showTrunkInfoView(responseModel.getTrunk(),responseModel.getBranch());
                    }
                    view.setClickable(true);
                    Toast.makeText(TrunkActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(TrunkActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
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
