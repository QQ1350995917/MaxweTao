package org.maxwe.tao.android.api.goods;

import android.text.method.DateTimeKeyListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONObject;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.api.CommonEntity;
import org.maxwe.tao.android.api.ParamsSignFiller;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.DateTimeUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Pengwei Ding on 2017-01-08 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsManager {
    private static final String METHOD_NAME = "taobao.tbk.item.get";
    private static final String URL_FOMRAL = "http://gw.api.taobao.com/router/rest";
    private static final String URL_SANDBOX = "http://gw.api.tbsandbox.com/router/rest";

    public interface OnRequestCallback{
        void onSuccess(String text);
        void onError(Throwable ex,Object object);
    }

    private static Callback.Cancelable request(String url, final Object object, final OnRequestCallback onRequestCallback) {
        RequestParams requestParams = new RequestParams(url);
        String s = JSON.toJSONString(object);
        requestParams.addParameter(Constants.PARAMS, s);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onRequestCallback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                onRequestCallback.onError(ex, object);
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


    public static void queryGoods(GoodsQueryEntity goodsQueryEntity, GoodsManager.OnRequestCallback onRequestCallback) {
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setMethod(METHOD_NAME);
        goodsEntity.setApp_key(Constants.TAO_APP_KEY);
        goodsEntity.setTimestamp(DateTimeUtils.getCurrentFullTime());
        goodsEntity.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");

        goodsEntity.setQ(goodsQueryEntity.getQ());
        goodsEntity.setSort(goodsQueryEntity.getSort());
        goodsEntity.setPage_no(goodsQueryEntity.getPage_no());
        goodsEntity.setPage_size(goodsQueryEntity.getPage_size());

        HashMap<String, String> stringStringHashMap = JSON.parseObject(JSON.toJSONString(goodsEntity, new ParamsSignFiller()), new TypeReference<HashMap<String, String>>() {
        });
        try {
            goodsEntity.setSign(CommonEntity.signTopRequest(stringStringHashMap,Constants.TAO_APP_SECRET,goodsEntity.getSign_method()));
            request(URL_FOMRAL, goodsEntity, onRequestCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
