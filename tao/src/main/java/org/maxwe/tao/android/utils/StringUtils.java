package org.maxwe.tao.android.utils;

import android.text.TextUtils;
import android.view.TextureView;

/**
 * Created by Pengwei Ding on 2017-03-07 21:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class StringUtils {
    public static boolean isEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public static Boolean equals(String string1, String string2){
        return TextUtils.equals(string1,string2);
    }
}
