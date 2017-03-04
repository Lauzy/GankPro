package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter.RvItemClickListener;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter.RvItemTouchListener;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.LyLoadMoreRecyclerView;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.OnLoadMoreListener;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.presenter.CategoryGankPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.AndroidAdapter;
import com.freedom.lauzy.gankpro.view.CategoryGankView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AndroidFragment extends BaseFragment {

    private static final String LYTAG = AndroidFragment.class.getSimpleName();
    @BindView(R.id.rv_android)
    LyLoadMoreRecyclerView mRvAndroid;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvAndroid.setLayoutManager(linearLayoutManager);
        mAdapter = new AndroidAdapter(mActivity, mResultsBeen, R.layout.layout_android_item);
        mRvAndroid.setAdapter(mAdapter);


        mAndroidRefreshLayout.setColorSchemeResources(R.color.color_style_gray);
        mAndroidRefreshLayout.setRefreshing(true);
        mAndroidRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGankPresenter.refreshData();
                mAndroidRefreshLayout.setRefreshing(false);
            }
        });

        mRvAndroid.setLoadMore(0, new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mGankPresenter.loadMoreData();
            }

        });

        mRvAndroid.addOnItemTouchListener(new RvItemTouchListener(mActivity, mRvAndroid, new RvItemClickListener() {
            @Override
            public void rvItemClick(int position) {
                Log.e(LYTAG, "rvItemClick: " + position);
            }

            @Override
            public void rvItemLongClick(int position) {

            }
        }));
    }

    @Override
    protected void loadData() {
        mGankPresenter = new CategoryGankPresenter(new CategoryGankView() {
            @Override
            public void initRvData(List<GankData.ResultsBean> data) {
                if (data != null) {
                    Log.e(LYTAG, "initRvData: Android: " + data.size());
                    mResultsBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.e(LYTAG, "initRvData: + empty ");
                    mRvAndroid.addEmptyView(mEmptyView);
                }
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {
                if (refreshData != null) {
                    mAdapter.removeAllData();
                    mAdapter.addData(refreshData, 1);
                    mRvAndroid.enableLoadMore();
                }
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {
                if (mAdapter.getItemCount() == 100) {
                    Log.e(LYTAG, "loadMoreRvData: noData");
                    mRvAndroid.loadMoreFinish();
                } else {
                    mAdapter.addData(loadMoreData, 1);
                }
                mAndroidRefreshLayout.setEnabled(true);
            }

            @Override
            public void initError(Throwable e) {
                Log.e(LYTAG, "initError: ");
                mRvAndroid.addEmptyView(mEmptyView);
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mAndroidRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
                mRvAndroid.loadMoreError();
                mAndroidRefreshLayout.setEnabled(true);
            }
        }, "Android");
        mGankPresenter.initData();
    }
}
