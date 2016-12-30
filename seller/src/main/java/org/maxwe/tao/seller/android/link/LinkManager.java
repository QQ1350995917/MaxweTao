package org.maxwe.tao.seller.android.link;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Pengwei Ding on 2016-12-24 13:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 转链管理器
 */
public class LinkManager {

    /**
     * 在沙箱环境中转链商品
     * http://gw.api.tbsandbox.com/router/rest
     * https://gw.api.tbsandbox.com/router/rest
     */
    public static void convertGoodsLinkInSandBoxByTP(TPGoodsLinkConvertEntity entity){
        String url = "http://gw.api.tbsandbox.com/router/rest?";
        RequestParams params = new RequestParams(url);
        params.addParameter(null,entity);
        x.http().post(params, new Callback.CommonCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 在沙箱环境中转链商店
     */
    public static void convertShopLinkInSandBoxByTP(){

    }

    /**
     * 在正式环境中转链商店
     * http://gw.api.taobao.com/router/rest
     * https://eco.taobao.com/router/rest
     */
    public static void convertGoodsLinkInFormalByTP(TPGoodsLinkConvertEntity entity){

    }

    /**
     * 在正式环境中转链商店
     */
    public static void convertShopLinkInFormalByTP(){

    }


    private void post(String url){
    }
}
