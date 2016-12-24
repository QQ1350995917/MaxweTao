package org.maxwe.tao.android.mine;

/**
 * Created by Pengwei Ding on 2016-12-23 10:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public interface IUserManager {

    boolean isLogin(UserEntity userEntity);

    <E extends UserEntity> E getUser();

    <E extends UserEntity> E updatePassword(UserEntity userEntity);

}
