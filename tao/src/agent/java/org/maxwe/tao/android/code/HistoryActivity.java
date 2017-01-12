package org.maxwe.tao.android.code;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.history.HistoryModel;
import org.maxwe.tao.android.utils.DateTimeUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-11 17:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_history)
public class HistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener{
    private LinkedList<HistoryModel> historyModelLinkedList = new LinkedList<>();

    private class AgentItemAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public AgentItemAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return historyModelLinkedList.size();
        }

        @Override
        public Object getItem(int position) {
            return historyModelLinkedList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = this.layoutInflater.inflate(R.layout.activity_history_item, null);
            TextView tv_act_history_item_time = (TextView) inflate.findViewById(R.id.tv_act_history_item_time);
            TextView tv_act_history_item_number = (TextView) inflate.findViewById(R.id.tv_act_history_item_number);
            TextView tv_act_history_item_to_id = (TextView) inflate.findViewById(R.id.tv_act_history_item_to_id);
            TextView tv_act_history_item_status = (TextView) inflate.findViewById(R.id.tv_act_history_item_status);
            HistoryModel agentEntity = historyModelLinkedList.get(position);
            tv_act_history_item_time.setText(DateTimeUtils.parseLongToFullTime(agentEntity.getCreateTime()));
            tv_act_history_item_number.setText(agentEntity.getCodeNum() + "");
            tv_act_history_item_to_id.setText(agentEntity.getMark());
            tv_act_history_item_status.setText(agentEntity.getToId());
            return inflate;
        }
    }

    @ViewInject(R.id.srl_act_history_list_container)
    private SwipeRefreshLayout srl_act_history_list_container;
    @ViewInject(R.id.lv_act_history_list)
    private ListView lv_act_history_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 100; i++) {
            historyModelLinkedList.add(new HistoryModel(i,"QAZWSXEDCRF","分销",System.currentTimeMillis()));
        }
        this.lv_act_history_list.setAdapter(new AgentItemAdapter(this));
    }

    @Override
    public void onRefresh() {
        srl_act_history_list_container.setRefreshing(false);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Event(value = R.id.bt_act_history_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }


}
