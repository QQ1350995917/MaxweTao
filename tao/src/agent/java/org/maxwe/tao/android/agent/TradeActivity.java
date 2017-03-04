package org.maxwe.tao.android.agent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.AgentApplication;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.level.LevelEntity;
import org.maxwe.tao.android.mate.BranchInfoRequestModel;
import org.maxwe.tao.android.mate.BranchInfoResponseModel;
import org.maxwe.tao.android.mate.MateModel;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.trade.TradeRequestModel;
import org.maxwe.tao.android.trade.TradeResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Pengwei Ding on 2016-12-30 17:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 授权码交易
 */
@ContentView(R.layout.activity_trade)
public class TradeActivity extends BaseActivity implements View.OnFocusChangeListener, RadioGroup.OnCheckedChangeListener {

    private MateModel branchAgentModel;

    @ViewInject(R.id.tv_act_trade_id)
    private TextView tv_act_trade_id;
    @ViewInject(R.id.tv_act_trade_level)
    private TextView tv_act_trade_level;

    @ViewInject(R.id.tv_act_trade_haveCodes)
    private TextView tv_act_trade_haveCodes;
    @ViewInject(R.id.tv_act_trade_leftCodes)
    private TextView tv_act_trade_leftCodes;
    @ViewInject(R.id.tv_act_trade_spendCodes)
    private TextView tv_act_trade_spendCodes;

    @ViewInject(R.id.tv_act_trade_no_data)
    private TextView tv_act_trade_no_data;

    @ViewInject(R.id.rl_act_trade_container)
    private RelativeLayout rl_act_trade_container;

    @ViewInject(R.id.rg_act_levels)
    private RadioGroup rg_act_levels; // 承载一个重要属性，当前用户的级别属性

    @ViewInject(R.id.et_act_trade_number)
    private EditText et_act_trade_number;

    @ViewInject(R.id.et_act_trade_password)
    private EditText et_act_trade_password;

