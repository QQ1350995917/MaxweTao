package org.maxwe.tao.android.main;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.AuthorActivity;
import org.maxwe.tao.android.api.AuthorizeEntity;
import org.maxwe.tao.android.api.Position;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-01-02 15:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_link)
public class LinkFragment extends BaseFragment {
    private static final int CODE_REQUEST_AUTHOR = 0;

    @ViewInject(R.id.et_frg_link_content)
    private EditText et_frg_link_content;


    @Event(value = R.id.et_frg_link_paste, type = View.OnClickListener.class)
    private void paste(View view) {
//        ClipboardManager systemService = (ClipboardManager) this.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//        if (!systemService.hasPrimaryClip()) {
//            Toast.makeText(this.getContext(), "粘贴板为空", Toast.LENGTH_SHORT).show();
//        } else {
//            ClipData clipData = systemService.getPrimaryClip();
//            int count = clipData.getItemCount();
//            String resultString = "";
//            for (int i = 0; i < count; ++i) {
//                ClipData.Item item = clipData.getItemAt(i);
//                CharSequence str = item.coerceToText(this.getContext());
//                resultString += str;
//            }
//            et_frg_link_content.setText(resultString);
//        }
        String currentKeeperId = SharedPreferencesUtils.getCurrentKeeperId(this.getContext());
        Position currentPP = SharedPreferencesUtils.getCurrentPP(this.getContext());
        if (TextUtils.isEmpty(currentKeeperId)) {
            Toast.makeText(this.getContext(), "请登录", Toast.LENGTH_SHORT).show();
        } else if (currentPP == null || TextUtils.isEmpty(currentPP.getId()) || TextUtils.isEmpty(currentPP.getSiteId())) {
            Toast.makeText(this.getContext(), "请选择推广位", Toast.LENGTH_SHORT).show();
        } else {
            et_frg_link_content.setText("mm_" + currentKeeperId + "_" + currentPP.getSiteId() + "_" + currentPP.getId());
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
        AuthorizeEntity author = SharedPreferencesUtils.getAuthor(this.getContext());
        if (author == null || (System.currentTimeMillis() - author.getCreateTime()) / 1000 > Integer.parseInt(author.getExpires_in())) {
            Intent intent = new Intent(LinkFragment.this.getContext(), AuthorActivity.class);
            intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
            LinkFragment.this.startActivityForResult(intent, this.CODE_REQUEST_AUTHOR);
        } else {
            new ShareAction(LinkFragment.this.getActivity()).withTitle(this.getString(R.string.app_name)).withText("hello")
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                    .setCallback(umShareListener).open();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_REQUEST_AUTHOR:
                if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
//                    AuthorizeEntity serializableExtra = (AuthorizeEntity) data.getSerializableExtra(AuthorActivity.KEY_INTENT_OF_AUTHOR);
//                    SharedPreferencesUtils.saveAuthor(this.getContext(),serializableExtra);
                }
                break;
            default:
                //UMShareAPI.get(this.getContext()).onActivityResult(requestCode, resultCode, data);
                break;
        }

    }
}
