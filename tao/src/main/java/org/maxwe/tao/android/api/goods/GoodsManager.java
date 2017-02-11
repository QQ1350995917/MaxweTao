package org.maxwe.tao.android.api.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.api.CommonModel;
import org.maxwe.tao.android.api.ParamsSignFiller;
import org.maxwe.tao.android.api.TaoNetwork;
import org.maxwe.tao.android.utils.DateTimeUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Pengwei Ding on 2017-01-08 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsManager {
    public static void requestGoods(GoodsQueryEntity goodsQueryEntity, TaoNetwork.OnRequestCallback onRequestCallback) {
        GoodsRequestModel goodsEntity = new GoodsRequestModel();
        goodsEntity.setMethod(TaoNetwork.METHOD_NAME_GOODS);
        goodsEntity.setApp_key(SellerApplication.TAO_APP_KEY);
        goodsEntity.setTimestamp(DateTimeUtils.getCurrentFullTime());
        goodsEntity.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        goodsEntity.setQ("女装");
        goodsEntity.setCat("16");
        goodsEntity.setItemloc("杭州");
        goodsEntity.setSort("tk_rate_des");
        goodsEntity.setIs_tmall(false);
        goodsEntity.setIs_overseas(false);
        goodsEntity.setStart_price(0);
        goodsEntity.setEnd_price(100);
        goodsEntity.setStart_tk_rate(1000);
        goodsEntity.setEnd_tk_rate(1234);
        goodsEntity.setPlatform(1);
        goodsEntity.setPage_no(1);
        goodsEntity.setPage_size(20);

        HashMap<String, String> stringStringHashMap = JSON.parseObject(JSON.toJSONString(goodsEntity, new ParamsSignFiller()), new TypeReference<HashMap<String, String>>() {
        });
        try {
            goodsEntity.setSign(CommonModel.signTopRequest(stringStringHashMap, SellerApplication.TAO_APP_SECRET,goodsEntity.getSign_method()));
            TaoNetwork.request(TaoNetwork.URL_FORMAL, goodsEntity, onRequestCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
