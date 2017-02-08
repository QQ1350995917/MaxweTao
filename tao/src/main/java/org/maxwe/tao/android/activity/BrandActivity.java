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
                List<Map<String, Object>> adZoneList = (List<Map<String, Object>>) dataMap.get(AuthorWebView.KEY_OTHER_ADZONES);
                if (adZoneList != null && adZoneList.size() > 0) {
                    Map<String, Object> stringObjectMap = adZoneList.get(0);
                    String name = stringObjectMap.get(AuthorWebView.KEY_NAME).toString();
                    tv_act_brand_unit.setText(name);
                    List<Map<String, Object>> maps = (List<Map<String, Object>>) stringObjectMap.get(AuthorWebView.KEY_SUB);
                    if (maps != null && maps.size() > 0){
                        String s = maps.get(0).get(AuthorWebView.KEY_NAME).toString();
                        tv_act_brand_position.setText(s);
                    }
                    Toast.makeText(BrandActivity.this, "ok", Toast.LENGTH_SHORT).show();
                } else {
                    createGuide("测试名称","w123456");
                    Toast.makeText(BrandActivity.this, "创建推广单元", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "获取推广列表出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createGuide(final String name,String account) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_ADD_GUIDE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        requestParams.addParameter(AuthorWebView.KEY_NAME,name);
        requestParams.addParameter(AuthorWebView.KEY_CATEGORY_ID,14);
        requestParams.addParameter(AuthorWebView.KEY_ACCOUNT1,account);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> infoMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_INFO);
                if (Boolean.parseBoolean(infoMap.get(AuthorWebView.KEY_OK).toString())) {
                    Toast.makeText(BrandActivity.this, "推广单元创建成功", Toast.LENGTH_SHORT).show();
                    getNewGuideInfo(name);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "推广单元创建出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getNewGuideInfo(final String newGuidName){
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
                    if (newGuidName.equals(name)){
                        createADZone(guideId);
                        Toast.makeText(BrandActivity.this, "获取新建单元成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "获取新建单元出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void createADZone(String siteId) {
        RequestParams requestParams = new RequestParams(AuthorWebView.URL_ADD_AD_ZONE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorWebView.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        requestParams.addParameter(AuthorWebView.KEY_TAG,29);
        requestParams.addParameter(AuthorWebView.KEY_SITE_ID,siteId);
        requestParams.addParameter(AuthorWebView.KEY_T,System.currentTimeMillis());
        requestParams.addParameter(AuthorWebView.KEY_NEW_AD_ZONE_NAME,"微信推广位000");
        requestParams.addParameter(AuthorWebView.KEY_GCID,8);
        requestParams.addParameter("_tb_token_","HP1B1kCmnFLq");
        requestParams.addParameter(AuthorWebView.KEY_SELECT_ACT,"add");
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, Object> infoMap = (Map<String, Object>) rootMap.get(AuthorWebView.KEY_INFO);
                if (Boolean.parseBoolean(infoMap.get(AuthorWebView.KEY_OK).toString())) {
                    Toast.makeText(BrandActivity.this, "推广单元创建成功", Toast.LENGTH_SHORT).show();
                    getGuideList();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(BrandActivity.this, "推广单元出错了", Toast.LENGTH_SHORT).show();
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
