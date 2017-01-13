package org.maxwe.tao.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.account.user.UserEntity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.VersionActivity;
import org.maxwe.tao.android.index.IndexFragment;
import org.maxwe.tao.android.mine.MineFragment;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_PROXY = 0;
    public static final int REQUEST_CODE_CONVERT_LINK = 1;
    public static final int REQUEST_CODE_MODIFY_PASSWORD = 3;
    public static final int REQUEST_CODE_ACCESS_CHECK = 4;
    public static final int REQUEST_CODE_LOGIN_TIME_OUT = 5;

    private Fragment indexFragment;
    private Fragment linkFragment;
    private Fragment mineFragment;

    @ViewInject(R.id.rg_act_navigate)
    private RadioGroup rg_act_navigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        for (int index = 0; index < this.rg_act_navigate.getChildCount(); index++) {
            this.rg_act_navigate.getChildAt(index).setOnClickListener(this);
        }
        this.setCurrentFragment(R.id.rb_act_main_index);

        if (SellerApplication.currentUserEntity == null || SellerApplication.currentUserEntity.getActCode() == null) {
            Intent intent = new Intent(this, AccessActivity.class);
            this.startActivityForResult(intent, REQUEST_CODE_ACCESS_CHECK);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof RadioButton) {
            this.setCurrentFragment(v.getId());
        }
    }

    private void setCurrentFragment(int index) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        this.hideAllFragments(fragmentTransaction);
        switch (index) {
            case R.id.rb_act_main_index:
                if (this.indexFragment == null) {
                    this.indexFragment = new IndexFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.indexFragment);
                } else {
                    fragmentTransaction.show(this.indexFragment);
                }
                break;
            case R.id.rb_act_main_convert_link:
                if (this.linkFragment == null) {
                    this.linkFragment = new LinkFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.linkFragment);
                } else {
                    fragmentTransaction.show(this.linkFragment);
                }
                break;
            case R.id.rb_act_main_mine:
                if (this.mineFragment == null) {
                    this.mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.mineFragment);
                } else {
                    fragmentTransaction.show(this.mineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (this.indexFragment != null) {
            fragmentTransaction.hide(this.indexFragment);
        }
        if (this.linkFragment != null) {
            fragmentTransaction.hide(this.linkFragment);
        }
        if (this.mineFragment != null) {
            fragmentTransaction.hide(this.mineFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PROXY:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_CONVERT_LINK:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onConvertLinkSuccessCallback();
                }
                break;
            case REQUEST_CODE_MODIFY_PASSWORD:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onModifyPasswordSuccessCallback((SessionModel) data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                }
                break;
            case REQUEST_CODE_ACCESS_CHECK:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onRequestMyInfoCallback((UserEntity) data.getSerializableExtra(Constants.KEY_INTENT_SESSION));
                    this.onCheckNewVersion();
                } else {
                    this.finish();
                }
                break;

            case REQUEST_CODE_LOGIN_TIME_OUT:
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                this.finish();
                break;
            default:
                break;
        }
    }

    private void onConvertLinkSuccessCallback() {

    }

    private void onModifyPasswordSuccessCallback(SessionModel sessionModel) {
        SharedPreferencesUtils.saveSession(this, sessionModel);
    }

    private void onRequestMyInfoCallback(UserEntity userEntity) {
        SellerApplication.currentUserEntity = userEntity;

    }

    private void onResponseMineInfo(UserEntity userEntity){
        if (userEntity == null || TextUtils.isEmpty(userEntity.getActCode())){

        }
    }

    private void onCheckNewVersion() {
        VersionEntity versionModel = new VersionEntity(this.getString(R.string.platform), this.getResources().getInteger(R.integer.integer_app_type), this.getVersionCode());
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_version_version);
        NetworkManager.requestByPost(url, versionModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                VersionEntity versionEntity = JSON.parseObject(result, VersionEntity.class);
                versionCompare(versionEntity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    private void versionCompare(VersionEntity versionEntityFromServer) {
        if (versionEntityFromServer == null) {
            return;
        }
        VersionEntity currentVersionEntity = new VersionEntity(this.getString(R.string.platform), this.getResources().getInteger(R.integer.integer_app_type), this.getVersionCode());
        if (currentVersionEntity.equals(versionEntityFromServer) && versionEntityFromServer.getVersionCode() > currentVersionEntity.getVersionCode()) {
            Intent intent = new Intent(MainActivity.this, VersionActivity.class);
            intent.putExtra(VersionActivity.KEY_VERSION, versionEntityFromServer);
            MainActivity.this.startActivity(intent);
        }
    }

    protected int getVersionCode() {
        try {
            String packageName = this.getPackageName();
            int versionCode = this.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
