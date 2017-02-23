package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-02-22 21:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class TaoConvertRequestModel extends SessionModel {
    private String cookie;
    private String siteid;
    private String adzoneid;
    private String promotionURL;

    public TaoConvertRequestModel() {
        super();
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(String adzoneid) {
        this.adzoneid = adzoneid;
    }

    public String getPromotionURL() {
        return promotionURL;
    }

    public void setPromotionURL(String promotionURL) {
        this.promotionURL = promotionURL;
    }
}
