package org.maxwe.tao.android.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.api.IModel;
import org.maxwe.tao.android.api.TaoNetwork;
import org.maxwe.tao.android.api.authorize.AuthorRequestModel;
import org.maxwe.tao.android.api.authorize.AuthorizeManager;
import org.maxwe.tao.android.main.AuthorActivity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

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

//        ItemManager.requestItem(0, new TaoNetwork.OnRequestCallback() {
//            @Override
//            public void onSuccess(String text) {
//                System.out.println(text);
//            }
//
//            @Override
//            public void onError(Throwable ex, CommonModel object) {
//                System.out.println("==========================");
//            }
//        });
//        GoodsQueryEntity goodsQueryEntity = new GoodsQueryEntity("女装", GoodsQueryEntity.TOTAL_SALES_DES, 0, 20);
//        GoodsManager.requestGoods(goodsQueryEntity, new TaoNetwork.OnRequestCallback() {
//            @Override
//            public void onSuccess(String text) {
//                System.out.println(text);
//            }
//
//            @Override
//            public void onError(Throwable exception, CommonModel commonModel) {
//                exception.printStackTrace();
//            }
//        });
    }


    @Event(value = R.id.bt_frg_search, type = View.OnClickListener.class)
    private void onSearchAction(View view) {
        AuthorRequestModel authorRequestModel = new AuthorRequestModel("23595494", 1234);
        AuthorizeManager.authorize(authorRequestModel, new TaoNetwork.OnRequestCallback() {
            @Override
            public void onSuccess(String text) {
                Intent intent = new Intent(IndexFragment.this.getContext(), AuthorActivity.class);
                intent.putExtra(AuthorActivity.KEY_CONTENT_HTML, text);
                IndexFragment.this.startActivityForResult(intent, 0);
            }

            @Override
            public void onError(Throwable ex, IModel object) {
                ex.printStackTrace();
            }
        });
    }

}
