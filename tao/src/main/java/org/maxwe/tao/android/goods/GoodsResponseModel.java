package org.maxwe.tao.android.goods;

/**
 * Created by Pengwei Ding on 2017-02-11 13:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class GoodsResponseModel {

    private GoodsResponseResults tbk_item_get_response;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public GoodsResponseResults getTbk_item_get_response() {
        return tbk_item_get_response;
    }

    public void setTbk_item_get_response(GoodsResponseResults tbk_item_get_response) {
        this.tbk_item_get_response = tbk_item_get_response;
    }




}
