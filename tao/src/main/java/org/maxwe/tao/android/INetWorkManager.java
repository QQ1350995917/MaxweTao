package org.maxwe.tao.android;

import org.xutils.common.Callback;

/**
 * Created by Pengwei Ding on 2017-01-10 18:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public interface INetWorkManager {
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
