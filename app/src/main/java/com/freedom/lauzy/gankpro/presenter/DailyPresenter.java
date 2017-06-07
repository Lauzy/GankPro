package com.freedom.lauzy.gankpro.presenter;

import android.util.Log;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.constants.LoadData;
import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;
import com.freedom.lauzy.gankpro.function.net.OnResponse;
import com.freedom.lauzy.gankpro.function.utils.DateUtils;
import com.freedom.lauzy.gankpro.model.DailyModel;
import com.freedom.lauzy.gankpro.view.DailyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * daily
 * Created by Lauzy on 2017/1/23.
 */
@SuppressWarnings("unchecked")
public class DailyPresenter extends BasePresenter<DailyView> {
    private final Calendar mCalendar;
    private static final String TAG = DailyPresenter.class.getSimpleName();
    private DailyModel mDailyModel;
    private LoadData GET_DATA_TYPE;
    private List<ItemBean> mDailyBeen = new ArrayList<>();
    private OnResponse<DailyData> mDailyDataOnResponse;

    public DailyPresenter(DailyView dailyView, Date pubTime) {
        attachView(dailyView);
        mDailyModel = new DailyModel();
        mCalendar = DateUtils.toCalender(pubTime);
    }

    public void initData() {
        GET_DATA_TYPE = LoadData.INIT_DATA_TYPE;
        mDailyDataOnResponse = new OnResponse<DailyData>() {
            @Override
            public void onSuccess(DailyData dailyData) {
                DailyData.ResultsBean results = dailyData.getResults();
                List<ItemBean> androidList = results.getAndroidList();
                List<ItemBean> iOSList = results.getiOSList();
                List<ItemBean> appList = results.getAppList();
                List<ItemBean> restVideoList = results.getRestVideoList();
                List<ItemBean> recommendList = results.getRecommendList();
                List<ItemBean> expandList = results.getExpandList();
                List<ItemBean> frontList = results.getFrontList();
                if (GET_DATA_TYPE == LoadData.INIT_DATA_TYPE) {
                    addData(androidList, iOSList, appList, restVideoList, recommendList, expandList, frontList);
                    getMvpBaseView().initRvData(mDailyBeen);
                } else if (GET_DATA_TYPE == LoadData.REFRESH_DATA_TYPE) {
                    mDailyBeen.clear();
                    addData(androidList, iOSList, appList, restVideoList, recommendList, expandList, frontList);
                    getMvpBaseView().refreshRvData(mDailyBeen);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (GET_DATA_TYPE == LoadData.INIT_DATA_TYPE) {
                    getMvpBaseView().initError(e);
                } else if (GET_DATA_TYPE == LoadData.REFRESH_DATA_TYPE) {
                    getMvpBaseView().refreshError(e);
                }
            }
        };
        Log.i(TAG, "initData: 初始化：年：" + mCalendar.get(Calendar.YEAR) + " 月：" +
                (mCalendar.get(Calendar.MONTH) + 1) + " 日:" + mCalendar.get(Calendar.DAY_OF_MONTH));
        mDailyModel.getDailyDataFromNet(mDailyDataOnResponse, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void addData(List<ItemBean>... itemBeen) {
        for (List<ItemBean> been : itemBeen) {
            if (been != null) {
                mDailyBeen.addAll(been);
            }
        }
    }

    public void refreshData() {
        GET_DATA_TYPE = LoadData.REFRESH_DATA_TYPE;
        mDailyModel.getDailyDataFromNet(mDailyDataOnResponse, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
