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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.agent.AgentModel;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.mate.BranchModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
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

    private LinkedList<AgentModel> agentEntities = new LinkedList<>();

    private class AgentItemAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public AgentItemAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return agentEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return agentEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = this.layoutInflater.inflate(R.layout.fragment_agent_item, null);
            TextView tv_frg_agent_item_id = (TextView) inflate.findViewById(R.id.tv_frg_agent_item_id);
            TextView tv_frg_agent_item_level = (TextView) inflate.findViewById(R.id.tv_frg_agent_item_level);
            GrantButton bt_frg_agent_item_status = (GrantButton) inflate.findViewById(R.id.bt_frg_agent_item_status);
            AgentModel agentModel = agentEntities.get(position);
            tv_frg_agent_item_id.setText("ID:" + agentModel.getAgentEntity().getMark());
            if (agentModel.getLevelEntity() != null) {
                tv_frg_agent_item_level.setText(agentModel.getLevelEntity().getName());
            }
            bt_frg_agent_item_status.setAgentEntity(agentModel);
            return inflate;
        }
    }


    @ViewInject(R.id.tv_act_agent_no_data)
    private TextView tv_act_agent_no_data;

    @ViewInject(R.id.srl_frg_agent_list_container)
    private SwipeRefreshLayout srl_frg_agent_list_container;
    @ViewInject(R.id.lv_frg_agent_list)
    private ListView lv_frg_agent_list;

    private AgentItemAdapter agentItemAdapter = null;
    private int pageIndex = 0;
    private static final int pageSize = 20;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.srl_frg_agent_list_container.setOnRefreshListener(this);
        this.srl_frg_agent_list_container.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.lv_frg_agent_list.setOnScrollListener(this);
        this.agentItemAdapter = new AgentItemAdapter(this.getContext());
        this.lv_frg_agent_list.setAdapter(agentItemAdapter);
        this.onRequestAgentMates();
        return view;
    }

    @Override
    public void onRefresh() {
        this.agentEntities.clear();
        this.pageIndex = 0;
        this.onRequestAgentMates();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                this.pageIndex++;
                this.onRequestAgentMates();
                this.srl_frg_agent_list_container.setRefreshing(true);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Event(value = R.id.bt_frg_agent_leader, type = View.OnClickListener.class)
    private void onFindTrunkAgentAction(View view) {
        Intent intent = new Intent(this.getContext(), TrunkActivity.class);
        this.startActivity(intent);
    }


    // 成功返回空数据
    private void onResponseSuccessEmpty() {
        this.srl_frg_agent_list_container.setRefreshing(false);
        if (this.agentEntities.size() == 0) {
            this.tv_act_agent_no_data.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this.getContext(), R.string.string_no_more_data, Toast.LENGTH_SHORT).show();
        }
    }

    // 成功返回
    private void onResponseSuccess(LinkedList<AgentModel> agentEntities) {
        this.srl_frg_agent_list_container.setRefreshing(false);
        this.agentEntities.addAll(agentEntities);
        this.agentItemAdapter.notifyDataSetChanged();
        this.tv_act_agent_no_data.setVisibility(View.GONE);
    }

    // 失败
    private void onResponseError() {
        this.srl_frg_agent_list_container.setRefreshing(false);
        if (this.agentEntities.size() == 0) {
            this.tv_act_agent_no_data.setVisibility(View.VISIBLE);
        }
    }

    private void onRequestAgentMates() {
        this.srl_frg_agent_list_container.setRefreshing(true);
        try {
            SessionModel session = SharedPreferencesUtils.getSession(this.getContext());
            BranchModel branchModel = new BranchModel(session, pageIndex, pageSize);
            branchModel.setSign(session.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_mate_mates);
            NetworkManager.requestByPost(url, branchModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    BranchModel responseModel = JSON.parseObject(result, BranchModel.class);
                    onResponseSuccess(responseModel.getAgentEntities());
                }

                @Override
                public void onLoginTimeout(String result) {
                    onResponseError();
                    SharedPreferencesUtils.clearSession(AgentFragment.this.getContext());
                    Toast.makeText(AgentFragment.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmptyResult(String result) {
                    onResponseSuccessEmpty();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    onResponseError();
                    Toast.makeText(AgentFragment.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            this.srl_frg_agent_list_container.setRefreshing(false);
        }
    }

}
