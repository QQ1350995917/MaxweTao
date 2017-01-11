package org.maxwe.tao.android.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.account.model.SessionModel;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.main.LinkFragment;
import org.maxwe.tao.android.main.MainActivity;
import org.maxwe.tao.android.utils.SharedPreferencesUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Pengwei Ding on 2016-12-24 10:12.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {
    @Event(value = R.id.bt_frg_mine_promotion, type = View.OnClickListener.class)
    private void onPromotionAction(View view) {
        Toast.makeText(MineFragment.this.getActivity(), "推广位开发中，敬请关注...", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_frg_mine_link_convert, type = View.OnClickListener.class)
    private void onLinkConvertAction(View view) {
        Intent intent = new Intent(this.getActivity(), LinkFragment.class);
        this.getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_CONVERT_LINK);
    }

    @Event(value = R.id.bt_frg_mine_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this.getActivity(), ModifyActivity.class);
        this.getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_frg_mine_newbie, type = View.OnClickListener.class)
    private void onNewbieAction(View view) {
        Toast.makeText(MineFragment.this.getActivity(), "教程开发中，敬请关注...", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_frg_mine_about_us, type = View.OnClickListener.class)
    private void onAboutUsAction(View view) {
        Toast.makeText(MineFragment.this.getActivity(), "开发中，敬请关注...", Toast.LENGTH_SHORT).show();
    }

    @Event(value = R.id.bt_frg_mine_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {
        SessionModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
        try {
            sessionModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPost(url, sessionModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
        }
    }
}
