package com.freedom.lauzy.gankpro.view;

import com.freedom.lauzy.gankpro.common.base.BaseView;
import com.freedom.lauzy.gankpro.function.entity.DailyData;

/**
 * daily
 * Created by Lauzy on 2017/1/23.
 */

public interface DailyView extends BaseView {
    void initRvData(DailyData data);
    void refreshRvData(DailyData refreshData);
    void refreshError(Throwable e);
}
