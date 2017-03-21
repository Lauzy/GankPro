package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.view.AndroidItemDecoration;
import com.freedom.lauzy.gankpro.presenter.CategoryGankPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.AndroidAdapter;
import com.freedom.lauzy.gankpro.view.CategoryGankView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AndroidFragment extends BaseFragment {

    private static final String LYTAG = AndroidFragment.class.getSimpleName();
    @BindView(R.id.rv_android)
    RecyclerView mRvAndroid;
    @BindView(R.id.android_refresh_layout)
    SwipeRefreshLayout mAndroidRefreshLayout;
    @BindView(R.id.empty_view)
    View mEmptyView;
    private CategoryGankPresenter mGankPresenter;
    private List<GankData.ResultsBean> mResultsBeen = new ArrayList<>();
    private AndroidAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
        mRvAndroid.setNestedScrollingEnabled(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvAndroid.setLayoutManager(linearLayoutManager);
//        mAdapter = new AndroidAdapter(mActivity, mResultsBeen, R.layout.layout_android_item);
        mAdapter = new AndroidAdapter(R.layout.layout_android_item, mResultsBeen);
        mRvAndroid.setAdapter(mAdapter);

        mAndroidRefreshLayout.setProgressViewOffset(true, 120, 240);
        mAndroidRefreshLayout.setColorSchemeResources(R.color.color_style_gray);
        mAndroidRefreshLayout.setRefreshing(true);
        mAndroidRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGankPresenter.refreshData();
                mAndroidRefreshLayout.setRefreshing(false);
            }
        });

        /*mRvAndroid.setLoadMore(0, new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mGankPresenter.loadMoreData();
            }

        });*/

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mGankPresenter.loadMoreData();
                mAndroidRefreshLayout.setEnabled(false);
            }
        });

       /* mRvAndroid.addOnItemTouchListener(new RvItemTouchListener(mActivity, mRvAndroid, new RvItemClickListener() {
            @Override
            public void rvItemClick(int position) {
                Log.e(LYTAG, "rvItemClick: " + position);
            }

            @Override
            public void rvItemLongClick(int position) {

            }
        }));*/
        mRvAndroid.addItemDecoration(new AndroidItemDecoration(mActivity));
    }

    @Override
    protected void loadData() {
        initPresenter();
        mGankPresenter.initData();
    }

    private void initPresenter() {
        mGankPresenter = new CategoryGankPresenter(new CategoryGankView() {
            @Override
            public void initRvData(List<GankData.ResultsBean> data) {
                if (data != null) {
                    mResultsBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
                    mRvAndroid.setNestedScrollingEnabled(true);
                } else {
                    mAdapter.setEmptyView(mEmptyView);
//                    mRvAndroid.addEmptyView(mEmptyView);
                }
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {
                if (refreshData != null) {
                    /*mAdapter.removeAllData();
                    mAdapter.addData(refreshData);
                    mRvAndroid.enableLoadMore();*/
                    mAdapter.setNewData(refreshData);
                    mAdapter.setEnableLoadMore(true);
                }
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {
                if (loadMoreData == null) {
                    mAdapter.loadMoreEnd();
//                    mRvAndroid.loadMoreFinish();
                } else {
//                    mAdapter.addData(loadMoreData);
                    mAdapter.addData(loadMoreData);
                    mAdapter.loadMoreComplete();
                }
                mAndroidRefreshLayout.setEnabled(true);
            }

            @Override
            public void initError(Throwable e) {
//                mRvAndroid.addEmptyView(mEmptyView);
//                mAdapter.setEmptyView(mEmptyView);
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
//                mRvAndroid.loadMoreError();
                mAdapter.loadMoreFail();
                mAndroidRefreshLayout.setEnabled(true);
            }
        }, "Android");
    }
}
