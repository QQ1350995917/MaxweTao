package org.maxwe.tao.android.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.agent.AgentEntityInter;
import org.maxwe.tao.android.main.MainActivity;
import org.maxwe.tao.android.response.Response;
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
        Intent intent = new Intent(this.getActivity(), LinkActivity.class);
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
        final SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
        AgentEntityInter agentEntityInter = new AgentEntityInter();
        agentEntityInter.setT(sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null));
        NetworkManager.requestLogout(agentEntityInter, new NetworkManager.OnRequestCallback() {
            @Override
            public void onSuccess(Response response) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                SharedPreferences.Editor remove = edit.remove(Constants.KEY_SHARD_T_CONTENT);
                remove.commit();
                Intent intent = new Intent(MineFragment.this.getActivity(), LoginActivity.class);
                MineFragment.this.getActivity().startActivity(intent);
                MineFragment.this.getActivity().finish();
            }

            @Override
            public void onError(Throwable exception, Object agentEntity) {
                Toast.makeText(MineFragment.this.getActivity(), R.string.string_toast_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
