package org.maxwe.tao.android.version;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.response.Response;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2017-01-06 18:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class VersionManager {

    private static final String URL_VERSION = Constants.DOMAIN + "/version/version";

    public interface OnRequestCallback {
        void onSuccess(Response response);

        void onError(Throwable exception, VersionEntity versionEntity);
    }

    private static Callback.Cancelable request(String url, final VersionEntity versionEntity, final OnRequestCallback onCreateCallback) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter(Constants.PARAMS, JSON.toJSONString(versionEntity));
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onCreateCallback.onSuccess(JSONObject.parseObject(result, Response.class));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                onCreateCallback.onError(ex, versionEntity);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        return cancelable;
    }

    public static Callback.Cancelable checkNewVersion(VersionEntity versionEntity, OnRequestCallback onCreateCallback) {
        return request(URL_VERSION, versionEntity, onCreateCallback);
    }
}
