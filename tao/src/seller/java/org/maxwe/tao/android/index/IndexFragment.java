package org.maxwe.tao.android.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.maxwe.tao.android.R;

/**
 * Created by Pengwei Ding on 2016-12-24 10:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IndexFragment extends Fragment implements View.OnClickListener {
    private ImageButton ib_frg_coupon;
    private ImageButton ib_frg_refine;
    private ImageButton ib_frg_live;
    private ImageButton ib_frg_link;
    private ImageButton ib_frg_newbie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        this.ib_frg_coupon = (ImageButton) view.findViewById(R.id.ib_frg_coupon);
        this.ib_frg_refine = (ImageButton) view.findViewById(R.id.ib_frg_refine);
        this.ib_frg_live = (ImageButton) view.findViewById(R.id.ib_frg_live);
        this.ib_frg_link = (ImageButton) view.findViewById(R.id.ib_frg_link);
        this.ib_frg_newbie = (ImageButton) view.findViewById(R.id.ib_frg_newbie);

        this.ib_frg_coupon.setOnClickListener(this);
        this.ib_frg_refine.setOnClickListener(this);
        this.ib_frg_live.setOnClickListener(this);
        this.ib_frg_link.setOnClickListener(this);
        this.ib_frg_newbie.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageButton) {
            switch (v.getId()) {
                case R.id.ib_frg_coupon:
                    break;
                case R.id.ib_frg_refine:
                    break;
                case R.id.ib_frg_live:
                    break;
                case R.id.ib_frg_link:
                    Intent intent = new Intent(this.getContext(),LinkActivity.class);
                    this.getContext().startActivity(intent);
                    break;
                case R.id.ib_frg_newbie:
                    break;
                default:
                    break;
            }
        }
    }
}
