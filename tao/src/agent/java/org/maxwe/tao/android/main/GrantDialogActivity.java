package org.maxwe.tao.android.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-05 14:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_grant_dialog)
public class GrantDialogActivity extends BaseActivity {

    public static final String KEY_CELLPHONE = "KEY_CELLPHONE";

    @ViewInject(R.id.tv_act_grant_dlg_cellphone)
    private TextView tv_act_grant_dlg_cellphone;

    @ViewInject(R.id.ll_act_grant_dlg_status_normal)
    private LinearLayout ll_act_grant_dlg_status_normal;

    @ViewInject(R.id.bt_act_grant_dlg_cancel)
    private Button bt_act_grant_dlg_cancel;

    @ViewInject(R.id.bt_act_grant_dlg_confirm)
    private Button bt_act_grant_dlg_confirm;

    @ViewInject(R.id.pb_act_grant_dlg_progress)
    private ProgressBar pb_act_grant_dlg_progress;

    @ViewInject(R.id.ll_act_grant_dlg_status_result)
    private LinearLayout ll_act_grant_dlg_status_result;

    @ViewInject(R.id.tv_act_grant_dlg_result)
    private TextView tv_act_grant_dlg_result;

    @ViewInject(R.id.bt_act_grant_dlg_result_confirm)
    private Button bt_act_grant_dlg_result_confirm;

    private String cellphone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        this.cellphone = this.getIntent().getExtras().getString(KEY_CELLPHONE, null);

        this.tv_act_grant_dlg_cellphone.setText(
                this.getString(R.string.string_grant_cellphone_dialog_title1) +
                        this.cellphone +
                        this.getString(R.string.string_grant_cellphone_dialog_title2)
        );

        this.showNormalStatus();
    }

    private void showNormalStatus() {
        this.ll_act_grant_dlg_status_normal.setVisibility(View.VISIBLE);
        this.pb_act_grant_dlg_progress.setVisibility(View.GONE);
        this.ll_act_grant_dlg_status_result.setVisibility(View.GONE);
    }

    private void showGoingStatus() {
        this.ll_act_grant_dlg_status_normal.setVisibility(View.GONE);
        this.pb_act_grant_dlg_progress.setVisibility(View.VISIBLE);
        this.ll_act_grant_dlg_status_result.setVisibility(View.GONE);
    }


    private void showResultStatus(String result) {
        this.tv_act_grant_dlg_result.setText(result);
        this.ll_act_grant_dlg_status_normal.setVisibility(View.GONE);
        this.pb_act_grant_dlg_progress.setVisibility(View.GONE);
        this.ll_act_grant_dlg_status_result.setVisibility(View.VISIBLE);
    }

    @Event(value = R.id.bt_act_grant_dlg_cancel, type = View.OnClickListener.class)
    private void onNormalCalcelAction(View view) {
        this.finish();
    }

    @Event(value = R.id.bt_act_grant_dlg_confirm, type = View.OnClickListener.class)
    private void onNormalConfirmAction(View view) {
        this.showGoingStatus();
        this.onGrantConfirmAction();
    }

    @Event(value = R.id.bt_act_grant_dlg_result_confirm, type = View.OnClickListener.class)
    private void onNormalResultConfirmAction(View view) {
        this.finish();
    }

    private void onGrantActionSuccess() {
        this.finish();
    }


    private void onGrantConfirmAction() {
//        AgentEntity agentEntity = new AgentEntity(accountCellphone, null, this.getResources().getInteger(R.integer.type_id));
//        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
//        agentEntityInter.setT(key);
//        TradeAgentModel tradeAgentModel = new TradeAgentModel(agentEntityInter);
//        tradeAgentModel.setTradeCode(1);
//        AgentEntity authorizedAgent = new AgentEntity(cellphone, null, -1);
//        tradeAgentModel.setAuthorizedAgent(authorizedAgent);
//        NetworkManager.requestGrant(tradeAgentModel, new NetworkManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()) {
//                    Toast.makeText(GrantDialogActivity.this, R.string.string_grant_cellphone_no_exist, Toast.LENGTH_SHORT).show();
//                    showResultStatus(GrantDialogActivity.this.getString(R.string.string_grant_cellphone_no_exist));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_PARAMS_REPEAT.getCode()) {
//                    Toast.makeText(GrantDialogActivity.this, R.string.string_grant_cellphone_repeat, Toast.LENGTH_SHORT).show();
//                    showResultStatus(GrantDialogActivity.this.getString(R.string.string_grant_cellphone_repeat));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()) {
//                    Toast.makeText(GrantDialogActivity.this, R.string.string_agents_no_code, Toast.LENGTH_SHORT).show();
//                    showResultStatus(GrantDialogActivity.this.getString(R.string.string_agents_no_code));
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
//                    Toast.makeText(GrantDialogActivity.this, R.string.string_grant_cellphone_success, Toast.LENGTH_SHORT).show();
//                    onGrantActionSuccess();
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
//                    Toast.makeText(GrantDialogActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Toast.makeText(GrantDialogActivity.this, R.string.string_grant_cellphone_fail, Toast.LENGTH_SHORT).show();
//                showResultStatus(GrantDialogActivity.this.getString(R.string.string_grant_cellphone_fail));
//            }
//
//            @Override
//            public void onError(Throwable exception, Object agentEntity) {
//                Toast.makeText(GrantDialogActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
//                showResultStatus(GrantDialogActivity.this.getString(R.string.string_toast_network_error));
//            }
//        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
