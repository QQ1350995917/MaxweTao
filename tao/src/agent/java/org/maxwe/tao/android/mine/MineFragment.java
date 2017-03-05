package org.maxwe.tao.android.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.maxwe.tao.android.AgentApplication;
import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.agent.TradeActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-11 16:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {
    public static final int REQUEST_CODE_MODIFY_PASSWORD = 3;

    @ViewInject(R.id.tv_frg_mine_level_name)
    private TextView tv_frg_mine_level_name;
    @ViewInject(R.id.tv_frg_mine_number)
    private TextView tv_frg_mine_number;

    @ViewInject(R.id.tv_frg_mine_id)
    private TextView tv_frg_mine_id;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MODIFY_PASSWORD:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onModifyPasswordSuccessCallback((TokenModel) data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resetCodesStatus();
    }

    public void resetCodesStatus() {
        if (AgentApplication.currentAgentModel != null) {
            if (AgentApplication.currentAgentModel.getLevelEntity() != null) {
                this.tv_frg_mine_level_name.setText(AgentApplication.currentAgentModel.getLevelEntity().getName());
            }
            if (AgentApplication.currentAgentModel.getAgentEntity() != null) {
                this.tv_frg_mine_number.setText(
                        AgentApplication.currentAgentModel.getAgentEntity().getSpendCodes() + "/" +
                                AgentApplication.currentAgentModel.getAgentEntity().getLeftCodes() + "/" +
                                AgentApplication.currentAgentModel.getAgentEntity().getHaveCodes()
                );
                this.tv_frg_mine_id.setText("ID:" + AgentApplication.currentAgentModel.getAgentEntity().getId());
            }
        } else {
            this.tv_frg_mine_number.setText("0/0/0");
        }
    }

    @Event(value = R.id.bt_frg_mine_id_copy, type = View.OnClickListener.class)
    private void onCopyMarkAction(View view){
        ClipboardManager clipboardManager = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        String markId = this.tv_frg_mine_id.getText().toString();
        String mark = markId.substring(3,markId.length());//去掉 ID:
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mark));
        Toast.makeText(this.getContext(), R.string.string_copy_success, Toast.LENGTH_SHORT).show();
    }


    @Event(value = R.id.bt_frg_agent_rebate, type = View.OnClickListener.class)
    private void onRebateAction(View view) {
        Intent intent = new Intent(this.getContext(), RebateActivity.class);
        intent.putExtra(Constants.KEY_INTENT_AGENT, AgentApplication.currentAgentModel);
        this.getContext().startActivity(intent);
    }


    @Event(value = R.id.bt_frg_mine_bank, type = View.OnClickListener.class)
    private void onBankBindAction(View view) {
        Intent intent = new Intent(this.getContext(), BankActivity.class);
        intent.putExtra(Constants.KEY_INTENT_AGENT, AgentApplication.currentAgentModel);
        this.getContext().startActivity(intent);
    }

    @Event(value = R.id.bt_frg_mine_modify_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this.getContext(), ModifyActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_frg_mine_exit, type = View.OnClickListener.class)
    private void onExitAction(final View view) {
        view.setClickable(false);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(MineFragment.this.getContext());
        try {
            sessionModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPost(url, sessionModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onParamsError(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onOther(int code, String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }
            });
        } catch (Exception e) {
            view.setClickable(true);
            e.printStackTrace();
            SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
            Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
            MineFragment.this.getActivity().startActivity(intent);
            MineFragment.this.getActivity().finish();
        }
    }

    private void onModifyPasswordSuccessCallback(TokenModel sessionModel) {
        SharedPreferencesUtils.saveSession(this.getContext(), sessionModel);
    }
}
