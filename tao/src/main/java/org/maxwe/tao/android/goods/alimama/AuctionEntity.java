package org.maxwe.tao.android.goods.alimama;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-02-24 23:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AuctionEntity implements Serializable {
    private String taoToken; //普通淘口令 "￥qeerQDT9Yv￥",
    private String shortLinkUrl; //普通下单短链接 "https; ////s.click.taobao.com/qlszN4x"
    private String clickUrl; //普通下单长链接 "https; ////s.click.taobao.com/t?e=m%3D2%26s%3DBHORaY%2FkQIAcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67g3ctxOV77STVkTGlWTgx8mpGS2VpSR8CDGf4oUkuTo6Onj5OL8jyTtp%2FeNiFqJpc7W0fhYlkC0W9%2FGPya%2FaA%2F3196L10uEQasa1OfP%2F%2BC3YIYULNg46oBA%3D&pvid=10_192.168.0.108_11845_1487937140648",

    private String couponLinkTaoToken; //带有优惠券的淘口令 "￥LgwLQDTm4R￥",
    private String couponShortLinkUrl; //带有优惠券的短连接 "https; ////s.click.taobao.com/njrzN4x",
    private String couponLink; //带有优惠券的长链接 "https; ////uland.taobao.com/coupon/edetail?e=rtHR9vZCMa4N%2BoQUE6FNzCQqh7tpEjRc49GZJg1OxP5vuDyo2aYECXjmZFopFXuGxRbCZ7O2c7zPujuOTVJagcKUixUTTLeu7sRUcQe1PUfqi5veOGb%2BCkrmWsy3CZf4baUu0D%2BdZgZLLb6DQgj%2F3tkQEDMVO%2BCu&pid=mm_120134623_21454360_72044740&af=1",

    private String qrCodeUrl; //二维码 "http; ////gqrcode.alicdn.com/img?type=hv&text=https%3A%2F%2Fs.click.taobao.com%2F6FtzN4x&h=300&w=300",
    private String type; // "auction",


    public AuctionEntity() {
        super();
    }

    public String getTaoToken() {
        return taoToken;
    }

    public void setTaoToken(String taoToken) {
        this.taoToken = taoToken;
    }

    public String getCouponShortLinkUrl() {
        return couponShortLinkUrl;
    }

    public void setCouponShortLinkUrl(String couponShortLinkUrl) {
        this.couponShortLinkUrl = couponShortLinkUrl;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getCouponLinkTaoToken() {
        return couponLinkTaoToken;
    }

    public void setCouponLinkTaoToken(String couponLinkTaoToken) {
        this.couponLinkTaoToken = couponLinkTaoToken;
    }

    public String getCouponLink() {
        return couponLink;
    }

    public void setCouponLink(String couponLink) {
        this.couponLink = couponLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortLinkUrl() {
        return shortLinkUrl;
    }

    public void setShortLinkUrl(String shortLinkUrl) {
        this.shortLinkUrl = shortLinkUrl;
    }
}
