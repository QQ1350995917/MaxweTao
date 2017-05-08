package org.maxwe.tao.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 13:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
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

    protected String getVersionName() {
        try {
            String packageName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(packageName, 0).versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getEMOJIStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
