package org.maxwe.tao.android.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.api.goods.GoodsManager;
import org.maxwe.tao.android.api.goods.GoodsQueryEntity;
import org.xutils.view.annotation.ContentView;

/**
 * Created by Pengwei Ding on 2016-12-24 10:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {
//    @ViewInject(R.id.ib_frg_coupon)
//    private ImageButton ib_frg_coupon;
//    @ViewInject(R.id.ib_frg_refine)
//    private ImageButton ib_frg_refine;
//    @ViewInject(R.id.ib_frg_live)
//    private ImageButton ib_frg_live;
//    @ViewInject(R.id.ib_frg_link)
//    private ImageButton ib_frg_link;
//    @ViewInject(R.id.ib_frg_newbie)
//    private ImageButton ib_frg_newbie;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GoodsQueryEntity goodsQueryEntity = new GoodsQueryEntity("女装", GoodsQueryEntity.TOTAL_SALES_DES, 0, 20);
        onRequestGoodsList(goodsQueryEntity);
    }


    //    @Event(value = R.id.ib_frg_link, type = View.OnClickListener.class)
    private void onConvertLinkAction(View view) {
    }

    private void onRequestGoodsList(GoodsQueryEntity goodsQueryEntity) {
        GoodsManager.queryGoods(goodsQueryEntity, new GoodsManager.OnRequestCallback() {
            @Override
            public void onSuccess(String text) {
                System.out.println(text);
            }

            @Override
            public void onError(Throwable exception, Object object) {
                exception.printStackTrace();
            }
        });
    }

}
