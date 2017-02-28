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
import android.webkit.CookieManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
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
public class GoodsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
    public static final String INTENT_KEY_URL_TYPE = "urlType";
    private int currentUrlType = 0;

    @ViewInject(R.id.sv_act_goods_search)
    private SearchView sv_act_goods_search;
    @ViewInject(R.id.ib_act_goods_brokerage)
    private Button ib_act_goods_brokerage;
    @ViewInject(R.id.ib_act_goods_price)
    private Button ib_act_goods_price;
    @ViewInject(R.id.ib_act_goods_sale)
    private Button ib_act_goods_sale;
    @ViewInject(R.id.ib_act_goods_ticket)
    private Button ib_act_goods_ticket;
    @ViewInject(R.id.srl_act_goods_swipe_container)
    private SwipeRefreshLayout srl_act_goods_swipe_container;
    @ViewInject(R.id.lv_act_goods_container)
    private ListView lv_act_goods_container;


    private List<AliGoodsEntity> goodsEntities = new LinkedList<>();
    private BaseAdapter listAdapter = null;
    private int pageIndex = 0;
    private int pageSize = 20;

    private AliGoodsRequestModel goodsRequestModel = new AliGoodsRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentUrlType = this.getIntent().getIntExtra(INTENT_KEY_URL_TYPE, currentUrlType);
        this.goodsRequestModel.setUrlType(this.currentUrlType);
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
                AliGoodsEntity goodsEntity = goodsEntities.get(position);
                View view = inflater.inflate(R.layout.include_index_fragment_goods, null);
                SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iv_inc_index_frg_item_goods_image);
                TextView title = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_title);
                TextView hasCoupon = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_is_coupon);
                TextView price = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_price);
                TextView coupon = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_coupon);
                TextView couponPrice = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_coupon_price);
                if (goodsEntity.getCouponAmount() > 0) {
                    hasCoupon.setVisibility(View.VISIBLE);
                    coupon.setVisibility(View.VISIBLE);
                    couponPrice.setVisibility(View.VISIBLE);
                    couponPrice.setText(goodsEntity.getCouponAmount() + "元");
                }

                TextView priceReserve = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_goods_price_reserve);
                priceReserve.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                TextView brokerage = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_brokerage);
                TextView brokerageGot = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_brokerage_got);
                TextView sale = (TextView) view.findViewById(R.id.iv_inc_index_frg_item_sale);
                imageView.setImageURI(Uri.parse(goodsEntity.getPictUrl()));
                title.setText(goodsEntity.getTitle());
                price.setText("￥" + (goodsEntity.getZkPrice() - goodsEntity.getCouponAmount()));
                priceReserve.setText("￥" + goodsEntity.getZkPrice());
                brokerage.setText(goodsEntity.getHightestBrokage() + "%");
                brokerageGot.setText("赚" + new DecimalFormat("###.00").format((goodsEntity.getZkPrice() - goodsEntity.getCouponAmount()) * goodsEntity.getHightestBrokage() / 100) + "元");
                sale.setText("月销:" + goodsEntity.getBiz30day());
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
        if (imm != null) {
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
        this.goodsRequestModel.setToPage(this.pageIndex);
        this.goodsRequestModel.setPerPageSize(this.pageSize);
        this.onRequestAliGoods(this.goodsRequestModel);
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

                this.goodsRequestModel.setToPage(this.pageIndex);
                this.goodsRequestModel.setPerPageSize(this.pageSize);
                this.onRequestAliGoods(goodsRequestModel);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AliGoodsEntity goodsEntity = this.goodsEntities.get(position);
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

    private void onRequestFinishBySuccess(List<AliGoodsEntity> goodsEntities) {
        this.hiddenLoading();
        this.goodsEntities.addAll(goodsEntities);
        this.listAdapter.notifyDataSetChanged();
    }

    private void onRequestAliFinishBySuccess(List<AliGoodsEntity> goodsEntities) {
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

    private void onRequestAliGoods(AliGoodsRequestModel aliGoodsRequestModel) {
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_ali_goods_search);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
        aliGoodsRequestModel.setT(sessionModel.getT());
        aliGoodsRequestModel.setId(sessionModel.getId());
        aliGoodsRequestModel.setCellphone(sessionModel.getCellphone());
        aliGoodsRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
        aliGoodsRequestModel.setCookie(cookie);
        try {
            aliGoodsRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPost(url, aliGoodsRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    List<AliGoodsEntity> aliGoodsEntities = JSON.parseArray(result, AliGoodsEntity.class);
                    if (aliGoodsEntities != null) {
                        onRequestFinishBySuccess(aliGoodsEntities);
                    }
                }

                @Override
                public void onEmptyResult(String result) {
                    super.onEmptyResult(result);
                    Toast.makeText(GoodsListActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
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

    private void resetAction(){
        this.ib_act_goods_brokerage.setText("佣金");
        this.ib_act_goods_price.setText("价格");
        this.ib_act_goods_sale.setText("销量");
        this.ib_act_goods_ticket.setText("优惠券");
        this.ib_act_goods_brokerage.setSelected(false);
        this.ib_act_goods_price.setSelected(false);
        this.ib_act_goods_sale.setSelected(false);
        this.ib_act_goods_ticket.setSelected(false);
    }

    @Event(value = R.id.ib_act_goods_brokerage, type = View.OnClickListener.class)
    private void onBrokerageAction(Button view) {
        this.resetAction();
        this.ib_act_goods_brokerage.setSelected(true);
        this.goodsRequestModel.setSortType(1);
        onRefresh();
    }

    @Event(value = R.id.ib_act_goods_price, type = View.OnClickListener.class)
    private void onPriceAction(Button view) {
        this.resetAction();
        this.ib_act_goods_price.setSelected(true);
        if (this.goodsRequestModel.getSortType() != 3) {
            this.goodsRequestModel.setSortType(3);
            view.setText("价格 ↓");
        } else if (this.goodsRequestModel.getSortType() != 4) {
            this.goodsRequestModel.setSortType(4);
            view.setText("价格 ↑");
        }
        onRefresh();
    }

    @Event(value = R.id.ib_act_goods_sale, type = View.OnClickListener.class)
    private void onSaleAction(Button view) {
        this.resetAction();
        this.ib_act_goods_sale.setSelected(true);
        this.goodsRequestModel.setSortType(9);
        onRefresh();
    }

    @Event(value = R.id.ib_act_goods_ticket, type = View.OnClickListener.class)
    private void onTicketAction(Button view) {
        this.resetAction();
        this.ib_act_goods_ticket.setSelected(true);
        this.goodsRequestModel.setSortType(9);
        onRefresh();
    }
}
