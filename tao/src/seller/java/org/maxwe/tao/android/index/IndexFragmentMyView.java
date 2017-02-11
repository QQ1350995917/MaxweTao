package org.maxwe.tao.android.index;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Pengwei Ding on 2017-02-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class IndexFragmentMyView extends IndexFragmentView{
    public IndexFragmentMyView(Context context) {
        super(context);
        init();
    }

    public IndexFragmentMyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexFragmentMyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.getSearchBar().setVisibility(View.GONE);
        this.getToolsBarView().setVisibility(View.GONE);
    }

}
