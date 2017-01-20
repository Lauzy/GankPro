package com.freedom.lauzy.gankpro.view;

import com.freedom.lauzy.gankpro.common.base.BaseView;
import com.freedom.lauzy.gankpro.function.entity.GankData;

/**
 * Created by Lauzy on 2017/1/20.
 */

public interface BeautyView extends BaseView {
    void initRvData(GankData data);
    void refreshRvData(GankData refreshData);
    void loadMoreRvData(GankData loadMoreData);
    void refreshError(Throwable e);
    void loadMoreError(Throwable e);
}
