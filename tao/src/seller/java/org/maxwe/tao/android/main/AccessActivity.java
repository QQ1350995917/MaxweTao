package org.maxwe.tao.android.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.account.user.UserEntity;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-03 20:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_access)
public class AccessActivity extends BaseActivity {

    @ViewInject(R.id.tv_act_access_title)
    private TextView tv_act_access_title;
    @ViewInject(R.id.pb_act_access_progress)
    private ProgressBar pb_act_access_progress;
    @ViewInject(R.id.ll_act_access_result)
    private LinearLayout ll_act_access_result;
    @ViewInject(R.id.tv_act_access_fail_info)
    private TextView tv_act_access_fail_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        this.onRequestMyInfo();
    }

    private void onRequestMyInfo() {
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_mine);
        SessionModel session = SharedPreferencesUtils.getSession(this);
        NetworkManager.requestByPost(url, session, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                onRequestMyInfoSuccess(null);
            }

            @Override
            public void onLoginTimeout(String result) {
                SharedPreferencesUtils.clearSession(AccessActivity.this);
                Toast.makeText(AccessActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                onRequestMyInfoFail(AccessActivity.this.getString(R.string.string_toast_timeout));
            }

            @Override
            public void onAccessBad(String result) {
                onRequestMyInfoFail(AccessActivity.this.getString(R.string.string_toast_params));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(AccessActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                onRequestMyInfoFail(AccessActivity.this.getString(R.string.string_toast_network_error));
            }
        });
    }

    private void onRequestMyInfoFail(String result) {
        this.tv_act_access_title.setText(R.string.string_sorry);
        this.pb_act_access_progress.setVisibility(View.GONE);
        this.ll_act_access_result.setVisibility(View.VISIBLE);
        this.tv_act_access_fail_info.setText(result);
    }

    private void onRequestMyInfoSuccess(UserEntity userEntity) {
//        if (agentEntity.getGrantCode() == null) {
//            this.tv_act_access_title.setText(R.string.string_sorry);
//            this.pb_act_access_progress.setVisibility(View.GONE);
//            this.ll_act_access_result.setVisibility(View.VISIBLE);
//            this.tv_act_access_fail_info.setText(R.string.string_accessing);
//        } else {
//            Intent intent = new Intent();
//            intent.putExtra(Constants.KEY_SHARD_T_ACCOUNT, agentEntity);
//            AccessActivity.this.setResult(LoginActivity.RESPONSE_CODE_SUCCESS, intent);
//            AccessActivity.this.finish();
//        }
    }

    @Event(value = R.id.bt_act_access_result_action, type = View.OnClickListener.class)
    private void onReopenAction(View view) {
        AccessActivity.this.setResult(LoginActivity.RESPONSE_CODE_FAIL);
        AccessActivity.this.finish();
    }

}
