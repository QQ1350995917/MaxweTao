package org.maxwe.tao.android;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.maxwe.tao.android.account.user.UserEntity;

/**
 * Created by Pengwei Ding on 2017-01-07 17:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SellerApplication extends TaoApplication {

    public static UserEntity currentUserEntity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        Fresco.initialize(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3773570447", "101f664cd7cde741b2c57a978fb49bbd");
        PlatformConfig.setQQZone("1105614963", "loJZPmd3JonHncxw");

        CrashReport.initCrashReport(getApplicationContext(), "d5d328146a", true);
    }

}
