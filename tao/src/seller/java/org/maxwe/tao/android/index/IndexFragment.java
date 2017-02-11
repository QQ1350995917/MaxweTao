package org.maxwe.tao.android.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by Pengwei Ding on 2016-12-24 10:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
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

    @ViewInject(R.id.tv_frg_index_tao)
    private TextView tv_frg_index_tao;
    @ViewInject(R.id.tv_frg_index_my)
    private TextView tv_frg_index_my;

    @ViewInject(R.id.vp_frg_index_container)
    private ViewPager vp_frg_index_container;

    private ArrayList<View> pages = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayoutInflater inflater = this.getLayoutInflater(savedInstanceState);
        IndexFragmentTaoView taoView =  new IndexFragmentTaoView(this.getContext());
        IndexFragmentMyView myView =  new IndexFragmentMyView(this.getContext());
        this.pages.add(taoView);
        this.pages.add(myView);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pages.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pages.get(position));
                return pages.get(position);
            }
        };

        vp_frg_index_container.addOnPageChangeListener(this);
        vp_frg_index_container.setAdapter(pagerAdapter);
        vp_frg_index_container.setCurrentItem(0);
        tv_frg_index_tao.setTextColor(Color.parseColor("#000000"));
        tv_frg_index_my.setTextColor(Color.parseColor("#CCCCCC"));
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

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            tv_frg_index_tao.setTextColor(Color.parseColor("#000000"));
            tv_frg_index_my.setTextColor(Color.parseColor("#CCCCCC"));
        } else if (position == 1){
            tv_frg_index_tao.setTextColor(Color.parseColor("#CCCCCC"));
            tv_frg_index_my.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //    @Event(value = R.id.bt_frg_search, type = View.OnClickListener.class)
//    private void onSearchAction(View view) {
////        Intent intent = new Intent(IndexFragment.this.getContext(), AuthorActivity.class);
////        intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
////        IndexFragment.this.startActivityForResult(intent, 0);
//
//        GoodsManager.requestGoods(null, new TaoNetwork.OnRequestCallback() {
//            @Override
//            public void onSuccess(String text) {
//                System.out.println(text);
//            }
//
//            @Override
//            public void onError(Throwable ex, IModel object) {
//                System.out.println();
//            }
//        });
//    }
}
