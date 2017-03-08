package org.maxwe.tao.android.goods.alimama;

import org.maxwe.tao.android.response.ResponseModel;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-11 13:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 * 以阿里妈妈模型作为标准
 * 构建淘妈咪系统内的商品响应统一模型
 */
public class GoodsResponseModel extends ResponseModel<GoodsRequestModel> {
    private long pageIndex;//暂无
    private long pageSize;//暂无
    private long counter;//暂无
    private LinkedList<GoodsEntity> goodsEntities;

    public GoodsResponseModel() {
        super();
    }

    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public LinkedList<GoodsEntity> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(LinkedList<GoodsEntity> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }
}
