package org.maxwe.tao.android.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.WebViewActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Pengwei Ding on 2016-12-24 10:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 淘宝商品
     * @param view
     */
    @Event(value = R.id.bt_frg_index_tao_goods, type = View.OnClickListener.class)
    private void onTaoGoodsAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        intent.putExtra(GoodsListActivity.INTENT_KEY_URL_TYPE,0);
        IndexFragment.this.startActivity(intent);
    }

    /**
     * 站内商品
     * @param view
     */
    @Event(value = R.id.bt_frg_index_tao_mami, type = View.OnClickListener.class)
    private void onTaoMaMiAction(View view) {
//        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
//        IndexFragment.this.startActivity(intent);
        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
    }

    /**
     * 高佣金商品
     * @param view
     */
    @Event(value = R.id.bt_frg_index_tao_money, type = View.OnClickListener.class)
    private void onMoneyAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        intent.putExtra(GoodsListActivity.INTENT_KEY_URL_TYPE,1);
        IndexFragment.this.startActivity(intent);
    }

    /**
     * 其他商品
     * @param view
     */
    @Event(value = R.id.bt_frg_index_tao_other, type = View.OnClickListener.class)
    private void onOhterAction(View view) {
//        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
//        IndexFragment.this.startActivity(intent);
        Toast.makeText(this.getContext(),"开发中，敬请关注",Toast.LENGTH_SHORT).show();
    }


    /**
     * 新手教程
     * @param view
     */
    @Event(value = R.id.bt_frg_index_tutorial, type = View.OnClickListener.class)
    private void onTutorialAction(View view) {
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.INTENT_KEY_PAGE_URL,
                this.getString(R.string.string_url_domain) +
                        this.getString(R.string.string_url_system_tutorial)
        );
        this.startActivity(intent);
    }

    /**
     * 用户必看
     * @param view
     */
    @Event(value = R.id.bt_frg_index_readme, type = View.OnClickListener.class)
    private void onReadmeAction(View view) {
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.INTENT_KEY_PAGE_URL,
                this.getString(R.string.string_url_domain) +
                        this.getString(R.string.string_url_system_readme)
        );
        this.startActivity(intent);
    }

    /**
     * 功能介绍
     * @param view
     */
    @Event(value = R.id.bt_frg_index_introduction, type = View.OnClickListener.class)
    private void onIntroductionAction(View view) {
        Intent intent = new Intent(this.getContext(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.INTENT_KEY_PAGE_URL,
                this.getString(R.string.string_url_domain) +
                        this.getString(R.string.string_url_system_introduction)
        );
        this.startActivity(intent);
    }
}
