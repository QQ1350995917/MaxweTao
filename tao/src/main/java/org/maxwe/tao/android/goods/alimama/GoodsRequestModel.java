package org.maxwe.tao.android.goods.alimama;

import org.maxwe.tao.android.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-01-08 11:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 以阿里妈妈模型作为标准
 * 构建淘妈咪系统内的商品请求统一模型
 */
public class GoodsRequestModel extends TokenModel {

    private long toPage = 1;// 页面
    private long perPageSize = 20;// 页面数据量
    private String q; // 查询关键字
    private String cookie; // 登录淘宝后产生的cookie
    private int sortType = 0;// 0:默认 7:佣金高到低 3:价格高到低 4:价格低到高 9:销量高到低
    private int urlType = 0;//标记链接类型 0:淘宝商品 1:高佣金商品 2:站内商品
    private int dpyhq = 0;// 1店铺优惠券 其他无
    private int userType = -1;// 0 淘宝，1天猫


    public GoodsRequestModel() {
        super();
    }

    public GoodsRequestModel(TokenModel tokenModel) {
        super(tokenModel);
    }

    public GoodsRequestModel(TokenModel tokenModel, long toPage, long perPageSize, String q, String cookie, int urlType) {
        super(tokenModel);
        this.toPage = toPage;
        this.perPageSize = perPageSize;
        this.q = q;
        this.cookie = cookie;
        this.urlType = urlType;
    }

    public long getToPage() {
        return toPage;
    }

    public void setToPage(long toPage) {
        this.toPage = toPage;
    }

    public long getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(long perPageSize) {
        this.perPageSize = perPageSize;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public int getDpyhq() {
        return dpyhq;
    }

    public void setDpyhq(int dpyhq) {
        this.dpyhq = dpyhq;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
