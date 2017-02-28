package com.freedom.lauzy.gankpro.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.Toast;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.common.base.BaseActivity;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;
import com.freedom.lauzy.gankpro.function.utils.DateUtils;
import com.freedom.lauzy.gankpro.presenter.DailyPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.DailyAdapter;
import com.freedom.lauzy.gankpro.view.DailyView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.freedom.lauzy.gankpro.function.ValueContants.ImageValue.IMAGE_URL;
import static com.freedom.lauzy.gankpro.function.ValueContants.ImageValue.PUBLISH_DATE;

public class DailyActivity extends BaseActivity {


    private static final String TAG = DailyActivity.class.getSimpleName();
    @BindView(R.id.img_title)
    ImageView mImgTitle;
    @BindView(R.id.toolbar)
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
    private List<ItemBean> mItemBeen = new ArrayList<>();
    private DailyAdapter mAdapter;
    private DailyPresenter mDailyPresenter;

    @Override
    protected void loadData() {
        String imgUrl = getIntent().getStringExtra(IMAGE_URL);
//        Bitmap img_data = getIntent().getParcelableExtra("img_data");
        /*CollectionEntityDao entityDao = GankApp.getGankApp().getDaoSession().getCollectionEntityDao();
        CollectionEntity entity = entityDao.queryBuilder().where(CollectionEntityDao.Properties.Id.eq(img_id)).build().list().get(0);*/
        ImageLoader.loadImage(this, imgUrl, mImgTitle);
        if (mPublishDate == null) {
            Log.e(TAG, "loadData: date is null");
        }
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
            public void refreshError(Throwable e) {
                mSrlDaily.setRefreshing(false);
            }
        }, mPublishDate);
        mDailyPresenter.initData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initViews() {
        mPublishDate = (Date) getIntent().getSerializableExtra(PUBLISH_DATE);
        mToolbarLayout.setTitle(DateUtils.toDate(mPublishDate));
        ViewCompat.setTransitionName(mImgTitle, "transitionImg");
        mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
        setSupportActionBar(mToolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDaily.setLayoutManager(linearLayoutManager);

        mSrlDaily.setColorSchemeResources(R.color.color_style_gray);
        mSrlDaily.setRefreshing(true);
        mAdapter = new DailyAdapter(mItemBeen);
        mRvDaily.setAdapter(mAdapter);

        mSrlDaily.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrlDaily.setRefreshing(false);
                mDailyPresenter.refreshData();
            }
        });
        mRvDaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0){
                    mFabBeauty.show();
                }else {
                    mFabBeauty.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
//                        mFabBeauty.show();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        mFabBeauty.hide();
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        mFabBeauty.show();
                        break;
                }
            }
        });
    }

    @OnClick(R.id.fab_beauty)
    public void onClick() {
        Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show();
    }
}
