package org.maxwe.tao.android.api;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pengwei Ding on 2017-01-17 10:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TaoNetwork {
    public interface OnRequestCallback {
        void onSuccess(String text);

        void onError(Throwable ex, IModel object);
    }

    public static final String URL_FORMAL = "http://gw.api.taobao.com/router/rest";
    public static final String URL_SANDBOX = "http://gw.api.tbsandbox.com/router/rest";

    // 获取类目
    public static final String METHOD_NAME_ITEM = "taobao.itemcats.get"; // http://open.taobao.com/docs/api.htm?spm=a219a.7386793.0.0.owNYvV&apiId=122

    public static final String METHOD_NAME_GOODS = "taobao.tbk.item.get";


    public static Callback.Cancelable request(String url, final IModel model, final OnRequestCallback onRequestCallback) {
        RequestParams requestParams = new RequestParams(url);
        HashMap<String, String> hashMap = (HashMap<String, String>) JSON.parseObject(JSON.toJSONString(model), HashMap.class);
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            requestParams.addParameter(entry.getKey(), entry.getValue());
        }
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onRequestCallback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                onRequestCallback.onError(ex, model);
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
}
