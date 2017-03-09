package org.maxwe.tao.android.goods.alimama;

import org.maxwe.tao.android.response.ResponseModel;

/**
 * Created by Pengwei Ding on 2017-03-08 21:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 转链响应模型
 */
public class AuctionResponseModel extends ResponseModel<AuctionRequestModel> {
    private AuctionEntity auction;

    public AuctionResponseModel() {
        super();
    }

    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
    }
}
