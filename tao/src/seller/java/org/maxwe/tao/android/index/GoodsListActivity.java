package org.maxwe.tao.android.index;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.maxwe.tao.android.goods.GoodsRequestModel;
import org.maxwe.tao.android.goods.GoodsResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-20 15:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_goods)
public class GoodsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener ,SearchView.OnQueryTextListener {
    @ViewInject(R.id.sv_act_goods_search)
    private SearchView sv_act_goods_search;
    @ViewInject(R.id.srl_act_goods_swipe_container)
    private SwipeRefreshLayout srl_act_goods_swipe_container;
    @ViewInject(R.id.lv_act_goods_container)
    private ListView lv_act_goods_container;

    private List<GoodsEntity> goodsEntities = new LinkedList<>();
    private BaseAdapter listAdapter = null;
    private int pageIndex = 0;
    private int pageSize = 20;

    private GoodsRequestModel goodsRequestModel = new GoodsRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(this);
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
                TextView hasCoupon = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_is_coupon);
                TextView price = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_price);
                TextView coupon = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_coupon);
                TextView couponPrice = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_coupon_price);
                if (Long.parseLong(goodsEntity.getCoupon_info()) > 0){
                    hasCoupon.setVisibility(View.VISIBLE);
                    coupon.setVisibility(View.VISIBLE);
                    couponPrice.setVisibility(View.VISIBLE);
                    couponPrice.setText(Long.parseLong(goodsEntity.getCoupon_info()) + "元");
                }

                TextView priceReserve = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_price_reserve);
                priceReserve.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                TextView brokerage = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_brokerage);
                TextView brokerageGot = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_brokerage_got);
                TextView sale = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_sale);
                imageView.setImageURI(Uri.parse(goodsEntity.getPict_url()));
                title.setText(goodsEntity.getTitle());
                price.setText("￥" + goodsEntity.getZk_final_price());
                priceReserve.setText("￥" + goodsEntity.getReserve_price());
                brokerage.setText(goodsEntity.getCommission_rate() + "%");
                brokerageGot.setText("赚" + new DecimalFormat("###.00").format(Float.parseFloat(goodsEntity.getZk_final_price()) * Float.parseFloat(goodsEntity.getCommission_rate()) / 100) + "元");
                sale.setText("月销:" + goodsEntity.getVolume());
                return view;
            }
        };

        this.lv_act_goods_container.setAdapter(this.listAdapter);
        this.sv_act_goods_search.setOnQueryTextListener(this);
        this.srl_act_goods_swipe_container.setOnRefreshListener(this);
        this.lv_act_goods_container.setOnItemClickListener(this);
        this.lv_act_goods_container.setOnScrollListener(this);
        this.onRefresh();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.goodsRequestModel.setQ(query);
        onRefresh();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null){
            imm.hideSoftInputFromWindow(this.sv_act_goods_search.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    public void onRefresh() {
        this.goodsEntities.clear();
        this.pageIndex = 1;
        this.showLoading();
        if (TextUtils.isEmpty(this.sv_act_goods_search.getQuery().toString())) {
            this.goodsRequestModel.setQ(null);
        }
        this.goodsRequestModel.setPage_no(this.pageIndex);
        this.goodsRequestModel.setPage_size(this.pageSize);
        this.onRequestTaoGoods(this.goodsRequestModel);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra(GoodsDetailActivity.KEY_GOODS, goodsEntity);
        this.startActivity(intent);
    }

    protected void showLoading() {
        this.srl_act_goods_swipe_container.setRefreshing(true);
    }

    protected void hiddenLoading() {
        this.srl_act_goods_swipe_container.setRefreshing(false);
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
        hiddenLoading();
        Toast.makeText(this, "呃 出错了", Toast.LENGTH_SHORT).show();
    }

    private void onRequestTaoGoods(GoodsRequestModel goodsRequestModel) {
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_tao_goods_query);
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this);
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
                        LinkedList<GoodsEntity> goodsEntities = responseModel.getGoodsEntities();
                        onRequestFinishBySuccess(goodsEntities);
                    }
                }

                @Override
                public void onEmptyResult(String result) {
                    super.onEmptyResult(result);
                    Toast.makeText(GoodsListActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(GoodsListActivity.this);
                    SharedPreferencesUtils.clearAuthor(GoodsListActivity.this);
                    Toast.makeText(GoodsListActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(GoodsListActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onRequestFinishByError();
        }
    }



//
//    @Override
//    protected void onBrokerageDefault(Button view) {
//        this.resetSortDefault();
//        view.setText("佣金");
//        this.goodsRequestModel.setSort(null);
//        onRefresh();
//    }
//
//    @Override
//    protected void onBrokerageDown(Button view) {
//        this.resetSortDefault();
//        view.setText("佣金 ↓");
//        this.goodsRequestModel.setSort(GoodsRequestModel.TK_RATE_DES);
//        onRefresh();
//    }
//
//    @Override
//    protected void onBrokerageUp(Button view) {
//        this.resetSortDefault();
//        view.setText("佣金 ↑");
//        this.goodsRequestModel.setSort(GoodsRequestModel.TK_RATE_ASC);
//        onRefresh();
//    }
//
//    @Override
//    protected void onPriceDefault(Button view) {
////        this.resetSortDefault();
////        view.setText("价格");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPriceDown(Button view) {
////        this.resetSortDefault();
////        view.setText("价格 ↓");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPriceUp(Button view) {
////        this.resetSortDefault();
////        view.setText("价格 ↑");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onSaleDefault(Button view) {
//        this.resetSortDefault();
//        view.setText("销量");
//        this.goodsRequestModel.setSort(null);
//        onRefresh();
//    }
//
//    @Override
//    protected void onSaleDown(Button view) {
//        this.resetSortDefault();
//        view.setText("销量 ↓");
//        this.goodsRequestModel.setSort(GoodsRequestModel.TOTAL_SALES_DES);
//        onRefresh();
//    }
//
//    @Override
//    protected void onSaleUp(Button view) {
//        this.resetSortDefault();
//        view.setText("销量 ↑");
//        this.goodsRequestModel.setSort(GoodsRequestModel.TOTAL_SALES_ASC);
//        onRefresh();
//    }

//    @Override
//    protected void onTicketDefault(Button view) {
////        this.resetSortDefault();
////        view.setText("优惠券");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onTicketDown(Button view) {
////        this.resetSortDefault();
////        view.setText("优惠券 ↓");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onTicketUp(Button view) {
////        this.resetSortDefault();
////        view.setText("优惠券 ↑");
//        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
//    }

}
