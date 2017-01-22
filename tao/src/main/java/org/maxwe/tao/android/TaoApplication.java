package org.maxwe.tao.android;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 13:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TaoApplication extends Application {

    public static String TAO_APP_KEY = "23595494";
    public static String TAO_APP_SESSION = "610002895f444607e40d5d4a524d84a591f210a75c143ee837058645";
    public static String TAO_APP_REFRESH_TOKEN = "61004289471dbf45b52aa40e248da1a144ef15a35f2550b837058645";
    public static String TAO_APP_SECRET = "6608da9c96be14e186ff485020892334";


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
