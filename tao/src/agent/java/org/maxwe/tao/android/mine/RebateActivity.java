package org.maxwe.tao.android.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.AgentApplication;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.history.RebateModel;
import org.maxwe.tao.android.history.RebateRequestModel;
import org.maxwe.tao.android.history.RebateResponseModel;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.DateTimeUtils;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-05 19:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 饭店控制器
 */
@ContentView(R.layout.activity_rebate)
public class RebateActivity extends BaseActivity {

    @ViewInject(R.id.tv_act_rebate_no_data)
    TextView tv_act_rebate_no_data;
    @ViewInject(R.id.ll_act_rebate_container)
    private LinearLayout ll_act_rebate_container;
    @ViewInject(R.id.lv_act_rebate_list)
    private ListView lv_act_rebate_list;
    private LayoutInflater inflater;
    private BaseAdapter baseAdapter = null;
    private List<RebateModel> rebates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AgentApplication.currentAgentModel != null) {
            onRequestRebateAction();
        }
        this.inflater = LayoutInflater.from(this);
        this.baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return rebates == null ? 0 : rebates.size();
            }

            @Override
            public Object getItem(int position) {
                return rebates == null ? null : rebates.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = inflater.inflate(R.layout.activity_rebate_item, null);
                TextView yearView = (TextView) inflate.findViewById(R.id.tv_act_rebate_item_year);
                TextView monthView = (TextView) inflate.findViewById(R.id.tv_act_rebate_item_month);
                TextView rebateView = (TextView) inflate.findViewById(R.id.tv_act_rebate_item_rebate);
                RebateModel rebateModel = rebates.get(position);
                yearView.setText(rebateModel.getYear() + "");
                monthView.setText(rebateModel.getMonth() + "");
                rebateView.setText(rebateModel.getRebate() + "");
                return inflate;
            }
        };
        this.lv_act_rebate_list.setAdapter(this.baseAdapter);
    }

    @Event(value = R.id.bt_act_rebate_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

    private void onRequestRebateAction() {
        try {
            TokenModel tokenModel = SharedPreferencesUtils.getSession(this);
            int[] currentYearMonthDate = DateTimeUtils.getCurrentYearMonthDate();
            RebateRequestModel rebateRequestModel = new RebateRequestModel(tokenModel, currentYearMonthDate[0], currentYearMonthDate[1], 3);
            rebateRequestModel.setSign(tokenModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_history_rebate);
            NetworkManager.requestByPostNew(url, rebateRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    RebateResponseModel responseModel = JSON.parseObject(result, RebateResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS){
                        onResponseSuccess(responseModel);
                    }
                    Toast.makeText(RebateActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(RebateActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void onResponseSuccess(RebateResponseModel responseModel) {
        if (responseModel != null && responseModel.getRebates() != null && responseModel.getRebates().size() > 0) {
            List<RebateModel> rebates = responseModel.getRebates();
            this.tv_act_rebate_no_data.setVisibility(View.GONE);
            this.ll_act_rebate_container.setVisibility(View.VISIBLE);
            this.rebates = rebates;
            this.baseAdapter.notifyDataSetChanged();
        }
    }
}
