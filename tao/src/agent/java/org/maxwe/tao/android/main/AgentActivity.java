package org.maxwe.tao.android.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.agent.AgentEntity;
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
@ContentView(R.layout.activity_agent)
public class AgentActivity extends BaseActivity {

    @ViewInject(R.id.lv_act_agent_agents)
    private ListView lv_act_agent_agents;

    private List<AgentEntity> list = new LinkedList<>();

    private class AgentItemAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private List<AgentEntity> list = null;

        public AgentItemAdapter(Context context, List<AgentEntity> list) {
            this.layoutInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            return this.list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = this.layoutInflater.inflate(R.layout.activity_agent_item, null);
            TextView tv_act_agent_item_agents = (TextView) inflate.findViewById(R.id.tv_act_agent_item_agents);
            TextView tv_act_agent_item_cellphone = (TextView) inflate.findViewById(R.id.tv_act_agent_item_cellphone);
            TextView tv_act_agent_item_access_code = (TextView) inflate.findViewById(R.id.tv_act_agent_item_access_code);

            AgentEntity agentEntity = this.list.get(position);
            tv_act_agent_item_agents.setText("累计授权：" + agentEntity.getType());
            tv_act_agent_item_cellphone.setText("电话号码：" + agentEntity.getCellphone());
            tv_act_agent_item_access_code.setText("授权号码：" + agentEntity.getCellphone());

            return inflate;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.list.clear();
        for (int i = 0; i < 100; i++) {
            AgentEntity agentEntity = new AgentEntity("cellphone = " + i, null, i);
            list.add(agentEntity);
        }
        this.lv_act_agent_agents.setAdapter(new AgentItemAdapter(this, this.list));
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
