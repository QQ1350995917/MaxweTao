package org.maxwe.tao.android.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;

import org.maxwe.tao.android.BaseFragment;
import org.maxwe.tao.android.R;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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
    }


//    @Event(value = R.id.ib_frg_link, type = View.OnClickListener.class)
    private void onConvertLinkAction(View view) {
    }

}
