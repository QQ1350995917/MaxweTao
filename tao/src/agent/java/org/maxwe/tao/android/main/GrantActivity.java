package org.maxwe.tao.android.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.response.IResponse;
import org.maxwe.tao.android.response.Response;
import org.maxwe.tao.android.utils.CellPhoneUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-12-30 17:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_grant)
public class GrantActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener {

    private class AgentItemAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public AgentItemAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = this.layoutInflater.inflate(R.layout.activity_grant_item, null);
            TextView tv_act_agent_item_cellphone = (TextView) inflate.findViewById(R.id.tv_act_agent_item_cellphone);
            TextView tv_act_agent_item_agents = (TextView) inflate.findViewById(R.id.tv_act_agent_item_codes_status);
//            AgentEntity agentEntity = list.get(position);
            tv_act_agent_item_cellphone.setText("电话:123456789000");
            tv_act_agent_item_agents.setText("码量(转让/剩余/共有):1234567890");
            return inflate;
        }
    }

    @ViewInject(R.id.et_act_agent_search_content)
    private EditText et_act_agent_search_content;
    @ViewInject(R.id.bt_act_agent_search_action)
    private Button bt_act_agent_search_action;

    @ViewInject(R.id.srl_act_agent_list_container)
    private SwipeRefreshLayout srl_act_agent_list_container;
    @ViewInject(R.id.lv_act_agent_agents)
    private ListView lv_act_agent_agents;

    private List<String> list = new LinkedList<>();
    private AgentItemAdapter agentItemAdapter;

    private int currentPageIndex = 0;
    private int counter = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.srl_act_agent_list_container.setOnRefreshListener(this);
        this.srl_act_agent_list_container.setColorSchemeResources(
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.list.clear();
        onLoadMySubAgents(currentPageIndex,counter);
        lv_act_agent_agents.setOnScrollListener(this);
        this.agentItemAdapter = new AgentItemAdapter(this);
        this.lv_act_agent_agents.setAdapter(agentItemAdapter);
    }


    @Event(value = R.id.bt_act_agent_search_action, type = View.OnClickListener.class)
    private void onGrantAction(View view) {
        String cellphone = this.et_act_agent_search_content.getText().toString();
        if (!CellPhoneUtils.isCellphone(cellphone)) {
//            Toast.makeText(this, R.string.string_grant_cellphone, Toast.LENGTH_SHORT).show();
            return;
        }

//        if (TextUtils.equals(cellphone, lastAccount)) {
//            Toast.makeText(this, R.string.string_grant_self, Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent intent = new Intent(this, GrantDialogActivity.class);
        intent.putExtra(GrantDialogActivity.KEY_CELLPHONE,cellphone);
        this.startActivity(intent);
    }


    @Override
    public void onRefresh() {
        this.list.clear();
        this.currentPageIndex = 0;
        this.onLoadMySubAgents(this.currentPageIndex,counter);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            // 判断是否滚动到底部
            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                //加载更多功能的代码
                srl_act_agent_list_container.setRefreshing(true);
                currentPageIndex ++;
                onLoadMySubAgents(currentPageIndex, counter);
            }
        }
    }

    private void onLoadMySubAgents(int pageIndex, int counter) {
//        AgentEntity agentEntity = new AgentEntity(cellphone, null, this.getResources().getInteger(R.integer.type_id));
//        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
//        agentEntityInter.setT(key);
//        SubAgentModel subAgentModel = new SubAgentModel(agentEntityInter);
//        subAgentModel.setPageIndex(pageIndex);
//        subAgentModel.setCounter(counter);
//        NetworkManager.requestAgents(subAgentModel, new NetworkManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS.getCode()){
//                    SubAgentModel responseSubAgentModel = JSON.parseObject(response.getData(), SubAgentModel.class);
//                    LinkedList<AgentEntity> subAgents = responseSubAgentModel.getSubAgents();
//                    list.addAll(subAgents);
//                    agentItemAdapter.notifyDataSetChanged();
//                    srl_act_agent_list_container.setRefreshing(false);
//                    return;
//                }
//                if (response.getCode() == IResponse.ResultCode.RC_SUCCESS_EMPTY.getCode()){
//                    Toast.makeText(GrantActivity.this,R.string.string_agents_no_data,Toast.LENGTH_SHORT).show();
//                    srl_act_agent_list_container.setRefreshing(false);
//                    return;
//                }
//
//                if (response.getCode() == IResponse.ResultCode.RC_ACCESS_TIMEOUT.getCode()){
//                    Toast.makeText(GrantActivity.this,R.string.string_toast_timeout,Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable exception, Object agentEntity) {
//                exception.printStackTrace();
//            }
//        });
    }

    @Event(value = R.id.bt_act_agent_back, type = View.OnClickListener.class)
    private void onBackToMainAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
