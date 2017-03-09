package org.maxwe.tao.android.goods.alimama;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-02-24 23:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 转链请求模型
 */
public class AuctionRequestModel extends TokenModel {
    private long siteid;  // 21454360;
    private long auctionid;  // 521477762631; 商品ID
    private long adzoneid;  // 72044740;
    private String cookie;

    public AuctionRequestModel() {
        super();
    }

    public AuctionRequestModel(TokenModel sessionModel, long siteid, long auctionid, long adzoneid, String cookie) {
        super(sessionModel.getT(), sessionModel.getId(), sessionModel.getCellphone(), sessionModel.getApt());
        this.siteid = siteid;
        this.auctionid = auctionid;
        this.adzoneid = adzoneid;
        this.cookie = cookie;
    }

    public long getSiteid() {
        return siteid;
    }

    public void setSiteid(long siteid) {
        this.siteid = siteid;
    }

    public long getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(long auctionid) {
        this.auctionid = auctionid;
    }

    public long getAdzoneid() {
        return adzoneid;
    }

    public void setAdzoneid(long adzoneid) {
        this.adzoneid = adzoneid;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
