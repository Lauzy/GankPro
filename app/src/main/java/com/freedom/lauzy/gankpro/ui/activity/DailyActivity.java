package com.freedom.lauzy.gankpro.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;
import com.freedom.lauzy.gankpro.common.utils.TransitionHelper;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;
import com.freedom.lauzy.gankpro.function.utils.DateUtils;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.freedom.lauzy.gankpro.function.view.DailyItemDecoration;
import com.freedom.lauzy.gankpro.presenter.DailyPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.DailyAdapter;
import com.freedom.lauzy.gankpro.view.DailyView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.IMAGE_URL;
import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.PIC_DESC;
import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.PUBLISH_DATE;


public class DailyActivity extends BaseToolbarActivity {


    private static final String LYTAG = DailyActivity.class.getSimpleName();
    @BindView(R.id.img_title)
    ImageView mImgTitle;
    @BindView(R.id.toolbar_daily)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.rv_daily)
    RecyclerView mRvDaily;
    @BindView(R.id.srl_daily)
    SwipeRefreshLayout mSrlDaily;
    @BindView(R.id.fab_beauty)
    FloatingActionButton mFabBeauty;
    private Date mPublishDate;
    @BindView(R.id.stub_empty_view)
    ViewStub mEmptyViewStub;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.act_daily)
    View mContentView;
    private List<ItemBean> mItemBeen = new ArrayList<>();
    private DailyAdapter mAdapter;
    private DailyPresenter mDailyPresenter;
    private String mImgUrl;
    private String mPicDesc;

    @Override
    protected void loadData() {
        mImgUrl = getIntent().getStringExtra(IMAGE_URL);
        mPicDesc = getIntent().getStringExtra(PIC_DESC);
//        Bitmap img_data = getIntent().getParcelableExtra("img_data");
        /*CollectionEntityDao entityDao = GankApp.getGankApp().getDaoSession().getCollectionEntityDao();
        CollectionEntity entity = entityDao.queryBuilder().where(CollectionEntityDao.Properties.Id.eq(img_id)).build().list().get(0);*/
        ImageLoader.loadImage(this, mImgUrl, mImgTitle);
        if (mPublishDate == null) {
            Log.d(LYTAG, "loadData: date is null");
        }

        initPresenter();
        mDailyPresenter.initData();
    }

    private void initPresenter() {
        mDailyPresenter = new DailyPresenter(new DailyView() {
            @Override
            public void initRvData(List<ItemBean> data) {
                if (data == null) {
                    mEmptyViewStub.inflate();
                } else {
                    mItemBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
                }
                mSrlDaily.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<ItemBean> refreshData) {
                if (refreshData != null) {
                    mAdapter.removeAllData();
                    mAdapter.addData(refreshData);
                    mAdapter.notifyDataSetChanged();
                }
                mSrlDaily.setRefreshing(false);
            }

            @Override
            public void initError(Throwable e) {
                mSrlDaily.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mSrlDaily.setRefreshing(false);
            }
        }, mPublishDate);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initViews() {
        ViewCompat.setTransitionName(mImgTitle, "transitionImg");
        setupWindowAnimations();
        initTitle();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDaily.setLayoutManager(linearLayoutManager);

        mSrlDaily.setColorSchemeResources(R.color.color_srl_gray);
        mSrlDaily.setRefreshing(true);
        mAdapter = new DailyAdapter(DailyActivity.this, mItemBeen);
        mRvDaily.setAdapter(mAdapter);

        mSrlDaily.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlDaily.setRefreshing(false);
                mDailyPresenter.refreshData();
            }
        });
        /*mRvDaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    mFabBeauty.show();
                } else {
                    mFabBeauty.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/
        mRvDaily.addItemDecoration(new DailyItemDecoration(DailyActivity.this, mItemBeen));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().setEnterTransition(TransitionUtils.buildExplodeEnterAnim(new BounceInterpolator()));
        getWindow().setExitTransition(TransitionUtils.buildExplodeExitAnim(new AccelerateDecelerateInterpolator()));
        getWindow().setReturnTransition(TransitionUtils.buildExplodeExitAnim(new AnticipateInterpolator()));
    }

    private void initTitle() {
        mPublishDate = (Date) getIntent().getSerializableExtra(PUBLISH_DATE);
        mToolbarLayout.setTitle(DateUtils.toDate(mPublishDate));
        mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
        mToolbar.setPadding(0, ScreenUtils.getStatusHeight(GankApp.getInstance()), 0, 0);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.icon_arrow_white_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.fab_beauty, R.id.img_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_beauty:
              /*  Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.img_title:


                Intent intent = ImgBeautyActivity.newInstance(DailyActivity.this, mImgUrl, mPicDesc);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(DailyActivity.this, false, new
                        Pair<>(mImgTitle, getString(R.string.string_img_share_elements)));
                TransitionUtils.transitionTo(DailyActivity.this, intent, pairs);
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(this, mImgTitle, "transitionDetailImg").toBundle());
                } else {
                    startActivity(intent);
                }*/
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDailyPresenter != null && mDailyPresenter.isViewAttached()) {
            mDailyPresenter.detachView();
        }
    }
}
