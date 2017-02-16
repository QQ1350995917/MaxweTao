package org.maxwe.tao.android.index;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.maxwe.tao.android.R;

/**
 * Created by Pengwei Ding on 2017-02-11 11:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public abstract class IndexFragmentView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, View.OnClickListener, AdapterView.OnItemClickListener ,TextView.OnEditorActionListener{
    private RelativeLayout rl_frg_search_container;
    private LinearLayout ll_inc_index_frg_tools_bar;
    private SwipeRefreshLayout srl_inc_index_frg_swipe_container;
    private ListView lv_inc_index_frg_container;
    private ListAdapter listAdapter;

    protected EditText et_frg_index_search;
    protected Button bt_frg_search;

    protected int brokerageStatus = 0;
    protected int priceStatus = 0;
    protected int saleStatus = 0;
    protected int ticketStatus = 0;

    public IndexFragmentView(Context context) {
        super(context);
        init();
    }

    public IndexFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndexFragmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    abstract public void onRefresh();

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == 0 || actionId == 3) && event != null) {
            this.onQueryAction(this.bt_frg_search, this.et_frg_index_search.getText().toString());
        }
        return false;
    }

    @Override
    public abstract void onScrollStateChanged(AbsListView view, int scrollState);

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.bt_frg_search == id) {
            this.onQueryAction((Button) v, this.et_frg_index_search.getText().toString());
        } else if (R.id.ib_frg_index_brokerage == id) {
            if (brokerageStatus == 0) {
                brokerageStatus = 1;
                this.onBrokerageDown((Button) v);
            } else if (brokerageStatus == 1) {
                brokerageStatus = 2;
                this.onBrokerageUp((Button) v);
            } else if (brokerageStatus == 2) {
                brokerageStatus = 0;
                this.onBrokerageDefault((Button) v);
            }
        } else if (R.id.ib_frg_index_price == id) {
            if (priceStatus == 0) {
                priceStatus = 1;
                this.onPriceDown((Button) v);
            } else if (priceStatus == 1) {
                priceStatus = 2;
                this.onPriceUp((Button) v);
            } else if (priceStatus == 2) {
                priceStatus = 0;
                this.onPriceDefault((Button) v);
            }
        } else if (R.id.ib_frg_index_sale == id) {
            if (saleStatus == 0) {
                saleStatus = 1;
                this.onSaleDown((Button) v);
            } else if (saleStatus == 1) {
                saleStatus = 2;
                this.onSaleUp((Button) v);
            } else if (saleStatus == 2) {
                saleStatus = 0;
                this.onSaleDefault((Button) v);
            }
        } else if (R.id.ib_frg_index_ticket == id) {
            if (ticketStatus == 0) {
                ticketStatus = 1;
                this.onTicketDown((Button) v);
            } else if (ticketStatus == 1) {
                ticketStatus = 2;
                this.onTicketUp((Button) v);
            } else if (ticketStatus == 2) {
                ticketStatus = 0;
                this.onTicketDefault((Button) v);
            }
        }
    }

    @Override
    public abstract void onItemClick(AdapterView<?> parent, View view, int position, long id);

    private void init() {
        View inflate = LayoutInflater.from(this.getContext()).inflate(R.layout.include_index_fragment, this);
        this.et_frg_index_search = (EditText) inflate.findViewById(R.id.et_frg_index_search);
        this.et_frg_index_search.setOnEditorActionListener(this);
        this.bt_frg_search = (Button) inflate.findViewById(R.id.bt_frg_search);
        this.bt_frg_search.setOnClickListener(this);

        Button brokerage = (Button) inflate.findViewById(R.id.ib_frg_index_brokerage);
        brokerage.setOnClickListener(this);
        Button price = (Button) inflate.findViewById(R.id.ib_frg_index_price);
        price.setOnClickListener(this);
        Button sale = (Button) inflate.findViewById(R.id.ib_frg_index_sale);
        sale.setOnClickListener(this);
        Button ticket = (Button) inflate.findViewById(R.id.ib_frg_index_ticket);
        ticket.setOnClickListener(this);

        this.rl_frg_search_container = (RelativeLayout) inflate.findViewById(R.id.rl_frg_search_container);
        this.ll_inc_index_frg_tools_bar = (LinearLayout) inflate.findViewById(R.id.ll_inc_index_frg_tools_bar);
        this.srl_inc_index_frg_swipe_container = (SwipeRefreshLayout) inflate.findViewById(R.id.srl_inc_index_frg_swipe_container);
        this.srl_inc_index_frg_swipe_container.setOnRefreshListener(this);
        this.lv_inc_index_frg_container = (ListView) inflate.findViewById(R.id.lv_inc_index_frg_container);
        this.lv_inc_index_frg_container.setOnItemClickListener(this);
        this.lv_inc_index_frg_container.setOnScrollListener(this);
        if (this.lv_inc_index_frg_container != null) {
            this.lv_inc_index_frg_container.setAdapter(this.listAdapter);
        }
    }

    protected RelativeLayout getSearchBar() {
        return this.rl_frg_search_container;
    }

    protected LinearLayout getToolsBarView() {
        return this.ll_inc_index_frg_tools_bar;
    }

    protected void showLoading() {
        this.srl_inc_index_frg_swipe_container.setRefreshing(true);
    }

    protected void hiddenLoading() {
        this.srl_inc_index_frg_swipe_container.setRefreshing(false);
    }

    protected void setListViewAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        if (this.listAdapter != null) {
            this.lv_inc_index_frg_container.setAdapter(this.listAdapter);
        }
    }

    protected abstract void onQueryAction(Button view, String text);

    protected abstract void onBrokerageDefault(Button view);

    protected abstract void onBrokerageDown(Button view);

    protected abstract void onBrokerageUp(Button view);

    protected abstract void onPriceDefault(Button view);

    protected abstract void onPriceDown(Button view);

    protected abstract void onPriceUp(Button view);

    protected abstract void onSaleDefault(Button view);

    protected abstract void onSaleDown(Button view);

    protected abstract void onSaleUp(Button view);

    protected abstract void onTicketDefault(Button view);

    protected abstract void onTicketDown(Button view);

    protected abstract void onTicketUp(Button view);


}
