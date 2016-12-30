package org.maxwe.tao.proxy.android.mine;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.proxy.android.common.Constants;
import org.maxwe.tao.proxy.android.response.Response;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 14:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IdentifyManager {

    private static final String REGISTER_URL = Constants.DOMAIN + "/proxy/create";

    public interface OnRegisterCallback{
        void onSuccess(RegisterEntity registerEntity);
        void onError(Throwable exception);
    }

    public static RegisterEntity register(final RegisterEntity registerEntity,final OnRegisterCallback onRegisterCallback){
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter(Constants.PARAMS, JSON.toJSONString(registerEntity));
        Callback.Cancelable cancelable = x.http().get(requestParams,new Callback.CommonCallback<Response>() {
            @Override
            public void onSuccess(Response result) {
                registerEntity.setT(result.getData());
                onRegisterCallback.onSuccess(registerEntity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onRegisterCallback.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return null;
    }

}
