package com.freedom.lauzy.gankpro.view;

import com.freedom.lauzy.gankpro.common.base.BaseView;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;

import java.util.List;

/**
 * Created by Lauzy on 2017/3/24.
 */

public interface CollectionView extends BaseView{
    void initRvData(List<CollectionEntity> data);
    void refreshRvData(List<CollectionEntity> refreshData);
    void initError(Throwable e);
    void refreshError(Throwable e);
}
