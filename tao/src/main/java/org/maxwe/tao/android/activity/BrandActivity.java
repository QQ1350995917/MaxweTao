package org.maxwe.tao.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.R;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-08 17:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 推广位列表
 */
@ContentView(R.layout.activity_brand)
public class BrandActivity extends BaseActivity {
    @ViewInject(R.id.tv_act_brand_unit)
    private TextView tv_act_brand_unit;
    @ViewInject(R.id.tv_act_brand_position)
    private TextView tv_act_brand_position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGuideList();
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
                List<Map<String, Object>> otherList = (List<Map<String, Object>>) dataMap.get(AuthorWebView.KEY_OTHER_LIST);
                List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get(AuthorWebView.KEY_OTHER_ADZONES);
//                if (otherList != null && otherList.size() > 0) {
//                    Map<String, Object> stringObjectMap = otherList.get(0);
//                    String name = stringObjectMap.get(AuthorWebView.KEY_NAME).toString();
//                    tv_act_brand_unit.setText(name);
//                    Toast.makeText(BrandActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }

                if (adZoneList != null && adZoneList.size() > 0){
                    Map<String, Object> stringObjectMap = adZoneList.get(0);
                    String name = stringObjectMap.get(AuthorWebView.KEY_NAME).toString();
                    tv_act_brand_unit.setText(name);
                    List<Map<String, Object>> maps = (List<Map<String, Object>>) stringObjectMap.get(AuthorWebView.KEY_SUB);
                    String s = maps.get(0).get(AuthorWebView.KEY_NAME).toString();
                    tv_act_brand_position.setText(s);

                    Toast.makeText(BrandActivity.this, "ok", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(BrandActivity.this, "ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
