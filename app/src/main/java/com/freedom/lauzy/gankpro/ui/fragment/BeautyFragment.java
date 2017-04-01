package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.view.BeautyItemDecoration;
import com.freedom.lauzy.gankpro.presenter.BeautyPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.BeautyAdapter;
import com.freedom.lauzy.gankpro.view.BeautyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BeautyFragment extends BaseFragment {

    private static final String LYTAG = BeautyFragment.class.getSimpleName();

    @BindView(R.id.beauty_recycler_view)
    RecyclerView mBeautyRecyclerView;
    @BindView(R.id.beauty_refresh_layout)
    SwipeRefreshLayout mBeautyRefreshLayout;
    private BeautyPresenter mBeautyPresenter;
    private List<GankData.ResultsBean> mResultsBeen = new ArrayList<>();
    private BeautyAdapter mAdapter;
    private View mEmptyView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void initViews() {
        initEmptyView();
       /* StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setAutoMeasureEnabled(true);
        staggeredGridLayoutManager.setItemPrefetchEnabled(false);*/
//        mBeautyRecyclerView.setHasFixedSize(true);
        initRecyclerView();
//        mBehaviorListener.setBehaviorCanScroll(false);
        mBeautyRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        /*LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);*/
        mBeautyRecyclerView.setLayoutManager(gridLayoutManager);

        mBeautyRefreshLayout.setProgressViewOffset(true, 120, 240);
        mBeautyRefreshLayout.setColorSchemeResources(R.color.color_srl_gray);
        mBeautyRefreshLayout.setRefreshing(true);
        mAdapter = new BeautyAdapter(R.layout.layout_beauty_item, mResultsBeen);
//        mAdapter = new BeautyAdapter(mResultsBeen);
        mBeautyRecyclerView.setAdapter(mAdapter);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (position % 3 == 0) {
                    return 2;
                }
                return 1;
            }
        });

        mBeautyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBeautyPresenter.refreshData();
                mAdapter.setEnableLoadMore(true);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mBeautyRefreshLayout.setEnabled(false);
                mBeautyPresenter.loadMoreData();
            }
        }, mBeautyRecyclerView);

        mBeautyRecyclerView.addItemDecoration(new BeautyItemDecoration(mActivity));
    }

    private void initEmptyView() {
        mEmptyView = View.inflate(mActivity, R.layout.layout_empty_view, null);
    }

    @Override
    protected void loadData() {
        initPresenter();
        mBeautyPresenter.initData();
    }

    private void initPresenter() {
        mBeautyPresenter = new BeautyPresenter(new BeautyView() {
            @Override
            public void initRvData(List<GankData.ResultsBean> data) {
                if (data == null || data.size() == 0) {
                    mBeautyRecyclerView.setNestedScrollingEnabled(false);
                    mAdapter.setEmptyView(mEmptyView);
                } else {
                    mResultsBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
//                    mBehaviorListener.setBehaviorCanScroll(true);
                    mBeautyRecyclerView.setNestedScrollingEnabled(true);
                }
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {
                if (refreshData != null) {
                    mBeautyRecyclerView.setNestedScrollingEnabled(true);
                    mAdapter.setNewData(refreshData);
                    mAdapter.setEnableLoadMore(true);
                }
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {
                if (loadMoreData == null || loadMoreData.size() == 0) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.addData(loadMoreData);
                    mAdapter.loadMoreComplete();
                }
                mBeautyRefreshLayout.setEnabled(true);
            }

            @Override
            public void initError(Throwable e) {
                mAdapter.setEmptyView(mEmptyView);
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
                mBeautyRefreshLayout.setEnabled(true);
                mAdapter.loadMoreFail();
            }
        });
    }
}
