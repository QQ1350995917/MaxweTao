package org.maxwe.tao.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-06 19:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_version)
public class VersionActivity extends BaseActivity {

    public static final String KEY_VERSION = "KEY_VERSION";
    private VersionEntity versionEntity = null;

    @ViewInject(R.id.tv_act_version_title)
    private TextView tv_act_version_title;
    @ViewInject(R.id.tv_act_version_info)
    private TextView tv_act_version_info;
    @ViewInject(R.id.tv_act_version_upgrade)
    private TextView tv_act_version_upgrade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.versionEntity = (VersionEntity) this.getIntent().getExtras().get(KEY_VERSION);
        if (this.versionEntity.getUpgrade() == 0) {
            this.setFinishOnTouchOutside(true);
            this.tv_act_version_upgrade.setVisibility(View.GONE);
        } else {
            this.setFinishOnTouchOutside(false);
            this.tv_act_version_upgrade.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.tv_act_version_title.setText(this.getString(R.string.string_new_version) + ":" + this.versionEntity.getVersionName());
        this.tv_act_version_info.setText(this.versionEntity.getInformation());
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
