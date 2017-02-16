package org.maxwe.tao.android.activity;

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

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.api.Promotion;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-08 17:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 推广位列表
 */
@ContentView(R.layout.activity_brand)
public class BrandActivity extends BaseActivity {

    private Map<Promotion, LinkedList<Position>> promotionListMap = new LinkedHashMap<>();
    private LinkedList<Promotion> promotionList = new LinkedList<>();

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
    private Promotion currentPromotion = null;
    private BaseAdapter positionAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.promotionAdapter = new BaseAdapter() {
            private LayoutInflater inflater = LayoutInflater.from(BrandActivity.this);

            @Override
            public int getCount() {
                return promotionList.size();
            }

            @Override
            public Object getItem(int position) {
                return promotionList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View inflate = inflater.inflate(R.layout.activity_brand_item1, null);
                TextView name = (TextView) inflate.findViewById(R.id.tv_act_brand_item_name);
                Promotion promotion = promotionList.get(position);
                name.setText(promotion.getName());
                return inflate;
            }
        };

        this.positionAdapter = new BaseAdapter() {
            private LayoutInflater inflater = LayoutInflater.from(BrandActivity.this);

            @Override
            public int getCount() {
                if (currentPromotion != null) {
                    return promotionListMap.get(currentPromotion).size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                if (currentPromotion != null) {
                    return promotionListMap.get(currentPromotion).get(position);
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
                Position position1 = promotionListMap.get(currentPromotion).get(position);
                RadioButton status = (RadioButton) inflate.findViewById(R.id.rb_act_brand_item_status);
                Position currentPP = SharedPreferencesUtils.getCurrentPP(BrandActivity.this);
                if (currentPP != null && position1.getId().equals(currentPP.getId())) {
                    status.setChecked(true);
                } else {
                    status.setChecked(false);
                }
                name.setText(position1.getName());
                return inflate;
            }
        };

        this.lv_act_brand_promotion.setAdapter(this.promotionAdapter);
        this.lv_act_brand_position.setAdapter(this.positionAdapter);

        this.lv_act_brand_promotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPromotion = promotionList.get(position);
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
                Position position1 = promotionListMap.get(currentPromotion).get(position);
                position1.setPromotion(currentPromotion);
                SharedPreferencesUtils.saveCurrentPP(BrandActivity.this, position1);
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
        super.onBackPressed();
    }

    private void onNoGuideList() {
        this.pb_act_brand_progress.setVisibility(View.GONE);
        this.rl_act_brand_create.setVisibility(View.VISIBLE);
    }

    private void guideListSuccess(List<Map<String, Object>> adZoneList) {
        this.promotionListMap.clear();
        this.pb_act_brand_progress.setVisibility(View.GONE);
        for (Map<String, Object> map : adZoneList) {
            String id = map.get("id").toString();
            String name = map.get("name").toString();
            List<Map<String, Object>> sub = (List<Map<String, Object>>) map.get("sub");
            Promotion promotion = new Promotion(id, name);
            LinkedList<Position> positions = new LinkedList<>();
            if (sub != null) {
                for (Map<String, Object> subMap : sub) {
                    String positionId = subMap.get("id").toString();
                    String positionName = subMap.get("name").toString();
                    Position position = new Position(id, positionId, positionName);
                    positions.add(position);
                }
            }
            this.promotionListMap.put(promotion, positions);
            this.promotionList.add(promotion);
        }

        this.promotionAdapter.notifyDataSetChanged();
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
        createGuide(this.getString(R.string.string_tao_promotion_new_name), weiXinAccount, this.getString(R.string.string_tao_position_new_name));
    }

    private void getGuideList() {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_AD_ZONE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> dataMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_DATA);
                List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get(AuthorWebView.KEY_OTHER_ADZONES);
                if (adZoneList != null && adZoneList.size() > 0) {
//                    Map<String, Object> stringObjectMap = adZoneList.get(0);
//                    String name = stringObjectMap.get(AuthorWebView.KEY_NAME).toString();
//                    List<Map<String, Object>> maps = (List<Map<String, Object>>) stringObjectMap.get(AuthorWebView.KEY_SUB);
//                    if (maps != null && maps.size() > 0) {
//                        String s = maps.get(0).get(AuthorWebView.KEY_NAME).toString();
//                    }
                    guideListSuccess(adZoneList);
                } else {
                    onNoGuideList();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "获取导购推广出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createGuide(final String name, String account, final String positionName) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_ADD_GUIDE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        requestParams.addParameter(AuthorWebView.KEY_NAME, name);
        requestParams.addParameter(AuthorWebView.KEY_CATEGORY_ID, 14);
        requestParams.addParameter(AuthorWebView.KEY_ACCOUNT1, account);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> infoMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_INFO);
                if (Boolean.parseBoolean(infoMap.get(AuthorWebView.KEY_OK).toString())) {
                    getNewGuideInfo(name, positionName);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "创建导购推广出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getNewGuideInfo(final String newGuideName, final String positionName) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_GUIDE_LIST);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> dataMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_DATA);
                List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get(AuthorWebView.KEY_GUIDE_LIST);
                if (adZoneList != null && adZoneList.size() > 0) {
                    Map<String, Object> stringObjectMap = adZoneList.get(0);
                    String name = stringObjectMap.get(AuthorWebView.KEY_NAME).toString();
                    String guideId = stringObjectMap.get(AuthorWebView.KEY_GUIDE_ID).toString();
                    if (newGuideName.equals(name)) {
                        createADZone(guideId, positionName);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "获取新建导购推广出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createADZone(String siteId, String positionName) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_ADD_AD_ZONE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        requestParams.addParameter(AuthorWebView.KEY_TAG, 29);
        requestParams.addParameter(AuthorWebView.KEY_SITE_ID, siteId);
        requestParams.addParameter(AuthorWebView.KEY_T, System.currentTimeMillis());
        requestParams.addParameter(AuthorWebView.KEY_NEW_AD_ZONE_NAME, positionName);
        requestParams.addParameter(AuthorWebView.KEY_GCID, 8);
        requestParams.addParameter(AuthorWebView.KEY_TB_TOKEN_, getTaoBaoTokenFormCookie(CookieStr));
        requestParams.addParameter(AuthorWebView.KEY_SELECT_ACT, "add");
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> infoMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_INFO);
                if (Boolean.parseBoolean(infoMap.get(AuthorWebView.KEY_OK).toString())) {
                    getGuideList();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "创建导购推广位出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private static String getTaoBaoTokenFormCookie(String cookie) {
        String taoBaoToken = null;
        if (cookie != null) {
            String[] split = cookie.split(";");
            if (split != null) {
                for (String item : split) {
                    if (item.contains(AuthorWebView.KEY_TB_TOKEN_)) {
                        String[] split1 = item.split("=");
                        if (split1.length > 1) {
                            taoBaoToken = split1[1];
                        }
                        break;
                    }
                }
            }
        }
        return taoBaoToken;
    }
}
