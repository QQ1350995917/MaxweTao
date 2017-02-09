package org.maxwe.tao.android.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Pengwei Ding on 2017-02-05 14:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 * <p/>
 * {"data":{"loginUrlPrefix":"http://www.alimama.com/member/login.htm?forward=","noLogin":true},"info":{"message":null,"ok":true}}
 * <p/>
 * {"data":{"loginUrlPrefix":"http://www.alimama.com/member/login.htm?forward=","noLogin":true},"info":{"message":null,"ok":true}}
 * <p/>
 * {"data":{"loginUrlPrefix":"http://www.alimama.com/member/login.htm?forward=","noLogin":true},"info":{"message":null,"ok":true}}
 */

/**
 * {"data":{"loginUrlPrefix":"http://www.alimama.com/member/login.htm?forward=","noLogin":true},"info":{"message":null,"ok":true}}
 */

/**
 * {
 * "data": {
 * "isTmallHKSeller": false,
 * "isShowMaterial": false,
 * "nickname": "www_ding",
 * "isB2C": false,
 * "isInCPADebtWhiteList": false,
 * "isJoinedCps": false,
 * "isEarnedSeller": false,
 * "env": "product",
 * "frozen": 0,
 * "logname": "www.dingpengwei@foxmail.com",
 * "taobaoNumberID": 837058645,
 * "isEarnedSellerEvaluatePermitted": true,
 * "isShopKeeperOwningMoney": false,
 * "infoCompleteUrl": null,
 * "isBigShopKeeper": false,
 * "shopKeeperId": 120134623,
 * "isEarnedSellerShopPermitted": false,
 * "_tb_token_": "HP1B1kCmnFLq",
 * "isInCPAWhiteList": false,
 * "newItemEventCount": 0
 * },
 * "info": {
 * "message": null,
 * "ok": true
 * }
 * }
 */
public class AuthorWebView extends WebView {
    public interface AuthorWebViewCallback {
        void onAuthorSuccess();
    }

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


    private AuthorWebViewCallback authorWebViewCallback;

    public AuthorWebView(Context context) {
        super(context);
        this.init();
    }

    public AuthorWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public AuthorWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.setWebContentsDebuggingEnabled(true);
        this.setScrollContainer(false);
        this.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        this.getSettings().setDefaultTextEncodingName("UTF-8");
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.getSettings().setLoadsImagesAutomatically(true);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setDatabaseEnabled(true);
        this.getSettings().setDomStorageEnabled(true);
//        this.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, final String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                if (url.equals(URL_INDEX)) {
                    //view.loadUrl("javascript:window.local_obj.showSource(document.body)");
                    if (AuthorWebView.this.authorWebViewCallback != null) {
                        AuthorWebView.this.authorWebViewCallback.onAuthorSuccess();
                    }
                }
                super.onPageFinished(view, url);
            }
        });

        this.loadUrl(URL_LOGIN);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.resumeTimers();
    }

    public void setAuthorWebViewCallback(AuthorWebViewCallback authorWebViewCallback) {
        this.authorWebViewCallback = authorWebViewCallback;
    }
}
