package org.maxwe.tao.android.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.account.model.LoginModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.VersionActivity;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_PROXY = 0;
    private static final int REQUEST_CODE_TRADE = 1;
    private static final int REQUEST_CODE_MODIFY_PASSWORD = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.onCheckNewVersion();
    }

    @Event(value = R.id.bt_act_main_my_proxy, type = View.OnClickListener.class)
    private void onMyProxyAction(View view) {
        Intent intent = new Intent(this, GrantActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_PROXY);
    }

    @Event(value = R.id.bt_act_main_proxy_code_trade, type = View.OnClickListener.class)
    private void onAccessCodeTradeAction(View view) {
        Intent intent = new Intent(this, org.maxwe.tao.android.main.TradeActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_TRADE);
    }

    @Event(value = R.id.bt_act_main_modify_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this, ModifyActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_act_main_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this);
        try {
            sessionModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPost(url, sessionModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"请求失败",Toast.LENGTH_SHORT).show();
        }
    }

//    private void onCheckNewVersion() {
//        VersionEntity currentVersionEntity = new VersionEntity(this.getString(R.string.platform), this.getResources().getInteger(R.integer.type_id), this.getVersionCode());
//        NetworkManager.requestNewVersion(currentVersionEntity, new NetworkManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()) {
//                    VersionEntity versionEntity = JSON.parseObject(response.getData(), VersionEntity.class);
//                    versionCompare(versionEntity);
//                }
//            }
//
//            @Override
//            public void onError(Throwable exception, Object object) {
//                exception.printStackTrace();
//            }
//        });
//    }

    private void versionCompare(VersionEntity versionEntityFromServer) {
        if (versionEntityFromServer == null) {
            return;
        }

//        VersionEntity currentVersionEntity = new VersionEntity(this.getString(R.string.platform), this.getResources().getInteger(R.integer.type_id), this.getVersionCode());
//        if (currentVersionEntity.equals(versionEntityFromServer) && versionEntityFromServer.getVersionCode() > currentVersionEntity.getVersionCode()) {
//            Intent intent = new Intent(MainActivity.this, VersionActivity.class);
//            intent.putExtra(VersionActivity.KEY_VERSION, versionEntityFromServer);
//            MainActivity.this.startActivity(intent);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PROXY:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_TRADE:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_MODIFY_PASSWORD:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onModifyPasswordSuccessCallback((SessionModel)data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                }
                break;
            default:
                break;
        }
    }

    private void onModifyPasswordSuccessCallback(SessionModel sessionModel) {
        SharedPreferencesUtils.saveSession(this,sessionModel);
    }
}
