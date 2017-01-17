package org.maxwe.tao.android.api.item;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.maxwe.tao.android.api.CommonModel;
import org.maxwe.tao.android.api.ParamsSignFiller;
import org.maxwe.tao.android.api.TaoNetwork;
import org.maxwe.tao.android.utils.DateTimeUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Pengwei Ding on 2017-01-17 10:30.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class ItemManager {
    public static void requestItem(int parent_cid, TaoNetwork.OnRequestCallback onRequestCallback) {
        ItemRequestModel itemEntity = new ItemRequestModel();
        itemEntity.setMethod(TaoNetwork.METHOD_NAME_ITEM);
//        itemEntity.setApp_key(SellerApplication.TAO_APP_KEY);
        itemEntity.setTimestamp(DateTimeUtils.getCurrentFullTime());

        itemEntity.setParent_cid(parent_cid);
        HashMap<String, String> stringStringHashMap = JSON.parseObject(JSON.toJSONString(itemEntity, new ParamsSignFiller()), new TypeReference<HashMap<String, String>>() {
        });
        try {
//            itemEntity.setSign(CommonModel.signTopRequest(stringStringHashMap,SellerApplication.TAO_APP_SECRET,itemEntity.getSign_method()));
            TaoNetwork.request(TaoNetwork.URL_FORMAL, itemEntity, onRequestCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
