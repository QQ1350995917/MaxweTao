package org.maxwe.tao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.TaoApplication;
import org.maxwe.tao.android.api.authorize.AuthorizeEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Pengwei Ding on 2017-01-22 14:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_author)
public class AuthorActivity extends BaseActivity {
    public static final String KEY_INTENT_OF_STATE_CODE = "KEY_INTENT_OF_STATE_CODE";
    public static final String KEY_INTENT_OF_AUTHOR = "KEY_INTENT_OF_AUTHOR";
    public static final int CODE_RESULT_OF_AUTHOR_FAIL = 0;
    public static final int CODE_RESULT_OF_AUTHOR_SUCCESS = 1;

    @ViewInject(R.id.wv_act_author)
    private WebView wv_act_author;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wv_act_author.getSettings().setDefaultTextEncodingName("UTF-8");
        this.wv_act_author.getSettings().setJavaScriptEnabled(true);
        this.wv_act_author.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.wv_act_author.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.wv_act_author.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.wv_act_author.getSettings().setLoadsImagesAutomatically(true);
        this.wv_act_author.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.contains("access_token")) {
                    AuthorizeEntity authorizeEntity = new AuthorizeEntity(url);
                    onSuccessBack(authorizeEntity);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        this.wv_act_author.loadUrl("https://oauth.m.taobao.com/authorize?state=12345&response_type=token&view=wap&client_id=" + TaoApplication.TAO_APP_KEY);
    }

    private void onSuccessBack(AuthorizeEntity authorizeEntity) {
        Intent intent = new Intent();
        intent.putExtra(this.KEY_INTENT_OF_AUTHOR, authorizeEntity);
        this.setResult(CODE_RESULT_OF_AUTHOR_SUCCESS, intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(CODE_RESULT_OF_AUTHOR_FAIL);
        this.finish();
    }
}
