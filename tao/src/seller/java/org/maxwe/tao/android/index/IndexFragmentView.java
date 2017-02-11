package org.maxwe.tao.android.index;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.maxwe.tao.android.R;

/**
 * Created by Pengwei Ding on 2017-02-11 11:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IndexFragmentView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, View.OnClickListener,AdapterView.OnItemClickListener{


    private RelativeLayout rl_frg_search_container;
    private LinearLayout ll_inc_index_frg_tools_bar;
    private SwipeRefreshLayout srl_inc_index_frg_swipe_container;
    private ListView lv_inc_index_frg_container;
    private ListAdapter listAdapter;

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
    public void onRefresh() {
        /**
         * TODO 清空数据
         * 重置页码
         * 请求数据
         */
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
                this.srl_inc_index_frg_swipe_container.setRefreshing(true);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void init() {
        View inflate = LayoutInflater.from(this.getContext()).inflate(R.layout.include_index_fragment, this);
        this.rl_frg_search_container = (RelativeLayout) inflate.findViewById(R.id.rl_frg_search_container);
        this.ll_inc_index_frg_tools_bar = (LinearLayout) inflate.findViewById(R.id.ll_inc_index_frg_tools_bar);

//        this.swipeRefreshLayout = new SwipeRefreshLayout(this.getContext());
//        RelativeLayout.LayoutParams swipeViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        this.swipeRefreshLayout.setLayoutParams(swipeViewLayoutParams);
//        this.listView = new ListView(this.getContext());
//        RelativeLayout.LayoutParams listViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        listViewLayoutParams.setMargins(10, 0, 10, 0);
//        listViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        this.listView.setLayoutParams(listViewLayoutParams);
//        this.swipeRefreshLayout.addView(this.listView);
//        this.addView(this.swipeRefreshLayout);
//        if (this.listAdapter != null){
//            this.listView.setAdapter(this.listAdapter);
//        }
    }

    protected RelativeLayout getSearchBar(){
        return this.rl_frg_search_container;
    }
    protected LinearLayout getToolsBarView(){
        return this.ll_inc_index_frg_tools_bar;
    }

    protected void setListViewAdapter(ListAdapter listAdapter){
        this.listAdapter = listAdapter;
    }

}
