package org.maxwe.tao.android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
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
    private AuthorWebView wv_act_author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wv_act_author.setAuthorWebViewCallback(new AuthorWebView.AuthorWebViewCallback() {
            @Override
            public void onAuthorSuccess() {
                onSuccessBack();
            }
        });
    }

    @Event(value = R.id.bt_act_author_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.onBackPressed();
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
