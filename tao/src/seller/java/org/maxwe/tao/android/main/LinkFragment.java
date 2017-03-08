package org.maxwe.tao.android.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.common.BrandActivity;
import org.maxwe.tao.android.index.GoodsDetailActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-01-02 15:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_link)
public class LinkFragment extends BaseFragment {
    private static final int CODE_REQUEST_AUTHOR = 0;
    private static final int CODE_REQUEST_BRAND = 1;

    @ViewInject(R.id.et_frg_link_content)
    private EditText et_frg_link_content;

    @ViewInject(R.id.bt_frg_link_result)
    private Button bt_frg_link_result;

    private String url = null;

    @Event(value = R.id.et_frg_link_paste, type = View.OnClickListener.class)
    private void paste(View view) {
        ClipboardManager systemService = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (!systemService.hasPrimaryClip()) {
            Toast.makeText(this.getContext(), "粘贴板为空", Toast.LENGTH_SHORT).show();
        } else {
            ClipData clipData = systemService.getPrimaryClip();
            int count = clipData.getItemCount();
            String resultString = "";
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(this.getContext());
                resultString += str;
            }
            if (resultString.startsWith("http")) {
                this.et_frg_link_content.setText(resultString);
                this.bt_frg_link_result.setVisibility(View.GONE);
            } else {
                Toast.makeText(this.getContext(), "您粘贴板上的内容不符合转链要求", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LinkFragment.this.getContext(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    @Event(value = R.id.bt_act_link_action, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        this.url = et_frg_link_content.getText().toString();
        if (TextUtils.isEmpty(this.url)) {
            Toast.makeText(LinkFragment.this.getContext(), "请输入要转换的链接", Toast.LENGTH_SHORT).show();
        } else {
            AuthorActivity.requestTaoLoginStatus(this.getContext(), new AuthorActivity.TaoLoginStatusCallback() {
                @Override
                public void onNeedLoginCallback() {
                    Intent intent = new Intent(LinkFragment.this.getContext(), AuthorActivity.class);
                    intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                    LinkFragment.this.startActivityForResult(intent, LinkFragment.this.CODE_REQUEST_AUTHOR);
                }

                @Override
                public void onNeedBrandCallback() {
                    Intent intent = new Intent(LinkFragment.this.getContext(), BrandActivity.class);
                    LinkFragment.this.startActivityForResult(intent, LinkFragment.CODE_REQUEST_BRAND);
                }

                @Override
                public void onNeedOkCallback() {
                    requestTaoConvert();
                }

                @Override
                public void onNeedErrorCallback() {

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.CODE_REQUEST_AUTHOR) {
            if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                onLinkConvertAction(null);
            } else {
                Toast.makeText(this.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == this.CODE_REQUEST_BRAND) {
            if (resultCode == BrandActivity.CODE_RESULT_SUCCESS) {
                requestTaoConvert();
            } else if (resultCode == BrandActivity.CODE_RESULT_FAIL) {
                Toast.makeText(this.getContext(), "您尚未请选择推广位,申请失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private AliGoodsEntity aliGoodsEntity;

    private void onRequestFinishBySuccess(List<AliGoodsEntity> aliGoodsEntities) {
        if (aliGoodsEntities != null && aliGoodsEntities.size() > 0) {
            aliGoodsEntity = aliGoodsEntities.get(0);
            this.bt_frg_link_result.setVisibility(View.VISIBLE);
            String text = "下单地址为" + "\r\n\r\n" + this.aliGoodsEntity.getAuctionUrl().toLowerCase() + "\r\n\r\n" + "点击查看详情";
            this.bt_frg_link_result.setText(text);
        } else {
            aliGoodsEntity = null;
            this.bt_frg_link_result.setText("该链接不被支持");
        }
    }

    @Event(value = R.id.bt_frg_link_result, type = View.OnClickListener.class)
    private void onLinkShareAction(View view) {
        if (aliGoodsEntity != null) {
            Intent intent = new Intent(this.getContext(), GoodsDetailActivity.class);
            intent.putExtra(GoodsDetailActivity.KEY_GOODS, this.aliGoodsEntity);
            this.startActivity(intent);
        }
    }

    private void onRequestFinishByError() {
        Toast.makeText(this.getContext(), "呃 出错了", Toast.LENGTH_SHORT).show();
    }

    private void requestTaoConvert() {
        String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_ali_goods_search);
        TokenModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
        AliGoodsRequestModel aliGoodsRequestModel = new AliGoodsRequestModel();
        aliGoodsRequestModel.setQ(this.url);
        aliGoodsRequestModel.setT(sessionModel.getT());
        aliGoodsRequestModel.setId(sessionModel.getId());
        aliGoodsRequestModel.setCellphone(sessionModel.getCellphone());
        aliGoodsRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
        aliGoodsRequestModel.setCookie(cookie);
        try {
            aliGoodsRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPost(url, aliGoodsRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    List<AliGoodsEntity> aliGoodsEntities = JSON.parseArray(result, AliGoodsEntity.class);
                    if (aliGoodsEntities != null) {
                        onRequestFinishBySuccess(aliGoodsEntities);
                    }
                }

                @Override
                public void onEmptyResult(String result) {
                    super.onEmptyResult(result);
                    Toast.makeText(LinkFragment.this.getContext(), "没有数据了", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(LinkFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(LinkFragment.this.getContext());
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_timeout, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(LinkFragment.this.getContext(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onRequestFinishByError();
        }
    }
}
