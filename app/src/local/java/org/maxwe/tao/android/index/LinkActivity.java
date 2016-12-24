package org.maxwe.tao.android.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import org.maxwe.tao.android.R;

/**
 * Created by Pengwei Ding on 2016-12-24 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class LinkActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ib_act_link_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_link);
        this.ib_act_link_back = (ImageButton)this.findViewById(R.id.ib_act_link_back);
        this.ib_act_link_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
