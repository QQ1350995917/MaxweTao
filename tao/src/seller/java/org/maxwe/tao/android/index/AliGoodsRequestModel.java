package org.maxwe.tao.android.index;

import org.maxwe.tao.android.account.model.SessionModel;

/**
 * Created by Pengwei Ding on 2017-02-24 22:04.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AliGoodsRequestModel extends SessionModel {
    private long toPage = 1;
    private long perPageSize = 20;
    private String q;
    private String cookie;
    private int sortType = 0;// 0:默认 1:佣金 2:优惠券 3:价格降低 4:价格升高 9:销量降序
    private int urlType = 0;

    public AliGoodsRequestModel() {
        super();
    }

    public AliGoodsRequestModel(SessionModel sessionModel, long toPage, long perPageSize, String q, String cookie,int urlType) {
        super(sessionModel.getT(), sessionModel.getMark(), sessionModel.getCellphone(), sessionModel.getApt());
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
}
