package org.maxwe.tao.android.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.common.BrandActivity;
import org.maxwe.tao.android.goods.alimama.GoodsEntity;
import org.maxwe.tao.android.goods.alimama.GoodsRequestModel;
import org.maxwe.tao.android.goods.alimama.GoodsResponseModel;
import org.maxwe.tao.android.index.GoodsDetailActivity;
import org.maxwe.tao.android.response.ResponseModel;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String convertUrl = null;

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
            this.et_frg_link_content.setText(resultString);
            this.bt_frg_link_result.setVisibility(View.GONE);
            Pattern p = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(resultString);
            matcher.find();
//            System.out.println(matcher.group());

//            if (resultString.startsWith("http")) {
//                this.et_frg_link_content.setText(resultString);
//                this.bt_frg_link_result.setVisibility(View.GONE);
//            } else {
//                Toast.makeText(this.getContext(), "您粘贴板上的内容不符合转链要求", Toast.LENGTH_SHORT).show();
//            }
        }
    }


    @Event(value = R.id.bt_act_link_action, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        this.convertUrl = et_frg_link_content.getText().toString();
        if (TextUtils.isEmpty(this.convertUrl)) {
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

    private GoodsEntity aliGoodsEntity;

    private void onRequestFinishBySuccess(List<GoodsEntity> aliGoodsEntities) {
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
        GoodsRequestModel aliGoodsRequestModel = new GoodsRequestModel();
        aliGoodsRequestModel.setQ(this.convertUrl);
        aliGoodsRequestModel.setT(sessionModel.getT());
        aliGoodsRequestModel.setId(sessionModel.getId());
        aliGoodsRequestModel.setCellphone(sessionModel.getCellphone());
        aliGoodsRequestModel.setApt(this.getResources().getInteger(R.integer.integer_app_type));
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(AuthorActivity.URL_LOGIN_MESSAGE);
        aliGoodsRequestModel.setCookie(cookie);
        try {
            aliGoodsRequestModel.setSign(sessionModel.getEncryptSing());
            NetworkManager.requestByPostNew(url, aliGoodsRequestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    GoodsResponseModel responseModel = JSON.parseObject(result, GoodsResponseModel.class);
                    if (responseModel.getCode() == ResponseModel.RC_SUCCESS) {
                        onRequestFinishBySuccess(responseModel.getGoodsEntities());
                    }
                    Toast.makeText(LinkFragment.this.getContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
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
