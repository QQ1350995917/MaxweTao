package org.maxwe.tao.android.code;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.AgentApplication;
import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.agent.AgentModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.agent.TrunkActivity;
import org.maxwe.tao.android.trade.TradeModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-11 16:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_code)
public class ActCodeFragment extends BaseFragment {
    private static final int REQUEST_CODE_GEN_ACT_CODE = 10;

    // 级别
    @ViewInject(R.id.tv_frg_code_level_name)
    private TextView tv_frg_code_level_name;
    // 码量
    @ViewInject(R.id.tv_frg_code_number)
    private TextView tv_frg_code_number;

    // 生成激活码后的界面，默认不显示
    @ViewInject(R.id.ll_frg_code_gen)
    private LinearLayout ll_frg_code_gen;
    // 显示激活码，生成激活码后的界面，默认不显示
    @ViewInject(R.id.tv_frg_code_gen_display)
    private TextView tv_frg_code_gen_display;

    // 激活自己的账号的按钮，默认不显示
    @ViewInject(R.id.bt_frg_code_active_action)
    private Button bt_frg_code_active_action;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.onRequestMineInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_GEN_ACT_CODE:
                if (resultCode == GenCodeActivity.RESULT_CODE_GEN_ACT_CODE_OK) {
                    TradeModel tradeModel = (TradeModel) data.getSerializableExtra(Constants.KEY_INTENT_SESSION);
                    onGenCodeSuccess(tradeModel);
                } else if (resultCode == GenCodeActivity.RESULT_CODE_GEN_ACT_CODE_ERROR) {

                }
                break;
            default:
                break;
        }
    }

    // 成功生成一个激活码回显到界面中
    private void onGenCodeSuccess(TradeModel tradeModel) {
        if (AgentApplication.currentAgentModel != null) {
            AgentEntity agentEntity = AgentApplication.currentAgentModel.getAgentEntity();
            agentEntity.setSpendCodes(agentEntity.getSpendCodes() + 1);
            agentEntity.setLeftCodes(agentEntity.getLeftCodes() - 1);
        }
        this.ll_frg_code_gen.setVisibility(View.VISIBLE);
        this.tv_frg_code_gen_display.setText(tradeModel.getActCode());
        resetCodesStatus();
    }

    public void resetCodesStatus() {
        if (AgentApplication.currentAgentModel != null) {
            if (AgentApplication.currentAgentModel.getLevelEntity() != null) {
                this.tv_frg_code_level_name.setText(AgentApplication.currentAgentModel.getLevelEntity().getName());
            }
            if (AgentApplication.currentAgentModel.getAgentEntity() != null) {
                this.tv_frg_code_number.setText(
                        AgentApplication.currentAgentModel.getAgentEntity().getSpendCodes() + "/" +
                                AgentApplication.currentAgentModel.getAgentEntity().getLeftCodes() + "/" +
                                AgentApplication.currentAgentModel.getAgentEntity().getHaveCodes()
                );
            }

        } else {
            this.tv_frg_code_number.setText("0/0/0");
        }
    }

    // 显示已经激活的显示状态（默认激活）
    private void showReachView(AgentModel responseModel) {
        AgentApplication.currentAgentModel = responseModel;
        this.bt_frg_code_active_action.setVisibility(View.INVISIBLE);
        resetCodesStatus();
    }

    // 显示未激活的显示状态（默认激活）
    private void showUnReachView(AgentModel responseModel) {
        AgentApplication.currentAgentModel = responseModel;
        this.bt_frg_code_active_action.setVisibility(View.VISIBLE);
    }

    // 历史记录
    @Event(value = R.id.bt_frg_code_history, type = View.OnClickListener.class)
    private void onGenCodeHistoryAction(View view) {
        Intent intent = new Intent(this.getContext(), HistoryActivity.class);
        this.startActivity(intent);
    }

    // 一键生成授权码
    @Event(value = R.id.bt_frg_code_click_gen_code, type = View.OnClickListener.class)
    private void onGenCodeAction(View view) {
        if (AgentApplication.currentAgentModel == null || AgentApplication.currentAgentModel.getAgentEntity().getReach() != 1) {
            Toast.makeText(this.getContext(), R.string.string_your_account_need_active, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this.getContext(), GenCodeActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_GEN_ACT_CODE);
    }


    // 点击复制
    @Event(value = R.id.bt_frg_code_click_copy, type = View.OnClickListener.class)
    private void onCopyCodeAction(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, this.tv_frg_code_gen_display.getText().toString()));
        Toast.makeText(this.getContext(), R.string.string_copy_success, Toast.LENGTH_SHORT).show();
    }

    // 点击激活账户
    @Event(value = R.id.bt_frg_code_active_action, type = View.OnClickListener.class)
    private void onActiveAction(View view) {
        Intent intent = new Intent(this.getContext(), TrunkActivity.class);
        this.startActivity(intent);
    }

    private void onRequestMineInfo() {
        try {
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_mine);
            SessionModel session = SharedPreferencesUtils.getSession(this.getContext());
            session.setSign(session.getEncryptSing());
            NetworkManager.requestByPost(url, session, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    AgentModel responseModel = JSON.parseObject(result, AgentModel.class);
                        if (responseModel.getAgentEntity().getReach() != 1) {
                        showUnReachView(responseModel);
                    } else {
                        showReachView(responseModel);
                    }
                }

                @Override
                public void onLoginTimeout(String result) {
                    Toast.makeText(ActCodeFragment.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.clearSession(ActCodeFragment.this.getContext());
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(ActCodeFragment.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
//                    onResponseReAct(MainActivity.this.getString(R.string.string_toast_network_error));
                }

                @Override
                public void onOther(int code, String result) {
                    super.onOther(code, result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }

    }

}
