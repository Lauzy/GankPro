package com.freedom.lauzy.gankpro.view;

import com.freedom.lauzy.gankpro.common.base.BaseView;
import com.freedom.lauzy.gankpro.function.entity.GankData;

import java.util.List;

/**
 * 分类View
 * Created by Lauzy on 2017/2/4.
 */

public interface CategoryGankView extends BaseView {
    void initRvData(List<GankData.ResultsBean> data);
    void refreshRvData(List<GankData.ResultsBean> refreshData);
    void loadMoreRvData(List<GankData.ResultsBean> loadMoreData);
    void initError(Throwable e);
    void refreshError(Throwable e);
    void loadMoreError(Throwable e);
}
