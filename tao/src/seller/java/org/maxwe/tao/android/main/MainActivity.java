package org.maxwe.tao.android.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.find.FindFragment;
import org.maxwe.tao.android.index.IndexFragment;
import org.maxwe.tao.android.mine.MineFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Fragment indexFragment;
    private Fragment findFragment;
    private Fragment mineFragment;
    private RadioGroup rg_act_navigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.rg_act_navigate = (RadioGroup) this.findViewById(R.id.rg_act_navigate);
        for (int index = 0; index < this.rg_act_navigate.getChildCount(); index++) {
            this.rg_act_navigate.getChildAt(index).setOnClickListener(this);
        }
        this.setCurrentFragment(R.id.rb_act_main_index);
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
            case R.id.rb_act_main_find:
                if (this.findFragment == null) {
                    this.findFragment = new FindFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.findFragment);
                } else {
                    fragmentTransaction.show(this.findFragment);
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
        if (this.findFragment != null) {
            fragmentTransaction.hide(this.findFragment);
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
}
