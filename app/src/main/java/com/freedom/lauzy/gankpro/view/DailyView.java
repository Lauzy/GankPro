package com.freedom.lauzy.gankpro.view;

import com.freedom.lauzy.gankpro.common.base.BaseView;
import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;

import java.util.List;

/**
 * daily
 * Created by Lauzy on 2017/1/23.
 */

public interface DailyView extends BaseView {
    void initRvData(List<ItemBean> data);
    void refreshRvData(List<ItemBean> refreshData);
    void initError(Throwable e);
    void refreshError(Throwable e);
}
