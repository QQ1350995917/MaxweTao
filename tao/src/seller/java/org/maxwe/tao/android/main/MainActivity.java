package org.maxwe.tao.android.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.index.IndexFragment;
import org.maxwe.tao.android.mine.MineFragment;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_PROXY = 0;
    public static final int REQUEST_CODE_CONVERT_LINK = 1;
    public static final int REQUEST_CODE_MODIFY_PASSWORD = 3;

    private Fragment indexFragment;
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

        Dialog dialog = new AccessDialog(this);
        dialog.show();


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
        if (this.mineFragment != null) {
            fragmentTransaction.hide(this.mineFragment);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
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
                    onModifyPasswordSuccessCallback(data.getStringExtra(Constants.T));
                }
                break;
            default:
                break;
        }
    }

    private void onConvertLinkSuccessCallback(){

    }

    private void onModifyPasswordSuccessCallback(String token) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.KEY_SHARD_T_CONTENT, token);
        edit.commit();
    }
}
