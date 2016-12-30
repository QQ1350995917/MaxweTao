package org.maxwe.tao.seller.android;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.maxwe.tao.seller.android.link.TPGoodsLinkConvertEntity;
import org.maxwe.tao.seller.android.utils.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pengwei Ding on 2016-12-24 14:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class LinkConvertTest extends ApplicationTestCase<Application> {
    public LinkConvertTest() {
        super(Application.class);
    }

    public void testConvertGoodsLinkInSandBoxByTP(){
//        http://gw.api.taobao.com/router/rest?sign=9E9B85D30BB2FD96DE1438F3ED0547B5&timestamp=2016-12-24+14%3A51%3A14&v=2.0&app_key=12129701&method=taobao.tbk.item.share.convert&partner_id=top-apitools&format=xml&force_sensitive_param_fuzzy=true
        TPGoodsLinkConvertEntity tpGoodsLinkConvertEntity = new TPGoodsLinkConvertEntity();
        tpGoodsLinkConvertEntity.setMethod("taobao.tbk.item.share.convert");
        tpGoodsLinkConvertEntity.setApp_key("");
        tpGoodsLinkConvertEntity.setSession(null);
        tpGoodsLinkConvertEntity.setTimestamp(DateTime.getCurrentTime());
        tpGoodsLinkConvertEntity.setFields("num_iid,click_url"); // 需返回的字段列表
//        tpGoodsLinkConvertEntity.setNum_iids(); // 商品ID串，用','分割，从taobao.tbk.item.get接口获取num_iid字段
//        tpGoodsLinkConvertEntity.setSub_pid(); // 三方pid，满足mm_xxx_xxx_xxx格式
//        tpGoodsLinkConvertEntity.setUnid(); // 自定义输入串，英文和数字组成，长度不能大于12个字符，区分不同的推广渠道
//        tpGoodsLinkConvertEntity.setAdzone_id(); // 广告位ID，区分效果位置
        try {
            tpGoodsLinkConvertEntity.setSign(TPGoodsLinkConvertEntity.signTopRequest(object2Map(tpGoodsLinkConvertEntity),"",tpGoodsLinkConvertEntity.getSign_method()));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public void testConvertShopLinkInSandBoxByTP(){}
    public void testConvertGoodsLinkInFormalByTP(){}
    public void testConvertShopLinkInFormalByTP(){}

    private static Map<String, String> object2Map(Object object){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, String> map=new HashMap<String,String>();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (!entry.getKey().equals("sign")){
                map.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return map;
    }
}
