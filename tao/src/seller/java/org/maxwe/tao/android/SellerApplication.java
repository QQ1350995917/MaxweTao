package org.maxwe.tao.android;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;

import org.maxwe.tao.android.account.user.UserEntity;

/**
 * Created by Pengwei Ding on 2017-01-07 17:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SellerApplication extends TaoApplication {

    public static UserEntity currentUserEntity = null;
    public static String SHARE_QQ_KEY = "1105924933";
    public static String SHARE_QQ_SECRET = "c0QsAczmqTW4Fu9i";
    public static String SHARE_WEI_XIN_KEY = "wxbb25593e790df366";
    public static String SHARE_WEI_XIN_SECRET = "0fe0d5fa022311dbd4b0189f5be18b5a";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
//        PlatformConfig.setSinaWeibo("3773570447", "101f664cd7cde741b2c57a978fb49bbd");
        CrashReport.initCrashReport(getApplicationContext(), "d5d328146a", true);
    }

}