    @ViewInject(R.id.bt_act_trade_action)
    private Button bt_act_trade_action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Serializable serializableExtra = this.getIntent().getSerializableExtra(Constants.KEY_INTENT_AGENT);
        if (serializableExtra != null && serializableExtra instanceof MateModel) {
            this.branchAgentModel = (MateModel) serializableExtra;
            onRequestBranchAgentInfo();
            this.rg_act_levels.setTag(this.branchAgentModel.getLevel());
            this.rg_act_levels.setOnCheckedChangeListener(this);
            this.et_act_trade_number.setOnFocusChangeListener(this);
        }
    }

    private void onRequestBranchAgentInfo() {
        try {
            TokenModel session = SharedPreferencesUtils.getSession(this);
            BranchInfoRequestModel trunkModel = new BranchInfoRequestModel(session, branchAgentModel.getAgent().getId());
            trunkModel.setSign(session.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_mate_mate);
            NetworkManager.requestByPost(url, trunkModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    BranchInfoResponseModel responseModel = JSON.parseObject(result, BranchInfoResponseModel.class);
                    onRequestBranchAgentInfoSuccess(responseModel);
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(TradeActivity.this);
                    Toast.makeText(TradeActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRequestBranchAgentInfoSuccess(BranchInfoResponseModel responseModel) {
        this.tv_act_trade_no_data.setVisibility(View.GONE);
        this.rl_act_trade_container.setVisibility(View.VISIBLE);
        AgentEntity agentEntity = responseModel.getBranch().getAgent();
        LevelEntity levelEntity = responseModel.getBranch().getLevel();
        this.tv_act_trade_id.setText("ID:" + agentEntity.getId());
        if (levelEntity == null) {
            this.tv_act_trade_level.setText("代理级别未定");
        } else {
            this.tv_act_trade_level.setText(levelEntity.getName());
        }

        this.tv_act_trade_haveCodes.setText(agentEntity.getHaveCodes() + "");
        this.tv_act_trade_leftCodes.setText(agentEntity.getLeftCodes() + "");
        this.tv_act_trade_spendCodes.setText(agentEntity.getSpendCodes() + "");

        List<LevelEntity> levels = responseModel.getLevels();
        LevelEntity currentLevel = responseModel.getBranch().getLevel();

        if (levels != null) {
            for (LevelEntity level : levels) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setTag(level);
                radioButton.setLayoutParams(new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                radioButton.setText(level.getName() + "(" + level.getMinNum() + "码起,每码价格" + level.getPrice() + "元)");
                if (level.equals(currentLevel)) {
                    radioButton.setChecked(true);
                }
                rg_act_levels.addView(radioButton);
            }
        }
        if (currentLevel != null) {
//            this.et_act_trade_number.setText("" + currentLevel.getMinNum());
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getTag() == null) {
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    group.setTag(group.getChildAt(i).getTag());
                    break;
                }
            }
        } else {
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (group.getTag().equals(group.getChildAt(i).getTag())) {
                    ((RadioButton) group.getChildAt(i)).setChecked(true);
                } else {
                    ((RadioButton) group.getChildAt(i)).setChecked(false);
                }
            }
            Toast.makeText(this, "级别自动升级，不可更改", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_trade_back, type = View.OnClickListener.class)
    private void onBackToMainAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
        }
    }

    private void onResponseSuccess(TradeResponseModel responseModel) {
        this.bt_act_trade_action.setClickable(true);
        this.et_act_trade_number.setText(null);
        this.et_act_trade_password.setText(null);
        if (AgentApplication.currentAgentModel != null) {
            AgentEntity agentEntity = AgentApplication.currentAgentModel.getAgentEntity();
            agentEntity.setSpendCodes(agentEntity.getSpendCodes() + responseModel.getCodeNum());
            agentEntity.setLeftCodes(agentEntity.getLeftCodes() - responseModel.getCodeNum());
        }

        if (this.branchAgentModel != null) {
            AgentEntity agentEntity = this.branchAgentModel.getAgent();
            agentEntity.setHaveCodes(agentEntity.getHaveCodes() + responseModel.getCodeNum());
            agentEntity.setLeftCodes(agentEntity.getLeftCodes() + responseModel.getCodeNum());
            this.tv_act_trade_haveCodes.setText(agentEntity.getHaveCodes() + "");
            this.tv_act_trade_leftCodes.setText(agentEntity.getLeftCodes() + "");
            this.tv_act_trade_spendCodes.setText(agentEntity.getSpendCodes() + "");
        }
    }

    private void onResponseError() {
        this.bt_act_trade_action.setClickable(true);
    }

    @Event(value = R.id.bt_act_trade_action, type = View.OnClickListener.class)
    private void onTradeAction(View view) {
        if (this.rg_act_levels.getTag() == null || !(this.rg_act_levels.getTag() instanceof LevelEntity)) {
            Toast.makeText(this, "请为您的代理选择级别", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = this.et_act_trade_number.getText().toString();
        String password = this.et_act_trade_password.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, R.string.string_input_grant_code_number, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Pattern.compile("^[-\\+]?[\\d]*$").matcher(number).matches()) {
            Toast.makeText(this, R.string.string_input_grant_code_number_integer, Toast.LENGTH_SHORT).show();
            return;
        }

        LevelEntity currentLevel = (LevelEntity) this.rg_act_levels.getTag();
        if (branchAgentModel.getLevel() == null) {
            if (Integer.parseInt(number) < currentLevel.getMinNum()) {
                Toast.makeText(this, R.string.string_trade_num_by_level, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            if (Integer.parseInt(number) < 1) {
                Toast.makeText(this, R.string.string_trade_num_by_level, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.string_input_account_password, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6 || password.length() > 12) {
            Toast.makeText(this, "密码格式错误", Toast.LENGTH_SHORT).show();
            return;
        }

        view.setClickable(false);
        try {
            TokenModel session = SharedPreferencesUtils.getSession(this);
            branchAgentModel.setLevel(currentLevel);
            TradeRequestModel tradeModel = new TradeRequestModel(session, branchAgentModel, Integer.parseInt(number));
            tradeModel.setSign(session.getEncryptSing());
            tradeModel.setAuthenticatePassword(password);
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_trade_trade);
            NetworkManager.requestByPost(url, tradeModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    TradeResponseModel responseModel = JSON.parseObject(result, TradeResponseModel.class);
                    onResponseSuccess(responseModel);
                    Toast.makeText(TradeActivity.this, R.string.string_input_trade_success, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAccessBad(String result) {
                    onResponseError();
                    Toast.makeText(TradeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoginTimeout(String result) {
                    onResponseError();
                    SharedPreferencesUtils.clearSession(TradeActivity.this);
                    Toast.makeText(TradeActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    onResponseError();
                    Toast.makeText(TradeActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onOther(int code, String result) {
                    if (code == IResponse.ResultCode.RC_ACCESS_BAD_2.getCode()) {
                        Toast.makeText(TradeActivity.this, R.string.string_trade_num_by_level, Toast.LENGTH_SHORT).show();
                    }
                    onResponseError();
                }
            });
        } catch (Exception e) {
            view.setClickable(true);
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }
}
