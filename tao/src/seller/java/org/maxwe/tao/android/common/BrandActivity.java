package org.maxwe.tao.android.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.goods.alimama.AdZoneEntity;
import org.maxwe.tao.android.goods.alimama.BrandCreateRequestModel;
import org.maxwe.tao.android.goods.alimama.BrandCreateResponseModel;
import org.maxwe.tao.android.goods.alimama.BrandListRequestModel;
import org.maxwe.tao.android.goods.alimama.BrandListResponseModel;
import org.maxwe.tao.android.goods.alimama.GuideEntity;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-08 17:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 推广位列表
 */
@ContentView(R.layout.activity_brand)
public class BrandActivity extends BaseActivity {
    public static final int CODE_RESULT_SUCCESS = 0;
    public static final int CODE_RESULT_FAIL = 1;

    private List<GuideEntity> guideEntities = new LinkedList<>();

    @ViewInject(R.id.lv_act_brand_promotion)
    private ListView lv_act_brand_promotion;
    @ViewInject(R.id.lv_act_brand_position)
    private ListView lv_act_brand_position;

    @ViewInject(R.id.rl_act_brand_create)
    private RelativeLayout rl_act_brand_create;
    @ViewInject(R.id.et_act_brand_wei_xin_account)
    private EditText et_act_brand_wei_xin_account;

    @ViewInject(R.id.pb_act_brand_progress)
    private ProgressBar pb_act_brand_progress;

    private BaseAdapter promotionAdapter = null;
    private GuideEntity currentGuideEntity = null;
    private BaseAdapter positionAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.promotionAdapter = new BaseAdapter() {
            private LayoutInflater inflater = LayoutInflater.from(BrandActivity.this);

            @Override
            public int getCount() {
                return guideEntities.size();
            }

            @Override
            public Object getItem(int position) {
                return guideEntities.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = inflater.inflate(R.layout.activity_brand_item1, null);
                TextView name = (TextView) inflate.findViewById(R.id.tv_act_brand_item_name);
                GuideEntity guideEntity = guideEntities.get(position);
                name.setText(guideEntity.getName());
                return inflate;
            }
        };

