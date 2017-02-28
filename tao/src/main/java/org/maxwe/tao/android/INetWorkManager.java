package org.maxwe.tao.android;

import org.xutils.common.Callback;

/**
 * Created by Pengwei Ding on 2017-01-10 18:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public interface INetWorkManager {

//    String URL_USER_EXIST = Constants.DOMAIN + "/user/exist";
//    String URL_USER_LOST = Constants.DOMAIN + "/user/lost";
//    String URL_USER_REGISTER = Constants.DOMAIN + "/user/register";
//    String URL_USER_LOGIN = Constants.DOMAIN + "/user/login";
//    String URL_USER_LOGOUT = Constants.DOMAIN + "/user/logout";
//    String URL_USER_MINE = Constants.DOMAIN + "/user/mine";
//    String URL_USER_ACTIVE = Constants.DOMAIN + "/user/active";

    abstract class OnNetworkCallback implements Callback.CommonCallback<String>{

        public abstract void onSuccess(String result);

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }

        public void onParamsError(String result){}
        public void onEmptyResult(String result){}
        public void onRepeat(String result){}
        public void onAccessBad(String result){}
        public void onBusy(String result){}
        public void onServerError(String result){}
        public void onLoginTimeout(String result){}
        public void onOther(int code,String result){}
    }
}
