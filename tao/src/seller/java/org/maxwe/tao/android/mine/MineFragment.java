package org.maxwe.tao.android.mine;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.INetWorkManager;
import org.maxwe.tao.android.NetworkManager;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.account.model.AccountSignOutRequestModel;
import org.maxwe.tao.android.account.model.TokenModel;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.activity.ModifyActivity;
import org.maxwe.tao.android.activity.WebViewActivity;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.common.BrandActivity;
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
    private static final int CODE_REQUEST_AUTHOR = 0;

    @Event(value = R.id.bt_frg_mine_login_tao_bao, type = View.OnClickListener.class)
    private void onTaoBaoLoginAction(View view) {
        AuthorActivity.requestTaoLoginStatus(this.getContext(), new AuthorActivity.TaoLoginStatusCallback() {
            @Override
            public void onNeedLoginCallback() {
                Intent intent = new Intent(MineFragment.this.getContext(), AuthorActivity.class);
                intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                MineFragment.this.startActivityForResult(intent, MineFragment.this.CODE_REQUEST_AUTHOR);
            }

            @Override
            public void onNeedBrandCallback() {
                Toast.makeText(MineFragment.this.getContext(),"您已经登录，赶快生成推广位吧",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNeedOkCallback() {
                Toast.makeText(MineFragment.this.getContext(),"您已经登录，无需再次登录",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNeedErrorCallback() {

            }
        });
    }
    @Event(value = R.id.bt_frg_mine_promotion, type = View.OnClickListener.class)
    private void onPromotionAction(View view) {
        AuthorActivity.requestTaoLoginStatus(this.getContext(), new AuthorActivity.TaoLoginStatusCallback() {
            @Override
            public void onNeedLoginCallback() {
                Intent intent = new Intent(MineFragment.this.getContext(), AuthorActivity.class);
                intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                MineFragment.this.startActivityForResult(intent, MineFragment.this.CODE_REQUEST_AUTHOR);
            }

            @Override
            public void onNeedBrandCallback() {
                Intent intent = new Intent(MineFragment.this.getContext(), BrandActivity.class);
                MineFragment.this.startActivity(intent);
            }

            @Override
            public void onNeedOkCallback() {
                Intent intent = new Intent(MineFragment.this.getContext(), BrandActivity.class);
                MineFragment.this.startActivity(intent);
            }

            @Override
            public void onNeedErrorCallback() {

            }
        });
    }

    @Event(value = R.id.bt_frg_mine_password, type = View.OnClickListener.class)
    private void onModifyPasswordAction(View view) {
        Intent intent = new Intent(this.getActivity(), ModifyActivity.class);
        this.getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_MODIFY_PASSWORD);
    }

    @Event(value = R.id.bt_frg_mine_about_us, type = View.OnClickListener.class)
    private void onAboutUsAction(View view) {
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.INTENT_KEY_PAGE_URL,
                this.getString(R.string.string_url_domain) +
                        this.getString(R.string.string_url_aboutus)
        );
        this.startActivity(intent);
    }

    @Event(value = R.id.bt_frg_mine_exit, type = View.OnClickListener.class)
    private void onExitAction(View view) {
        try {
            TokenModel sessionModel = SharedPreferencesUtils.getSession(this.getContext());
            AccountSignOutRequestModel requestModel = new AccountSignOutRequestModel(sessionModel);
            requestModel.setSign(sessionModel.getEncryptSing());
            String url = this.getString(R.string.string_url_domain) + this.getString(R.string.string_url_account_logout);
            NetworkManager.requestByPostNew(url, requestModel, new INetWorkManager.OnNetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    SellerApplication.currentUserEntity = null;
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onLoginTimeout(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onParamsError(String result) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
                    SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
                    Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
                    MineFragment.this.getActivity().startActivity(intent);
                    MineFragment.this.getActivity().finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            SharedPreferencesUtils.clearSession(MineFragment.this.getContext());
            SharedPreferencesUtils.clearAuthor(MineFragment.this.getContext());
            Intent intent = new Intent(MineFragment.this.getContext(), LoginActivity.class);
            MineFragment.this.getActivity().startActivity(intent);
            MineFragment.this.getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_REQUEST_AUTHOR:
                if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {
                    onPromotionAction(null);
                }
                break;
            default:
                break;
        }

    }
}
