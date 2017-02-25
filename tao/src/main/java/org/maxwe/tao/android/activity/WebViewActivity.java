package org.maxwe.tao.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.maxwe.tao.android.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-02-25 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 专用显示静态网页
 */
@ContentView(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {
    public static final String INTENT_KEY_PAGE_URL = "INTENT_KEY_PAGE_URL";

    @ViewInject(R.id.wb_act_webview_title)
    private TextView wb_act_webview_title;

    @ViewInject(R.id.wb_act_webview)
    private WebView wb_act_webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String pageUrl = this.getIntent().getStringExtra(INTENT_KEY_PAGE_URL);
        this.wb_act_webview.setWebContentsDebuggingEnabled(true);
        this.wb_act_webview.setScrollContainer(false);
        this.wb_act_webview.getSettings().setDefaultTextEncodingName("UTF-8");
        this.wb_act_webview.getSettings().setJavaScriptEnabled(true);
        this.wb_act_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.wb_act_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.wb_act_webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.wb_act_webview.getSettings().setLoadsImagesAutomatically(true);
        this.wb_act_webview.getSettings().setAllowFileAccess(true);
        this.wb_act_webview.getSettings().setAppCacheEnabled(true);
        this.wb_act_webview.getSettings().setDatabaseEnabled(true);
        this.wb_act_webview.getSettings().setDomStorageEnabled(true);
        this.wb_act_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wb_act_webview_title.setText(view.getTitle());
            }
        });
        this.wb_act_webview.loadUrl(pageUrl);
    }

    @Event(value = R.id.bt_act_webview_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
    }

}
