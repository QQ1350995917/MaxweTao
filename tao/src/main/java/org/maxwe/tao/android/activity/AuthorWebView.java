package org.maxwe.tao.android.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Pengwei Ding on 2017-02-05 14:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AuthorWebView extends WebView {

    private static final String isLoginRequest = "http://ad.alimama.com/cps/shopkeeper/loginMessage.json";

    private static final String main = "http://pub.alimama.com/myunion.htm";
    private static final String index2 = "https://login.m.taobao.com/login.htm?loginFrom=wap_tb";
    private static final String index3 = "http://h5.m.taobao.com/mlapp/mytaobao.html#mlapp-mytaobao";

    // 登录页面
    private static final String URL_LOGIN = "https://login.m.taobao.com/login.htm?redirectURL=http://pub.alimama.com/common/site/generalize/guideList.json";
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

    private boolean isLogin = false;
    private boolean isUnion = false;

    private Handler addNewShopping = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

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
//        this.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                view.loadUrl("javascript:window.local_obj.showSource('<html>'+document.getElementsByTagName('html')+'</html>');");
//                System.out.println(url);
//                if (url.equals(URL_INDEX)) {
//                    if (!isLogin) {
//                        view.loadUrl(URL_SHOPPING);
////                        isLogin = true;
////                        view.loadUrl(SCRIPT_ON_MY_UNION);
//                    }
//
//                } else if (url.equals(URL_SHOPPING)) {
//                    int baseDelay = 5000;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.loadUrl(SCRIPT_SHOPPING_ADD_CLICK);
//                            System.out.println("执行点击事件");
//                        }
//                    },baseDelay + 2000);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.loadUrl(SCRIPT_SHOPPING_ADD_NAME);
//                            System.out.println("设置名称");
//                        }
//                    },baseDelay + 6000);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.loadUrl(SCRIPT_SHOPPING_ADD_CATEGORY);
//                            System.out.println("设置类别");
//                        }
//                    },baseDelay + 4000);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.loadUrl(SCRIPT_SHOPPING_ADD_ACCOUNT);
//                            System.out.println("设置账号");
//                        }
//                    },baseDelay + 5000);
//
//                }
                super.onPageFinished(view, url);
            }
        });
        this.loadUrl(isLoginRequest);
//        this.loadUrl("https://login.taobao.com");
    }


    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println(html);
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
}
