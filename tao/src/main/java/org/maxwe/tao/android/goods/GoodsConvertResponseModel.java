package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.response.IResponse;

/**
 * Created by Pengwei Ding on 2017-02-23 16:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsConvertResponseModel implements IResponse {
    private TaoConvertResponseModel convert;
    private GoodsEntity goodsEntity;

    public GoodsConvertResponseModel() {
        super();
    }

    public TaoConvertResponseModel getConvert() {
        return convert;
    }

    public void setConvert(TaoConvertResponseModel convert) {
        this.convert = convert;
    }

    public GoodsEntity getGoodsEntity() {
        return goodsEntity;
    }

    public void setGoodsEntity(GoodsEntity goodsEntity) {
        this.goodsEntity = goodsEntity;
    }
}
