package org.maxwe.tao.proxy.android;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 13:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ProxyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
