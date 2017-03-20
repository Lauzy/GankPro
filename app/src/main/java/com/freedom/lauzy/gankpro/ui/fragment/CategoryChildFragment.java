package com.freedom.lauzy.gankpro.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.presenter.CategoryGankPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.AndroidAdapter;
import com.freedom.lauzy.gankpro.view.CategoryGankView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryChildFragment extends BaseFragment {


    private static final String CATEGORY_TYPE = "category";
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.srl_category)
    SwipeRefreshLayout mSrlCategory;
    @BindView(R.id.empty_view)
    View mEmptyView;
    private String mType;
    private CategoryGankPresenter mGankPresenter;
    private AndroidAdapter mAdapter;
    private List<GankData.ResultsBean> mResultsBeen = new ArrayList<>();

    /*private GankBottomBehavior mBottomBehavior;
    private static final String BEHAVIOR = "behavior";*/

    public static CategoryChildFragment newInstance(String type) {
        CategoryChildFragment fragment = new CategoryChildFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_TYPE, type);
//        args.putSerializable(BEHAVIOR, bottomBehavior);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(CATEGORY_TYPE);
//            mBottomBehavior = (GankBottomBehavior) getArguments().getSerializable(BEHAVIOR);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category_child;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvCategory.setLayoutManager(linearLayoutManager);

        mAdapter = new AndroidAdapter(R.layout.layout_android_item, mResultsBeen);
        mRvCategory.setAdapter(mAdapter);

//        mSrlCategory.setProgressViewOffset(true, 120, 240);
        mSrlCategory.setColorSchemeResources(R.color.color_style_gray);
        mSrlCategory.setRefreshing(true);
        mSrlCategory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGankPresenter.refreshData();
                mSrlCategory.setRefreshing(false);
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mGankPresenter.loadMoreData();
                mSrlCategory.setEnabled(false);
            }
        });

        /*mRvCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mBottomBehavior != null){
                    if (dy <= 0) {
                        mBottomBehavior.show();
                    } else {
                        mBottomBehavior.hide();
                    }
                }
            }
        });*/
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
                } else {
                    mAdapter.setEmptyView(mEmptyView);
//                    mRvAndroid.addEmptyView(mEmptyView);
                }
                mSrlCategory.setRefreshing(false);
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
                mSrlCategory.setRefreshing(false);
            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {
                if (loadMoreData == null || loadMoreData.size() == 0) {
                    mAdapter.loadMoreEnd();
//                    mRvAndroid.loadMoreFinish();
                } else {
//                    mAdapter.addData(loadMoreData);
                    mAdapter.addData(loadMoreData);
                    mAdapter.loadMoreComplete();
                }
                mSrlCategory.setEnabled(true);
            }

            @Override
            public void initError(Throwable e) {
//                mRvAndroid.addEmptyView(mEmptyView);
//                mAdapter.setEmptyView(mEmptyView);
                mSrlCategory.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                mSrlCategory.setRefreshing(false);
            }

            @Override
            public void loadMoreError(Throwable e) {
//                mRvAndroid.loadMoreError();
                mAdapter.loadMoreFail();
                mSrlCategory.setEnabled(true);
            }
        }, mType);
    }
}
