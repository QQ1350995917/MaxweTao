package org.maxwe.tao.android.index;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.maxwe.tao.android.goods.GoodsRequestModel;
import org.maxwe.tao.android.goods.GoodsResponseModel;
import org.maxwe.tao.android.goods.GoodsResponseResults;
import org.maxwe.tao.android.goods.GoodsResponseResultsEntity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IndexFragmentTaoView extends IndexFragmentView {
    private List<GoodsEntity> goodsEntities = new LinkedList<>();
    private BaseAdapter listAdapter = null;
    private int pageIndex = 0;
    private int pageSize = 20;

    private GoodsRequestModel goodsRequestModel = new GoodsRequestModel();

    public IndexFragmentTaoView(Context context) {
        super(context);
        this.init();
    }

    public IndexFragmentTaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public IndexFragmentTaoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(IndexFragmentTaoView.this.getContext());
        this.listAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return goodsEntities.size();
            }

            @Override
            public Object getItem(int position) {
                return goodsEntities.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                GoodsEntity goodsEntity = goodsEntities.get(position);
                View view = inflater.inflate(R.layout.include_index_fragment_goods, null);
                SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iv_inc_index_frg_item_goods_image);
                TextView title = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_title);
                TextView price = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_price);
                imageView.setImageURI(Uri.parse(goodsEntity.getPict_url()));
                title.setText(goodsEntity.getTitle());
                price.setText("￥" + goodsEntity.getReserve_price());
                return view;
            }
        };
        this.setListViewAdapter(this.listAdapter);
        this.onRefresh();
    }

    @Override
    public void onRefresh() {
        this.goodsEntities.clear();
        this.pageIndex = 1;
        this.showLoading();
        if (TextUtils.isEmpty(et_frg_index_search.getText().toString())){
            this.goodsRequestModel.setQ(null);
        }
        this.goodsRequestModel.setPage_no(this.pageIndex);
        this.goodsRequestModel.setPage_size(this.pageSize);
        this.onRequestTaoGoods(this.goodsRequestModel);
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
                this.pageIndex++;
                this.showLoading();

                this.goodsRequestModel.setPage_no(this.pageIndex);
                this.goodsRequestModel.setPage_size(this.pageSize);
                this.onRequestTaoGoods(goodsRequestModel);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GoodsEntity goodsEntity = this.goodsEntities.get(position);
        Intent intent = new Intent(this.getContext(), IndexFragmentGoodsDetailActivity.class);
        intent.putExtra(IndexFragmentGoodsDetailActivity.KEY_GOODS, goodsEntity);
        this.getContext().startActivity(intent);
    }

    private void onRequestTaoGoods(GoodsRequestModel goodsRequestModel) {
        String url = this.getContext().getString(R.string.string_url_domain) + this.getContext().getString(R.string.string_url_goods_query);
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
        goodsRequestModel.setT(sessionModel.getT());
        goodsRequestModel.setMark(sessionModel.getMark());
        goodsRequestModel.setCellphone(sessionModel.getCellphone());
        goodsRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        try {
            goodsRequestModel.setSign(sessionModel.getEncryptSing());
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
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(IndexFragmentTaoView.this.getContext());
                    SharedPreferencesUtils.clearAuthor(IndexFragmentTaoView.this.getContext());
                    Toast.makeText(IndexFragmentTaoView.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(IndexFragmentTaoView.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.goodsRequestModel.setQ(text);
        onRefresh();
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onBrokerageDefault(Button view) {
        this.resetSortDefault();
        view.setText("佣金");
        this.goodsRequestModel.setSort(null);
        onRefresh();
    }

    @Override
    protected void onBrokerageDown(Button view) {
        this.resetSortDefault();
        view.setText("佣金 ↓");
        this.goodsRequestModel.setSort(GoodsRequestModel.TK_RATE_DES);
        onRefresh();
    }

    @Override
    protected void onBrokerageUp(Button view) {
        this.resetSortDefault();
        view.setText("佣金 ↑");
        this.goodsRequestModel.setSort(GoodsRequestModel.TK_RATE_ASC);
        onRefresh();
    }

    @Override
    protected void onPriceDefault(Button view) {
        this.resetSortDefault();
        view.setText("价格");
    }

    @Override
    protected void onPriceDown(Button view) {
        this.resetSortDefault();
        view.setText("价格 ↓");
    }

    @Override
    protected void onPriceUp(Button view) {
        this.resetSortDefault();
        view.setText("价格 ↑");
    }

    @Override
    protected void onSaleDefault(Button view) {
        this.resetSortDefault();
        view.setText("销量");
        this.goodsRequestModel.setSort(null);
        onRefresh();
    }

    @Override
    protected void onSaleDown(Button view) {
        this.resetSortDefault();
        view.setText("销量 ↓");
        this.goodsRequestModel.setSort(GoodsRequestModel.TOTAL_SALES_DES);
        onRefresh();
    }

    @Override
    protected void onSaleUp(Button view) {
        this.resetSortDefault();
        view.setText("销量 ↑");
        this.goodsRequestModel.setSort(GoodsRequestModel.TOTAL_SALES_ASC);
        onRefresh();
    }

    @Override
    protected void onTicketDefault(Button view) {
        this.resetSortDefault();
        view.setText("优惠券");
    }

    @Override
    protected void onTicketDown(Button view) {
        this.resetSortDefault();
        view.setText("优惠券 ↓");
    }

    @Override
    protected void onTicketUp(Button view) {
        this.resetSortDefault();
        view.setText("优惠券 ↑");
    }
}
