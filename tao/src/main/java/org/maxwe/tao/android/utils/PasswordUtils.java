package org.maxwe.tao.android.utils;

/**
 * Created by Pengwei Ding on 2017-03-07 21:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class PasswordUtils {
    /**
     * 识别明文密码是否符合系统规定
     * @param password 明文密码
     * @return
     */
    public static boolean isPlainPasswordOk(String password) {
        if (!StringUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 12) {
            return true;
        }
        return false;
    }
}
