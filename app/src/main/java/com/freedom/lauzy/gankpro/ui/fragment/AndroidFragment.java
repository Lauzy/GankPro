package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.EmptyWrapper;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.LoadMoreWrapper;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.presenter.CategoryGankPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.AndroidAdapter;
import com.freedom.lauzy.gankpro.view.CategoryGankView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AndroidFragment extends BaseFragment {

    @BindView(R.id.rv_android)
    RecyclerView mRvAndroid;
    @BindView(R.id.android_refresh_layout)
    SwipeRefreshLayout mAndroidRefreshLayout;
    private CategoryGankPresenter mGankPresenter;
    private List<GankData.ResultsBean> mResultsBeen = new ArrayList<>();
    private AndroidAdapter mAdapter;
    private EmptyWrapper mEmptyWrapper;
    private LinearLayoutManager mLinearLayoutManager;
    private int mLastVisibleItemPosition;

    @Override
    protected void initViews() {
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvAndroid.setLayoutManager(mLinearLayoutManager);
        mAdapter = new AndroidAdapter(mActivity, mResultsBeen, R.layout.layout_android_item);
        mRvAndroid.setAdapter(mAdapter);

        initEmptyView();

        mAndroidRefreshLayout.setColorSchemeResources(R.color.color_style_gray);
        mAndroidRefreshLayout.setRefreshing(true);
        mAndroidRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGankPresenter.refreshData();
                mAndroidRefreshLayout.setRefreshing(false);
            }
        });

        mRvAndroid.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    mGankPresenter.loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(R.layout.layout_empty_view);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_android;
    }

    @Override
    protected void loadData() {
        mGankPresenter = new CategoryGankPresenter(new CategoryGankView() {
            @Override
            public void initRvData(List<GankData.ResultsBean> data) {
                if (data != null) {
                    mResultsBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mRvAndroid.setAdapter(mEmptyWrapper);
                }
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {
                if (refreshData == null) {
                    mAndroidRefreshLayout.setRefreshing(false);
                } else {
                    mAdapter.removeAllData();
                    mAdapter.addData(refreshData);
                    mAndroidRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {
                if (loadMoreData == null || loadMoreData.size() == 0) {
                } else {
                    mAdapter.addData(loadMoreData);
                }
                mAndroidRefreshLayout.setEnabled(true);
            }

            @Override
            public void initError(Throwable e) {
                mRvAndroid.setAdapter(mEmptyWrapper);
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
                mAndroidRefreshLayout.setEnabled(true);
            }
        }, "Android");
        mGankPresenter.initData();
    }
}
