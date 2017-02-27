package org.maxwe.tao.android.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;


/**
 * Created by Pengwei Ding on 2017-01-22 14:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录淘宝进行授权登录
 */
@ContentView(R.layout.activity_author)
public class AuthorActivity extends BaseActivity {
    public interface TaoLoginStatusCallback {
        void onNeedLoginCallback();

        void onNeedBrandCallback();

        void onNeedOkCallback();

        void onNeedErrorCallback();
    }

    public static void requestTaoLoginStatus(final Context context, final TaoLoginStatusCallback taoLoginStatusCallback) {
        RequestParams requestParams = new RequestParams(AuthorActivity.URL_LOGIN_MESSAGE);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
        requestParams.addHeader("Cookie", CookieStr);
        Callback.Cancelable cancelable = x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map rootMap = JSON.parseObject(result, Map.class);
                Map<String, String> dataMap = (Map<String, String>) rootMap.get(AuthorActivity.KEY_DATA);
                if (dataMap != null) {
                    if (dataMap.containsKey(AuthorActivity.KEY_NO_LOGIN)) {
                        taoLoginStatusCallback.onNeedLoginCallback();
                    } else {
                        SharedPreferencesUtils.saveCurrentKeeperId(context, (String.valueOf(dataMap.get("shopKeeperId"))));
                        Position currentPP = SharedPreferencesUtils.getCurrentPP(context);
                        if (currentPP == null || TextUtils.isEmpty(currentPP.getId()) || TextUtils.isEmpty(currentPP.getSiteId())) {
                            Toast.makeText(context, "请选择推广位", Toast.LENGTH_SHORT).show();
                            taoLoginStatusCallback.onNeedBrandCallback();
                        } else {
                            taoLoginStatusCallback.onNeedOkCallback();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                taoLoginStatusCallback.onNeedErrorCallback();
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static final String KEY_INTENT_OF_STATE_CODE = "KEY_INTENT_OF_STATE_CODE";
    public static final String KEY_INTENT_OF_AUTHOR = "KEY_INTENT_OF_AUTHOR";
    public static final int CODE_RESULT_OF_AUTHOR_FAIL = 1;
    public static final int CODE_RESULT_OF_AUTHOR_SUCCESS = 2;


    // 返回数据的data关键字
    public static final String KEY_DATA = "data";
    // 返回数据的info关键字
    public static final String KEY_INFO = "info";
    // 判断登录过期的关键字
    public static final String KEY_NO_LOGIN = "noLogin";
    // 判断登录状态的关键字
    public static final String KEY_TB_TOKEN_ = "_tb_token_";
    // 获取推广管理的集合
    public static final String KEY_OTHER_LIST = "otherList";
    public static final String KEY_OTHER_ADZONES = "otherAdzones";
    public static final String KEY_OK = "ok";
    public static final String KEY_GUIDE_LIST = "guideList";

    // 获取推广位的导购名称
    public static final String KEY_NAME = "name";
    public static final String KEY_SUB = "sub";
    public static final String KEY_CATEGORY_ID = "categoryId";
    public static final String KEY_ACCOUNT1 = "account1";
    public static final String KEY_TAG = "tag";
    public static final String KEY_NEW_AD_ZONE_NAME = "newadzonename";
    public static final String KEY_SITE_ID = "siteid";
    public static final String KEY_T = "t";
    public static final String KEY_GCID = "gcid";
    public static final String KEY_SELECT_ACT = "selectact";
    public static final String KEY_GUIDE_ID = "guideID";


    // 查询是否登录状态
    public static final String URL_LOGIN_MESSAGE = "http://ad.alimama.com/cps/shopkeeper/loginMessage.json";

    // 获取导购管理和导购推广位的信息
    public static final String URL_AD_ZONE = "http://pub.alimama.com/common/adzone/newSelfAdzone2.json?tag=29";

    public static final String URL_ADD_GUIDE = "http://pub.alimama.com/common/site/generalize/guideAdd.json";
    public static final String URL_ADD_AD_ZONE = "http://pub.alimama.com/common/adzone/selfAdzoneCreate.json";

    // 查询推广位的信息
    public static final String URL_GUIDE_LIST = "http://pub.alimama.com/common/site/generalize/guideList.json";

    // 登录页面,登陆成功后返回登录信息
    private static final String URL_LOGIN = "https://login.m.taobao.com/login.htm?redirectURL=http://login.taobao.com/member/taobaoke/login.htm?is_login=1&loginFrom=wap_alimama";


    private static final String main = "http://pub.alimama.com/myunion.htm";
    private static final String index2 = "https://login.m.taobao.com/login.htm?loginFrom=wap_tb";
    private static final String index3 = "http://h5.m.taobao.com/mlapp/mytaobao.html#mlapp-mytaobao";


    // 登录成功后跳转的页面 在这个页面加载完毕后执行 SCRIPT_ON_INDEX 脚本
    private static final String URL_INDEX = "http://www.alimama.com/index.htm";//
    // 执行 SCRIPT_ON_INDEX 脚本后要执行一个脚本跳转到用户后台页面 在该页面执行 SCRIPT_SHOPPING_ADD_CLICK 脚本
    private static final String URL_MY_UNION = "http://pub.alimama.com/myunion.htm";
    private static final String URL_SHOPPING = "http://pub.alimama.com/myunion.htm#!/manage/site/site?tab=4&toPage=1";//导购管理页面

    private static final String SCRIPT_ON_INDEX = "javascript:(document.getElementsByClassName('open nologin')[0].click())";//进入后台确认页面
    private static final String SCRIPT_ON_MY_UNION = "javascript:(document.getElementsByClassName('product-block product-block-6')[0].childNodes[0].click())";//执行进入后台确认动作
    private static final String SCRIPT_SHOPPING_ADD_CLICK = "javascript:(document.getElementById('J_item_list').childNodes[1].childNodes[1].childNodes[1].click())";//新增导购推广的事件
    private static final String SCRIPT_SHOPPING_ADD_NAME = "javascript:(document.getElementsByName('name')[0].value = 'test ok 3')"; //新增导购推广的名称
    private static final String SCRIPT_SHOPPING_ADD_CATEGORY = "javascript:(document.getElementsByName('categoryId')[0].value = '14')";//新增导购推广的媒体类型为微信
    private static final String SCRIPT_SHOPPING_ADD_ACCOUNT = "javascript:(document.getElementsByName('account1')[0].value = '333333')";//新增导购推广的账号
    private static final String SCRIPT_SHOPPING_ADD_SUBMIT = "javascript:(document.getElementById('vf-dialog').childNodes[0].childNodes[7].childNodes[1].click())";//新增导购推广的提交事件


    @ViewInject(R.id.wv_act_author)
    private WebView wv_act_author;

    @ViewInject(R.id.ll_act_author_mask_login)
    private RelativeLayout ll_act_author_mask_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wv_act_author.setWebContentsDebuggingEnabled(true);
        this.wv_act_author.setScrollContainer(false);
        this.wv_act_author.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        this.wv_act_author.getSettings().setDefaultTextEncodingName("UTF-8");
        this.wv_act_author.getSettings().setJavaScriptEnabled(true);
        this.wv_act_author.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.wv_act_author.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.wv_act_author.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.wv_act_author.getSettings().setLoadsImagesAutomatically(true);
        this.wv_act_author.getSettings().setAllowFileAccess(true);
        this.wv_act_author.getSettings().setAppCacheEnabled(true);
        this.wv_act_author.getSettings().setDatabaseEnabled(true);
        this.wv_act_author.getSettings().setDomStorageEnabled(true);


        this.wv_act_author.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, final String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ll_act_author_mask_login.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                if (url.equals(URL_INDEX)) {
                    //view.loadUrl("javascript:window.local_obj.showSource(document.body)");
                    onSuccessBack();
                } else {
                    onBackPressed();
                }
                ll_act_author_mask_login.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        this.wv_act_author.loadUrl(URL_LOGIN);
    }

    @Event(value = R.id.bt_act_author_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.wv_act_author.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.wv_act_author.resumeTimers();
    }

    private void onSuccessBack() {
        this.setResult(CODE_RESULT_OF_AUTHOR_SUCCESS);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(CODE_RESULT_OF_AUTHOR_FAIL);
        this.finish();
    }
}
