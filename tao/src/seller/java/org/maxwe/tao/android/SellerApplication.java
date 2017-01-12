package org.maxwe.tao.android;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.maxwe.tao.android.account.user.UserEntity;

/**
 * Created by Pengwei Ding on 2017-01-07 17:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class SellerApplication extends TaoApplication {
    public static String TAO_APP_KEY = "23595494";
    public static String TAO_APP_SECRET = "6608da9c96be14e186ff485020892334";

    public static UserEntity currentUserEntity = null;


    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("1822462096", "5e10ac1205943d6ca705ad29e186a56b");
        PlatformConfig.setQQZone("1105614963", "loJZPmd3JonHncxw");
    }
}
