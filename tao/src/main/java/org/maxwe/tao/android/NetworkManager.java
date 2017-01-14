package org.maxwe.tao.android;

import android.util.Base64;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.CryptionUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-30 18:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 网络请求
 */
public class NetworkManager implements INetWorkManager {

    public static Callback.Cancelable requestByPost(String url, final Object object, final OnNetworkCallback onNetworkCallback) {
        final RequestParams requestParams = new RequestParams(url);
        String jsonParams = JSON.toJSONString(object);
        String encodeToString = Base64.encodeToString(jsonParams.getBytes(), Base64.NO_WRAP);
        String encryptionParams = CryptionUtils.parseByte2HexStr(CryptionUtils.encrypt(encodeToString));
        requestParams.addParameter(Constants.PARAMS, encryptionParams);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Response responseModel = JSON.parseObject(result, Response.class);
                if (responseModel.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()){
                    onNetworkCallback.onSuccess(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()){
                    onNetworkCallback.onParamsError(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
                    onNetworkCallback.onLoginTimeout(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()){
                    onNetworkCallback.onEmptyResult(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()){
                    onNetworkCallback.onAccessBad(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_TO_MANY.getCode()){
                    onNetworkCallback.onBusy(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_SEVER_ERROR.getCode()){
                    onNetworkCallback.onServerError(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_PARAMS_REPEAT.getCode()){
                    onNetworkCallback.onRepeat(responseModel.getData());
                    return;
                }

                onNetworkCallback.onOther(responseModel.getCode(),responseModel.getData());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onNetworkCallback.onError(ex,isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                onNetworkCallback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                onNetworkCallback.onFinished();
            }
        });
        return cancelable;
    }

    public static Callback.Cancelable requestByPostNoCryption(String url, final Object object, final OnNetworkCallback onNetworkCallback) {
        final RequestParams requestParams = new RequestParams(url);
        String jsonParams = JSON.toJSONString(object);
        requestParams.addParameter(Constants.PARAMS, jsonParams);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Response responseModel = JSON.parseObject(result, Response.class);
                if (responseModel.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()){
                    onNetworkCallback.onSuccess(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_PARAMS_BAD.getCode()){
                    onNetworkCallback.onParamsError(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
                    onNetworkCallback.onLoginTimeout(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()){
                    onNetworkCallback.onEmptyResult(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_ACCESS_BAD.getCode()){
                    onNetworkCallback.onAccessBad(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_TO_MANY.getCode()){
                    onNetworkCallback.onBusy(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_SEVER_ERROR.getCode()){
                    onNetworkCallback.onServerError(responseModel.getData());
                    return;
                }
                if (responseModel.getCode() == IResponse.ResultCode.RC_PARAMS_REPEAT.getCode()){
                    onNetworkCallback.onRepeat(responseModel.getData());
                    return;
                }

                onNetworkCallback.onOther(responseModel.getCode(),responseModel.getData());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onNetworkCallback.onError(ex,isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                onNetworkCallback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                onNetworkCallback.onFinished();
            }
        });
        return cancelable;
    }
}
