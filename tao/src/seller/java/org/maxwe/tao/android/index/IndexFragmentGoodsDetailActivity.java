package org.maxwe.tao.android.index;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.maxwe.tao.android.R;
import org.maxwe.tao.android.activity.BaseActivity;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.goods.GoodsEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Pengwei Ding on 2017-02-12 13:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
@ContentView(R.layout.activity_goods_detail)
public class IndexFragmentGoodsDetailActivity extends BaseActivity {
    public static final String KEY_GOODS = "goodsEntity";
    @ViewInject(R.id.iv_act_goods_detail_image)
    private SimpleDraweeView iv_act_goods_detail_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoodsEntity goodsEntity = (GoodsEntity) this.getIntent().getExtras().get(KEY_GOODS);
        iv_act_goods_detail_image.setImageURI(Uri.parse(goodsEntity.getPict_url()));
    }

    @Event(value = R.id.bt_act_goods_detail_back, type = View.OnClickListener.class)
    private void onModifyBackAction(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}