        this.positionAdapter = new BaseAdapter() {
            private LayoutInflater inflater = LayoutInflater.from(BrandActivity.this);

            @Override
            public int getCount() {
                if (currentGuideEntity != null) {
                    return currentGuideEntity.getAdZones() == null ? 0 : currentGuideEntity.getAdZones().size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                if (currentGuideEntity != null) {
                    return currentGuideEntity.getAdZones() == null ? null : currentGuideEntity.getAdZones().get(position);
                }
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = inflater.inflate(R.layout.activity_brand_item2, null);
                inflate.setBackgroundColor(Color.WHITE);
                TextView name = (TextView) inflate.findViewById(R.id.tv_act_brand_item_name);
                AdZoneEntity adZoneEntity = currentGuideEntity.getAdZones().get(position);
                RadioButton status = (RadioButton) inflate.findViewById(R.id.rb_act_brand_item_status);
                AdZoneEntity adZoneEntityCurrent = SharedPreferencesUtils.getCurrentPP2(BrandActivity.this);
                if (adZoneEntityCurrent != null && adZoneEntity.getId().equals(adZoneEntityCurrent.getId())) {
                    status.setChecked(true);
                } else {
                    status.setChecked(false);
                }
                name.setText(adZoneEntity.getName());
                return inflate;
            }
        };

        this.lv_act_brand_promotion.setAdapter(this.promotionAdapter);
        this.lv_act_brand_position.setAdapter(this.positionAdapter);

        this.lv_act_brand_promotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = parent.getChildAt(i);
                    childAt.setBackground(BrandActivity.this.getResources().getDrawable(R.color.transparent));
                }
                view.setBackground(BrandActivity.this.getResources().getDrawable(R.drawable.shape_rect_bg_shadow));
                currentGuideEntity = guideEntities.get(position);
                positionAdapter.notifyDataSetChanged();
            }
        });
        this.lv_act_brand_position.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = parent.getChildAt(i);
                    RadioButton status = (RadioButton) childAt.findViewById(R.id.rb_act_brand_item_status);
                    status.setChecked(false);
                }
                RadioButton status = (RadioButton) view.findViewById(R.id.rb_act_brand_item_status);
                status.setChecked(true);
                AdZoneEntity adZoneEntity = currentGuideEntity.getAdZones().get(position);
                SharedPreferencesUtils.saveCurrentPP(BrandActivity.this, adZoneEntity);
            }
        });

        this.pb_act_brand_progress.setVisibility(View.VISIBLE);
        getGuideList();
    }

    @Event(value = R.id.bt_act_brand_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AdZoneEntity currentAdZoneEntity = SharedPreferencesUtils.getCurrentPP2(this);
        if (currentAdZoneEntity == null) {
            this.setResult(CODE_RESULT_FAIL);
        } else {
            this.setResult(CODE_RESULT_SUCCESS);
        }
        this.finish();
    }

    private void onNoGuideList() {
        this.pb_act_brand_progress.setVisibility(View.GONE);
        this.rl_act_brand_create.setVisibility(View.VISIBLE);
    }

    private void guideListSuccess(List<GuideEntity> guideEntities) {
        this.guideEntities.clear();
        this.pb_act_brand_progress.setVisibility(View.GONE);
        this.guideEntities.addAll(guideEntities);
        this.promotionAdapter.notifyDataSetChanged();
        this.currentGuideEntity = this.guideEntities.get(0);
        this.positionAdapter.notifyDataSetChanged();
    }

    private void guideListError() {
        this.pb_act_brand_progress.setVisibility(View.GONE);
        Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_act_brand_create, type = View.OnClickListener.class)
    private void onCreateAction(View view) {
        String weiXinAccount = et_act_brand_wei_xin_account.getText().toString();
        if (TextUtils.isEmpty(weiXinAccount)) {
            Toast.makeText(this, R.string.string_tao_wei_xin, Toast.LENGTH_SHORT).show();
            return;
        }
        this.pb_act_brand_progress.setVisibility(View.VISIBLE);
        this.rl_act_brand_create.setVisibility(View.GONE);
        createGuide(this.getString(R.string.string_tao_promotion_new_name), this.getString(R.string.string_tao_position_new_name),weiXinAccount);
    }

    private void getGuideList() {
        try {
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_ali_goods_brands);
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
            TokenModel session = SharedPreferencesUtils.getSession(BrandActivity.this);
            BrandListRequestModel requestModel = new BrandListRequestModel(session, cookie);
            requestModel.setSign(session.getEncryptSing());
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    BrandListResponseModel responseModel = JSON.parseObject(result, BrandListResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        guideListSuccess(responseModel.getBrands());
                    } else if (responseModel.getCode() == ResponseModel.RC_NOT_FOUND) {
                        onNoGuideList();
                    }
                    Toast.makeText(BrandActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(BrandActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BrandActivity.this, "发生错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void createGuide(String guideName, String adZoneName, String weChat) {
        try {
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_ali_goods_create_brands);
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
            TokenModel session = SharedPreferencesUtils.getSession(BrandActivity.this);
            BrandCreateRequestModel requestModel = new BrandCreateRequestModel(session, cookie);
            requestModel.setGuideName(guideName);
            requestModel.setAdZoneName(adZoneName);
            requestModel.setWeChat(weChat);
            requestModel.setSign(session.getEncryptSing());
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    BrandCreateResponseModel responseModel = JSON.parseObject(result, BrandCreateResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        guideListSuccess(responseModel.getBrands());
                    } else {
                        onNoGuideList();
                    }
                    Toast.makeText(BrandActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(BrandActivity.this, "网络错误，请重试", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BrandActivity.this, "发生错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
