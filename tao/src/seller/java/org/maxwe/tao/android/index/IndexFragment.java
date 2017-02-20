package org.maxwe.tao.android.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
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

    @Event(value = R.id.sv_frg_index_search, type = View.OnClickListener.class)
    private void onSearchAction(View view) {
//        Intent intent = new Intent(IndexFragment.this.getContext(), AuthorActivity.class);
//        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
//        IndexFragment.this.startActivityForResult(intent, 0);
    }

    @Event(value = R.id.bt_frg_index_tao_goods, type = View.OnClickListener.class)
    private void onTaoGoodsAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        IndexFragment.this.startActivity(intent);
    }

    @Event(value = R.id.bt_frg_index_tao_mami, type = View.OnClickListener.class)
    private void onTaoMaMiAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        IndexFragment.this.startActivity(intent);
    }


    @Event(value = R.id.bt_frg_index_tao_money, type = View.OnClickListener.class)
    private void onMoneyAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        IndexFragment.this.startActivity(intent);
    }

    @Event(value = R.id.bt_frg_index_tao_other, type = View.OnClickListener.class)
    private void onOhterAction(View view) {
        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
        IndexFragment.this.startActivity(intent);
    }
}
