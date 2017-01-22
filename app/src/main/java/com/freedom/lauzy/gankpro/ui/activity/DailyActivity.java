package com.freedom.lauzy.gankpro.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseActivity;

public class DailyActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        CollapsingToolbarLayout coordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        coordinatorLayout.setTitle("Daily");
        ImageView imageView = (ImageView) findViewById(R.id.img_toolbar);
        ViewCompat.setTransitionName(imageView,"transitionImg");
    }
}
