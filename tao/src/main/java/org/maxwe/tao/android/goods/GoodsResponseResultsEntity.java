package org.maxwe.tao.android.goods;

import org.maxwe.tao.android.response.IResponse;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-11 13:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsResponseResultsEntity implements IResponse {
    private List<GoodsEntity> n_tbk_item;

    public GoodsResponseResultsEntity() {
        super();
    }

    public List<GoodsEntity> getN_tbk_item() {
        return n_tbk_item;
    }

    public void setN_tbk_item(List<GoodsEntity> n_tbk_item) {
        this.n_tbk_item = n_tbk_item;
    }
}
