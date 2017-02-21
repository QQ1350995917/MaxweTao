package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.response.IResponse;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-11 13:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsResponseModel implements IResponse{
    private long pageIndex;
    private long pageSize;
    private long counter;
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
