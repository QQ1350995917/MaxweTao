package org.maxwe.tao.android.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.account.user.UpdateReasonRequestModel;
import org.maxwe.tao.android.account.user.UpdateReasonResponseModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.maxwe.tao.android.utils.StringUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-03-23 23:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 申请佣金的理由页面
 */
@ContentView(R.layout.activity_reason)
public class UpdateReasonActivity extends BaseActivity {
    @ViewInject(R.id.et_act_promotion_setting_reason)
    private EditText et_act_promotion_setting_reason;

    @ViewInject(R.id.bt_act_promotion_setting_save)
    private Button bt_act_promotion_setting_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SellerApplication.currentUserEntity == null ||
                SellerApplication.currentUserEntity.getReason() == null) {
            this.et_act_promotion_setting_reason.setGravity(Gravity.CENTER_VERTICAL);
            this.et_act_promotion_setting_reason.setTextSize(30);
            this.et_act_promotion_setting_reason.setText("没有数据，请重试");
            this.et_act_promotion_setting_reason.setFocusable(false);
            this.bt_act_promotion_setting_save.setVisibility(View.INVISIBLE);
        } else {
            this.et_act_promotion_setting_reason.setText(SellerApplication.currentUserEntity.getReason());
        }
    }

    @Event(value = R.id.bt_act_promotion_setting_save, type = View.OnClickListener.class)
    private void onSaveReasonAction(View view) {
        if (this.et_act_promotion_setting_reason.getText().toString().length() < 4 ||
                this.et_act_promotion_setting_reason.getText().toString().length() > 200) {
            Toast.makeText(this, "请输入4个字以上200字以下内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.equals(SellerApplication.currentUserEntity.getReason(),
                this.et_act_promotion_setting_reason.getText().toString().trim())){
            Toast.makeText(this, "内容未改变，无需保存", Toast.LENGTH_SHORT).show();
            return;
        }

        this.bt_act_promotion_setting_save.setClickable(false);
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_updateReason);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
        UpdateReasonRequestModel modifyModel = new UpdateReasonRequestModel(sessionModel, this.et_act_promotion_setting_reason.getText().toString().trim());
        try {
            modifyModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, modifyModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    UpdateReasonResponseModel responseModel = JSON.parseObject(result, UpdateReasonResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        SellerApplication.currentUserEntity.setReason(responseModel.getReason());
                    }
                    Toast.makeText(UpdateReasonActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    bt_act_promotion_setting_save.setClickable(true);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(UpdateReasonActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    bt_act_promotion_setting_save.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.bt_act_promotion_setting_save.setClickable(true);
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.finish();
    }

}
