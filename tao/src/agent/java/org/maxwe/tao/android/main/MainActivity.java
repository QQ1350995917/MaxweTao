package org.maxwe.tao.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.agent.AgentFragment;
import org.maxwe.tao.android.code.ActCodeFragment;
import org.maxwe.tao.android.mine.MineFragment;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 默认显示已经被激活的状态，在访问状态下进行校验
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PROXY = 0;
    private static final int REQUEST_CODE_TRADE = 1;


    private Fragment codeFragment;
    private Fragment agentFragment;
    private Fragment mineFragment;

    @ViewInject(R.id.rg_act_navigate)
    private RadioGroup rg_act_navigate;

    public static AgentEntity currentAgentEntity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        for (int index = 0; index < this.rg_act_navigate.getChildCount(); index++) {
            this.rg_act_navigate.getChildAt(index).setOnClickListener(this);
        }
        this.setCurrentFragment(R.id.rb_act_main_active_code);


//        this.onCheckNewVersion();
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
            case R.id.rb_act_main_active_code:
                if (this.codeFragment == null) {
                    this.codeFragment = new ActCodeFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.codeFragment);
                } else {
                    fragmentTransaction.show(this.codeFragment);
                }
                break;
            case R.id.rb_act_main_agent:
                if (this.agentFragment == null) {
                    this.agentFragment = new AgentFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.agentFragment);
                } else {
                    fragmentTransaction.show(this.agentFragment);
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
        if (this.codeFragment != null) {
            fragmentTransaction.hide(this.codeFragment);
        }
        if (this.agentFragment != null) {
            fragmentTransaction.hide(this.agentFragment);
        }
        if (this.mineFragment != null) {
            fragmentTransaction.hide(this.mineFragment);
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
            default:
                break;
        }
    }


}
