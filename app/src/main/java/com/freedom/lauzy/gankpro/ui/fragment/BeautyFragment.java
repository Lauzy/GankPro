package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.LoadData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.presenter.BeautyPresenter;
import com.freedom.lauzy.gankpro.view.BeautyView;

import java.util.List;

import butterknife.BindView;

public class BeautyFragment extends BaseFragment {


    private static final String TAG = BeautyFragment.class.getSimpleName();

    @BindView(R.id.beauty_recycler_view)
    RecyclerView mBeautyRecyclerView;
    private BeautyPresenter mBeautyPresenter;

    @Override
    protected void initViews() {
        mBeautyRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
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
            }

            @Override
            public void refreshRvData(List<GankData.ResultsBean> refreshData) {

            }

            @Override
            public void loadMoreRvData(List<GankData.ResultsBean> loadMoreData) {

            }

            @Override
            public void refreshError(Throwable e) {

            }

            @Override
            public void loadMoreError(Throwable e) {

            }
        });
        mBeautyPresenter.loadData(1, LoadData.INIT_DATA);
    }
}
