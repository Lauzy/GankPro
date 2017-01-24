package com.freedom.lauzy.gankpro.ui.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.presenter.BeautyPresenter;
import com.freedom.lauzy.gankpro.ui.activity.DailyActivity;
import com.freedom.lauzy.gankpro.ui.adapter.BeautyAdapter;
import com.freedom.lauzy.gankpro.view.BeautyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BeautyFragment extends BaseFragment {


    private static final String TAG = BeautyFragment.class.getSimpleName();

    @BindView(R.id.beauty_recycler_view)
    RecyclerView mBeautyRecyclerView;
    @BindView(R.id.beauty_refresh_layout)
    SwipeRefreshLayout mBeautyRefreshLayout;
    private BeautyPresenter mBeautyPresenter;
    private List<GankData.ResultsBean> mResultsBeen = new ArrayList<>();
    private BeautyAdapter mAdapter;
    private View mEmptyView;

    @Override
    protected void initViews() {
        initEmptyView();
       /* final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setItemPrefetchEnabled(false);*/

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 2);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBeautyRecyclerView.setLayoutManager(gridLayoutManager);
        mBeautyRefreshLayout.setColorSchemeResources(R.color.color_style_gray);
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
                mBeautyRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mBeautyRefreshLayout.setEnabled(false);
                mBeautyPresenter.loadMoreData();
            }
        });

        mBeautyRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(mActivity,DailyActivity.class));
            }
        });
    }

    private void initEmptyView() {
        mEmptyView = View.inflate(mActivity, R.layout.layout_empty_view, null);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void loadData() {

        mBeautyPresenter = new BeautyPresenter(new BeautyView() {
            @Override
            public void initRvData(List<GankData.ResultsBean> data) {
                Log.i(TAG, "initRvData: " + data.size());
                if (data.size() == 0) {
                    mAdapter.setEmptyView(mEmptyView);
                } else {
                    mResultsBeen.addAll(data);
                    mAdapter.notifyDataSetChanged();
                }
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {
                if (refreshData == null) {
                    mBeautyRefreshLayout.setRefreshing(false);
                } else {
                    mAdapter.setNewData(refreshData);
                    mBeautyRefreshLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);
                }
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
            public void refreshError(Throwable e) {
                mBeautyRefreshLayout.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
                mBeautyRefreshLayout.setEnabled(true);
                mAdapter.loadMoreFail();
            }
        });
        mBeautyPresenter.initData();
    }
}
