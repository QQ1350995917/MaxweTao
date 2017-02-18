package org.maxwe.tao.android.index;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.maxwe.tao.android.goods.GoodsRequestModel;
import org.maxwe.tao.android.goods.GoodsResponseModel;
import org.maxwe.tao.android.goods.GoodsResponseResults;
import org.maxwe.tao.android.goods.GoodsResponseResultsEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IndexFragmentMyView extends IndexFragmentView{
    private List<GoodsEntity> goodsEntities = new LinkedList<>();
    private BaseAdapter listAdapter = null;
    private int pageIndex = 0;
    private int pageSize = 20;

    public IndexFragmentMyView(Context context) {
        super(context);
        init();
    }

    public IndexFragmentMyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexFragmentMyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.getSearchBar().setVisibility(View.GONE);
        this.getToolsBarView().setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        this.goodsEntities.clear();
        this.pageIndex = 1;
        this.showLoading();
        GoodsRequestModel goodsRequestModel = new GoodsRequestModel();
        goodsRequestModel.setPage_no(this.pageIndex);
        goodsRequestModel.setPage_size(this.pageSize);
        //this.onRequestTaoGoods(goodsRequestModel);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                /**
                 * 页码++
                 * 请求数据
                 */
                this.pageIndex ++;
                this.showLoading();
                GoodsRequestModel goodsRequestModel = new GoodsRequestModel();
                goodsRequestModel.setPage_no(this.pageIndex);
                goodsRequestModel.setPage_size(this.pageSize);
                this.onRequestTaoGoods(goodsRequestModel);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void onRequestTaoGoods(GoodsRequestModel goodsRequestModel) {
        String url = this.getContext().getString(R.string.string_url_domain) + this.getContext().getString(R.string.string_url_tao_goods_query);
        goodsRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        NetworkManager.requestByPost(url, goodsRequestModel, new INetWorkManager.OnNetworkCallback() {
            @Override
            public void onSuccess(String result) {
                GoodsResponseModel responseModel = JSON.parseObject(result, GoodsResponseModel.class);
                if (responseModel != null) {
                    GoodsResponseResults tbk_item_get_response = responseModel.getTbk_item_get_response();
                    if (tbk_item_get_response != null) {
                        GoodsResponseResultsEntity results = tbk_item_get_response.getResults();
                        if (results != null) {
                            List<GoodsEntity> n_tbk_item = results.getN_tbk_item();
                            onRequestFinishBySuccess(n_tbk_item);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onRequestFinishByError();
            }
        });
    }

    private void onRequestFinishBySuccess(List<GoodsEntity> goodsEntities) {
        this.hiddenLoading();
        this.goodsEntities.addAll(goodsEntities);
        this.listAdapter.notifyDataSetChanged();
    }

    private void onRequestFinishByEmpty() {
        this.hiddenLoading();
    }

    private void onRequestFinishByError() {
        Toast.makeText(this.getContext(), "呃 出错了", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onQueryAction(Button view, String text) {

    }

    @Override
    protected void onBrokerageDefault(Button view) {

    }

    @Override
    protected void onBrokerageDown(Button view) {

    }

    @Override
    protected void onBrokerageUp(Button view) {

    }

    @Override
    protected void onPriceDefault(Button view) {

    }

    @Override
    protected void onPriceDown(Button view) {

    }

    @Override
    protected void onPriceUp(Button view) {

    }

    @Override
    protected void onSaleDefault(Button view) {

    }

    @Override
    protected void onSaleDown(Button view) {

    }

    @Override
    protected void onSaleUp(Button view) {

    }

    @Override
    protected void onTicketDefault(Button view) {

    }

    @Override
    protected void onTicketDown(Button view) {

    }

    @Override
    protected void onTicketUp(Button view) {

    }
}
