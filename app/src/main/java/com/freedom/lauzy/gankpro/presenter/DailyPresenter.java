package com.freedom.lauzy.gankpro.presenter;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.LoadData;
import com.freedom.lauzy.gankpro.model.DailyModel;
import com.freedom.lauzy.gankpro.view.DailyView;

/**
 * Created by Lauzy on 2017/1/23.
 */

public class DailyPresenter extends BasePresenter<DailyView> {
    private DailyModel mDailyModel;
    private LoadData GET_DATA_TYPE;
    private String mPublishTime;

    public DailyPresenter(DailyView dailyView,String pubTime){
        attachView(dailyView);
        mDailyModel = new DailyModel();
        mPublishTime = pubTime;
    }

    public void initData(){
        GET_DATA_TYPE = LoadData.INIT_DATA_TYPE;
    }
}
