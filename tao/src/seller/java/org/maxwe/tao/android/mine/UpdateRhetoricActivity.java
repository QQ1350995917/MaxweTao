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
import org.maxwe.tao.android.account.user.UpdateRhetoricRequestModel;
import org.maxwe.tao.android.account.user.UpdateRhetoricResponseModel;
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
 * Description: 通用推广语设置
 */
@ContentView(R.layout.activity_rhetoric)
public class UpdateRhetoricActivity extends BaseActivity {
    @ViewInject(R.id.et_act_rhetoric_content)
    private EditText et_act_rhetoric_content;

    @ViewInject(R.id.bt_act_rhetoric_save)
    private Button bt_act_rhetoric_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SellerApplication.currentUserEntity == null) {
            this.et_act_rhetoric_content.setGravity(Gravity.CENTER_VERTICAL);
            this.et_act_rhetoric_content.setTextSize(30);
            this.et_act_rhetoric_content.setText("没有数据，请重试");
            this.et_act_rhetoric_content.setFocusable(false);
            this.bt_act_rhetoric_save.setVisibility(View.INVISIBLE);
        } else if (SellerApplication.currentUserEntity.getRhetoric() == null) {
            this.et_act_rhetoric_content.setHint("置空表示不使用推广用语");
        } else {
            this.et_act_rhetoric_content.setText(SellerApplication.currentUserEntity.getRhetoric());
        }
    }

    @Event(value = R.id.bt_act_rhetoric_save, type = View.OnClickListener.class)
    private void onSaveRhetoricAction(View view) {
        if (this.et_act_rhetoric_content.getText().toString().length() > 60) {
            Toast.makeText(this, "请输入60字以下内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.equals(SellerApplication.currentUserEntity.getRhetoric(),
                this.et_act_rhetoric_content.getText().toString().trim())){
            Toast.makeText(this, "内容未改变，无需保存", Toast.LENGTH_SHORT).show();
            return;
        }

        this.bt_act_rhetoric_save.setClickable(false);
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_updateRhetoric);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this);
        UpdateRhetoricRequestModel modifyModel = new UpdateRhetoricRequestModel(sessionModel, this.et_act_rhetoric_content.getText().toString().trim());
        try {
            modifyModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, modifyModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    UpdateRhetoricResponseModel responseModel = JSON.parseObject(result, UpdateRhetoricResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        SellerApplication.currentUserEntity.setRhetoric(responseModel.getRhetoric());
                    }
                    Toast.makeText(UpdateRhetoricActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    bt_act_rhetoric_save.setClickable(true);
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(UpdateRhetoricActivity.this, R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                    bt_act_rhetoric_save.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.bt_act_rhetoric_save.setClickable(true);
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.bt_act_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

}
