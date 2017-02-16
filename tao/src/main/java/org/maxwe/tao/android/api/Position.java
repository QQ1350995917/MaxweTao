package org.maxwe.tao.android.api;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-15 13:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广位
 */
public class Position implements Serializable{
    private String siteId;
    private String id;
    private String name;
    private Promotion promotion;

    public Position() {
        super();
    }

    public Position(String siteId, String id, String name) {
        this.siteId = siteId;
        this.id = id;
        this.name = name;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public String toJsonString(){
        String jsonString = JSON.toJSONString(this);
        return jsonString;
    }
}
