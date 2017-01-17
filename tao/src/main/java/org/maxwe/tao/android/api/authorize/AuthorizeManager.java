package org.maxwe.tao.android.api.authorize;

import org.maxwe.tao.android.api.TaoNetwork;

/**
 * Created by Pengwei Ding on 2017-01-17 14:06.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 用户授权
 * http://open.taobao.com/docs/doc.htm?spm=a219a.7386781.3.8.2dQ1F4&docType=1&articleId=102635&treeId=1
 * 正式环境：https://oauth.taobao.com/authorize
 * 沙箱环境：https://oauth.tbsandbox.com/authorize
 */
public class AuthorizeManager {

    public static final String URL_FORMAL = "https://oauth.taobao.com/authorize";
    public static final String URL_SANDBOX = "https://oauth.tbsandbox.com/authorize";

    public static void authorize(AuthorRequestModel authorRequestModel, TaoNetwork.OnRequestCallback onRequestCallback) {
        TaoNetwork.request(URL_FORMAL, authorRequestModel, onRequestCallback);
    }
}
