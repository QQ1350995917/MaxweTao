package org.maxwe.tao.android.agent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-11 16:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_agent)
public class AgentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, View.OnClickListener {
    private LinkedList<AgentEntity> historyModelLinkedList = new LinkedList<>();

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
            View inflate = this.layoutInflater.inflate(R.layout.fragment_agent_item, null);
            TextView bt_frg_agent_item_id = (TextView) inflate.findViewById(R.id.bt_frg_agent_item_id);
            TextView bt_frg_agent_item_level = (TextView) inflate.findViewById(R.id.bt_frg_agent_item_level);
            Button bt_frg_agent_item_status = (Button) inflate.findViewById(R.id.bt_frg_agent_item_status);

            AgentEntity agentEntity = historyModelLinkedList.get(position);

            bt_frg_agent_item_id.setText(agentEntity.getMark());
            bt_frg_agent_item_level.setText(agentEntity.getLevelId());
            bt_frg_agent_item_status.setText(agentEntity.getMark());

            bt_frg_agent_item_status.setOnClickListener(AgentFragment.this);
            return inflate;
        }
    }


    @ViewInject(R.id.srl_frg_agent_list_container)
    private SwipeRefreshLayout srl_frg_agent_list_container;
    @ViewInject(R.id.lv_frg_agent_list)
    private ListView lv_frg_agent_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        for (int i = 0; i < 100; i++) {
            historyModelLinkedList.add(new AgentEntity("QAZWSXEDCRF", "分销"));
        }
        this.lv_frg_agent_list.setAdapter(new AgentItemAdapter(this.getContext()));
        return view;
    }

    @Override
    public void onRefresh() {
        srl_frg_agent_list_container.setRefreshing(false);
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
    public void onClick(View v) {
        Intent intent = new Intent(this.getContext(), TradeActivity.class);
        this.getActivity().startActivity(intent);
    }


    @Event(value = R.id.bt_frg_agent_leader, type = View.OnClickListener.class)
    private void onFindTrunkAgentAction(View view) {
        Intent intent = new Intent(this.getContext(), TrunkActivity.class);
        this.startActivity(intent);
    }

}
