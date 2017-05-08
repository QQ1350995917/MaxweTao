package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.version.VersionEntity;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2017-02-27 21:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void onCheckNewVersion(){
        VersionEntity versionModel = new VersionEntity(this.getString(R.string.platform), this.getResources().getInteger(R.integer.integer_app_type), this.getVersionCode());
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_version_version);
        NetworkManager.requestByPostNoCryption(url, versionModel, new INetWorkManager.OnNetworkCallback() {
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
            Intent intent = new Intent(this, VersionActivity.class);
            intent.putExtra(VersionActivity.KEY_VERSION, versionEntityFromServer);
            this.startActivity(intent);
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
