package org.maxwe.tao.android.code;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.history.HistoryEntity;
import org.maxwe.tao.android.history.HistoryModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
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
public class HistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
    private LinkedList<HistoryEntity> historyEntityLinkedList = new LinkedList<>();

    private class HistoryItemAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private Context context;

        public HistoryItemAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return historyEntityLinkedList.size();
        }

        @Override
        public Object getItem(int position) {
            return historyEntityLinkedList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = this.layoutInflater.inflate(R.layout.activity_history_item, null);
            TextView tv_act_history_item_time = (TextView) inflate.findViewById(R.id.tv_act_history_item_time);
            final TextView tv_act_history_item_number = (TextView) inflate.findViewById(R.id.tv_act_history_item_number);
            TextView tv_act_history_item_to_id = (TextView) inflate.findViewById(R.id.tv_act_history_item_to_id);
            HistoryEntity historyEntity = historyEntityLinkedList.get(position);
            tv_act_history_item_time.setText(historyEntity.getSwapTimeString());
            if (historyEntity.getType() == 1) {
                tv_act_history_item_number.setText(historyEntity.getActCode());
                if (historyEntity.getToMark() == null) {
                    tv_act_history_item_number.setClickable(true);
                    tv_act_history_item_number.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboardManager = (ClipboardManager) HistoryItemAdapter.this.context.getSystemService(Context.CLIPBOARD_SERVICE);
                            String code = tv_act_history_item_number.getText().toString();
                            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, code));
                            Toast.makeText(HistoryItemAdapter.this.context, R.string.string_copy_success, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                tv_act_history_item_number.setText(historyEntity.getCodeNum() + "");
            }
            tv_act_history_item_to_id.setText(historyEntity.getToMark());
            return inflate;
        }
    }

    @ViewInject(R.id.tv_act_history_no_data)
    private TextView tv_act_history_no_data;

    @ViewInject(R.id.ll_act_history_header)
    private LinearLayout ll_act_history_header;
    @ViewInject(R.id.srl_act_history_list_container)
    private SwipeRefreshLayout srl_act_history_list_container;
    @ViewInject(R.id.lv_act_history_list)
    private ListView lv_act_history_list;

    private HistoryItemAdapter historyItemAdapter = null;
    private int pageIndex = 0;
    private static final int pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.srl_act_history_list_container.setOnRefreshListener(this);
        this.srl_act_history_list_container.setColorSchemeResources(
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.lv_act_history_list.setOnScrollListener(this);
        this.historyItemAdapter = new HistoryItemAdapter(this);
        this.lv_act_history_list.setAdapter(historyItemAdapter);
        this.onRequestHistory();
    }

    @Override
    public void onRefresh() {
        this.historyEntityLinkedList.clear();
        this.pageIndex = 0;
        this.onRequestHistory();
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
                this.onRequestHistory();
                this.srl_act_history_list_container.setRefreshing(true);
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

    // 成功返回空数据
    private void onResponseSuccessEmpty() {
        this.srl_act_history_list_container.setRefreshing(false);
        if (this.historyEntityLinkedList.size() == 0) {
            this.tv_act_history_no_data.setVisibility(View.VISIBLE);
            this.ll_act_history_header.setVisibility(View.GONE);
            this.srl_act_history_list_container.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, R.string.string_no_more_data, Toast.LENGTH_SHORT).show();
        }
    }

    // 成功返回
    private void onResponseSuccess(LinkedList<HistoryEntity> historyEntities) {
        this.srl_act_history_list_container.setRefreshing(false);
        this.historyEntityLinkedList.addAll(historyEntities);
        this.historyItemAdapter.notifyDataSetChanged();
        this.tv_act_history_no_data.setVisibility(View.GONE);
        this.ll_act_history_header.setVisibility(View.VISIBLE);
        this.srl_act_history_list_container.setVisibility(View.VISIBLE);
    }

    // 失败
    private void onResponseError() {
        this.srl_act_history_list_container.setRefreshing(false);
        if (this.historyEntityLinkedList.size() == 0) {
            this.tv_act_history_no_data.setVisibility(View.VISIBLE);
            this.ll_act_history_header.setVisibility(View.GONE);
            this.srl_act_history_list_container.setVisibility(View.GONE);
        }
    }

    private void onRequestHistory() {
        this.srl_act_history_list_container.setRefreshing(true);
        try {
            SessionModel session = SharedPreferencesUtils.getSession(this);
            HistoryModel historyModel = new HistoryModel(session, pageIndex, pageSize);
            historyModel.setSign(session.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_history_history);
            NetworkManager.requestByPost(url, historyModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    HistoryModel responseModel = JSON.parseObject(result, HistoryModel.class);
                    onResponseSuccess(responseModel.getHistoryEntities());
                }

                @Override
                public void onLoginTimeout(String result) {
                    onResponseError();
                    SharedPreferencesUtils.clearSession(HistoryActivity.this);
                    Toast.makeText(HistoryActivity.this, R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEmptyResult(String result) {
                    onResponseSuccessEmpty();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    onResponseError();
                    Toast.makeText(HistoryActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
            this.srl_act_history_list_container.setRefreshing(false);
        }
    }
}
