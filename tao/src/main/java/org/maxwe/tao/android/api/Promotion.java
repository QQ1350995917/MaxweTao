package org.maxwe.tao.android.api;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-15 13:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 导购推广
 */
public class Promotion implements Serializable {
    private String siteId;
    private String name;

    public Promotion() {
        super();
    }

    public Promotion(String siteId, String name) {
        this.siteId = siteId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Promotion) {
            Promotion promotion = (Promotion) o;
            if (promotion.getSiteId().equals(this.getSiteId())) {
                return true;
            } else {
                return false;
            }
        }
        return super.equals(o);
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJsonString(){
        String jsonString = JSON.toJSONString(this);
        return jsonString;
    }
}
