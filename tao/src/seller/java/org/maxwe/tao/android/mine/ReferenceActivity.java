package org.maxwe.tao.android.mine;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.index.GoodsDetailActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Pengwei Ding on 2017-04-09 12:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 推广赚积分
 */
@ContentView(R.layout.activity_webview)
public class ReferenceActivity extends BaseActivity {
    public static final String INTENT_KEY_PAGE_URL = "INTENT_KEY_PAGE_URL";

    @ViewInject(R.id.wb_act_webview_title)
    private TextView wb_act_webview_title;

    @ViewInject(R.id.wb_act_webview)
    private WebView wb_act_webview;

    private IWXAPI iwxapi;
    private Tencent instance;
    private IUiListener shareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Toast.makeText(ReferenceActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(ReferenceActivity.this, " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(ReferenceActivity.this, " 分享取消啦", Toast.LENGTH_SHORT).show();
        }
    };

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
        this.wb_act_webview.addJavascriptInterface(new JsInterface(), "AndroidWebView");
        this.wb_act_webview.loadUrl(pageUrl);

        this.instance = Tencent.createInstance(SellerApplication.SHARE_QQ_KEY, this);
        this.iwxapi = WXAPIFactory.createWXAPI(this, SellerApplication.SHARE_WEI_XIN_KEY);
        this.iwxapi.registerApp(SellerApplication.SHARE_WEI_XIN_KEY);
    }

    public void shareToQQ(boolean isQQZone, String message) {
        final Bundle params = new Bundle();
        if (isQQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        }

        params.putString(QQShare.SHARE_TO_QQ_TITLE, "淘妈咪推荐分享");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, message);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, message);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, this.getString(R.string.string_url_domain) + "/webapp/asserts/images/taomami.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "淘妈咪");
        if (isQQZone) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        }
        instance.shareToQQ(this, params, shareListener);
    }

    public void shareToWeiChat(boolean isWeiChatZone, String message) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;//不要透明通道
        options.inPremultiplied = false;//不允许透明度
        File file = new File(ReferenceActivity.this.getFilesDir() + File.separator + "taomami.png");
        if (!file.exists()) {
            try {
                AssetManager assets = this.getAssets();
                InputStream open = assets.open("taomami.png");
                OutputStream myOutput = new FileOutputStream(file);
                byte[] buffer = new byte[open.available()];
                int length = open.read(buffer);
                myOutput.write(buffer, 0, length);
                myOutput.flush();
                open.close();
                myOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bitmap sourceBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        WXImageObject wxImageObject = new WXImageObject(sourceBitmap);
        wxImageObject.setImagePath(file.getAbsolutePath());
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxImageObject);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "img" + String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = wxMediaMessage;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = isWeiChatZone ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);
    }

    @Event(value = R.id.bt_act_webview_back, type = View.OnClickListener.class)
    private void onBackAction(View view) {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    class JsInterface {
        @JavascriptInterface
        public void onShareToWeChat(String message) {
            ClipboardManager cm = (ClipboardManager) ReferenceActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(message);
            shareToWeiChat(false, message);
        }

        @JavascriptInterface
        public void onShareToWeChatZone(String message) {
            ClipboardManager cm = (ClipboardManager) ReferenceActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(message);
            shareToWeiChat(true, message);
        }

        @JavascriptInterface
        public void onShareToQQ(String message) {
            ClipboardManager cm = (ClipboardManager) ReferenceActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(message);
            shareToQQ(false, message);
        }

        @JavascriptInterface
        public void onShareToQQZone(String message) {
            ClipboardManager cm = (ClipboardManager) ReferenceActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(message);
            shareToQQ(true, message);
        }
    }
}
