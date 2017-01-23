package com.freedom.lauzy.gankpro.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.freedom.lauzy.gankpro.function.ValueContants.ImageValue.IMAGE_URL;
import static com.freedom.lauzy.gankpro.function.ValueContants.ImageValue.PUBLISH_DATE;

public class DailyActivity extends BaseActivity {


    @BindView(R.id.img_title)
    ImageView mImgTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.rv_daily)
    RecyclerView mRvDaily;
    @BindView(R.id.srl_daily)
    SwipeRefreshLayout mSrlDaily;
    @BindView(R.id.fab_beauty)
    FloatingActionButton mFabBeauty;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void loadData() {
        String imgUrl = getIntent().getStringExtra(IMAGE_URL);
        String publishDate = getIntent().getStringExtra(PUBLISH_DATE);
//        Bitmap img_data = getIntent().getParcelableExtra("img_data");
        /*CollectionEntityDao entityDao = GankApp.getGankApp().getDaoSession().getCollectionEntityDao();
        CollectionEntity entity = entityDao.queryBuilder().where(CollectionEntityDao.Properties.Id.eq(img_id)).build().list().get(0);*/
        Picasso.with(this).load(imgUrl).into(mImgTitle);
    }

    @Override
    protected void initViews() {
        mToolbarLayout.setTitle("Daily");
        ViewCompat.setTransitionName(mImgTitle, "transitionImg");
    }

}
