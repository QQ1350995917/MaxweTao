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
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2017-01-05 14:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AgentDialog extends Dialog implements View.OnClickListener {

    private OnKeyListener onKeyListener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    @ViewInject(R.id.rl_dlg_agent_grant_before)
    private RelativeLayout rl_dlg_agent_grant_before;
    private TextView tv_dlg_agent_cellphone;
    private Button bt_dlg_agent_cancel;
    private Button bt_dlg_agent_confirm;
    @ViewInject(R.id.rl_dlg_agent_grant_going)
    private RelativeLayout rl_dlg_agent_grant_going;
    @ViewInject(R.id.rl_dlg_agent_grant_result)
    private RelativeLayout rl_dlg_agent_grant_result;
    private TextView tv_dlg_agent_result;

    private String cellphone;

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
        this.setOnKeyListener(this.onKeyListener);
//        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.dialog_agent);
        this.rl_dlg_agent_grant_before = (RelativeLayout) this.findViewById(R.id.rl_dlg_agent_grant_before);
        this.tv_dlg_agent_cellphone = (TextView) this.findViewById(R.id.tv_dlg_agent_cellphone);
        this.bt_dlg_agent_cancel = (Button) this.findViewById(R.id.bt_dlg_agent_cancel);
        this.bt_dlg_agent_cancel.setOnClickListener(this);
        this.bt_dlg_agent_confirm = (Button) this.findViewById(R.id.bt_dlg_agent_confirm);
        this.bt_dlg_agent_confirm.setOnClickListener(this);
        this.rl_dlg_agent_grant_going = (RelativeLayout) this.findViewById(R.id.rl_dlg_agent_grant_going);
        this.rl_dlg_agent_grant_result = (RelativeLayout) this.findViewById(R.id.rl_dlg_agent_grant_result);
        this.tv_dlg_agent_result = (TextView) this.findViewById(R.id.tv_dlg_agent_result);
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void onGrantConfirmAction(String cellphone) {
        this.rl_dlg_agent_grant_before.setVisibility(View.GONE);
        this.rl_dlg_agent_grant_going.setVisibility(View.VISIBLE);
        this.rl_dlg_agent_grant_result.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = AgentDialog.this.getContext().getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        String accountCellphone = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);

        AgentEntity agentEntity = new AgentEntity(accountCellphone,null,AgentDialog.this.getContext().getResources().getInteger(R.integer.type_id));
        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
        agentEntityInter.setT(key);

        AgentEntity authorizedAgent = new AgentEntity(cellphone,null,-1);
        AgentEntityInter authorizedAgentInter = new AgentEntityInter(authorizedAgent);



        AgentManager.requestGrant(agentEntityInter, new AgentManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_no_exist, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_REPEAT.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_repeat, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
                    Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_success, Toast.LENGTH_SHORT).show();
                    AgentDialog.this.dismiss();
                    return;
                }

                Toast.makeText(AgentDialog.this.getContext(), R.string.string_grant_cellphone_fail, Toast.LENGTH_SHORT).show();
                rl_dlg_agent_grant_before.setVisibility(View.GONE);
                rl_dlg_agent_grant_going.setVisibility(View.GONE);
                rl_dlg_agent_grant_result.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable exception, AgentEntity agentEntity) {
                Toast.makeText(AgentDialog.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                tv_dlg_agent_result.setText(R.string.string_toast_network_error);
                rl_dlg_agent_grant_before.setVisibility(View.GONE);
                rl_dlg_agent_grant_going.setVisibility(View.GONE);
                rl_dlg_agent_grant_result.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        this.setTitle(
                this.getContext().getString(R.string.string_grant_cellphone_dialog_title1)
                        + this.cellphone
                        + this.getContext().getString(R.string.string_grant_cellphone_dialog_title2));
        this.rl_dlg_agent_grant_before.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dlg_agent_cancel:
                this.dismiss();
                break;
            case R.id.bt_dlg_agent_confirm:
                onGrantConfirmAction(this.cellphone);
                break;
            case R.id.bt_dlg_agent_result_confirm:
                this.dismiss();
                break;
        }
    }
}
