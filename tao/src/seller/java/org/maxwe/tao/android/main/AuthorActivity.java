package org.maxwe.tao.android.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-17 14:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_author)
public class AuthorActivity extends BaseActivity {
    public static String KEY_CONTENT_HTML = "KEY_CONTENT_HTML";

    @ViewInject(R.id.wv_act_author)
    private WebView wv_act_author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wv_act_author.getSettings().setDefaultTextEncodingName("UTF-8");
        this.wv_act_author.getSettings().setJavaScriptEnabled(true);
        this.wv_act_author.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.wv_act_author.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.wv_act_author.getSettings().setLoadsImagesAutomatically(true);
        Bundle extras = this.getIntent().getExtras();
        if (extras == null) {

        } else {
            Object o = extras.get(KEY_CONTENT_HTML);
            if (o == null) {

            } else {
                String htmlContent = o.toString().replace("\r\n","").replace("\t","");
                System.out.println(htmlContent);
                this.wv_act_author.loadData(htmlContent, "text/html;charset=UTF-8", null);
            }
        }
    }
}
