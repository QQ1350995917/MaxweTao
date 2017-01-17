package org.maxwe.tao.android.api.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

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
//        goodsEntity.setApp_key(SellerApplication.TAO_APP_KEY);
        goodsEntity.setTimestamp(DateTimeUtils.getCurrentFullTime());
//        goodsEntity.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        goodsEntity.setFields("nick");

        goodsEntity.setQ(goodsQueryEntity.getQ());
        goodsEntity.setSort(goodsQueryEntity.getSort());
        goodsEntity.setPage_no(goodsQueryEntity.getPage_no());
        goodsEntity.setPage_size(goodsQueryEntity.getPage_size());

        HashMap<String, String> stringStringHashMap = JSON.parseObject(JSON.toJSONString(goodsEntity, new ParamsSignFiller()), new TypeReference<HashMap<String, String>>() {
        });
        try {
//            goodsEntity.setSign(CommonModel.signTopRequest(stringStringHashMap,SellerApplication.TAO_APP_SECRET,goodsEntity.getSign_method()));
            TaoNetwork.request(TaoNetwork.URL_FORMAL, goodsEntity, onRequestCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
