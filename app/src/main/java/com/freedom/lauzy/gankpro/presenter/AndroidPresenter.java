package com.freedom.lauzy.gankpro.presenter;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.LoadData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;
import com.freedom.lauzy.gankpro.model.AndroidModel;
import com.freedom.lauzy.gankpro.view.AndroidView;

import java.util.List;

import static com.freedom.lauzy.gankpro.function.LoadData.INIT_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.LoadData.LOAD_MORE_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.LoadData.REFRESH_DATA_TYPE;

/**
 * Created by Lauzy on 2017/2/4.
 */

public class AndroidPresenter extends BasePresenter<AndroidView> {
    private AndroidModel mAndroidModel;
    private LoadData GET_DATA_TYPE;
    private OnResponse<GankData> mGankDataResponse;
    private int page = 2;

    public AndroidPresenter(AndroidView androidView) {
        attachView(androidView);
        mAndroidModel = new AndroidModel();
    }

    public void initData() {
        GET_DATA_TYPE = INIT_DATA_TYPE;
        mGankDataResponse = new OnResponse<GankData>() {
            @Override
            public void onSuccess(GankData gankData) {
                List<GankData.ResultsBean> gankDataResults = gankData.getResults();
                if (GET_DATA_TYPE == INIT_DATA_TYPE) {
                    getMvpBaseView().initRvData(gankDataResults);
                } else if (GET_DATA_TYPE == REFRESH_DATA_TYPE) {
                    getMvpBaseView().refreshRvData(gankDataResults);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA_TYPE) {
                    page++;
                    getMvpBaseView().loadMoreRvData(gankDataResults);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (GET_DATA_TYPE == INIT_DATA_TYPE) {
                } else if (GET_DATA_TYPE == REFRESH_DATA_TYPE) {
                    getMvpBaseView().refreshError(e);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA_TYPE) {
                    getMvpBaseView().loadMoreError(e);
                }
            }
        };
        mAndroidModel.getAndroidDataFromNet("Android", 1, mGankDataResponse);
    }

    public void refreshData() {
        page = 1;
        GET_DATA_TYPE = REFRESH_DATA_TYPE;
        mAndroidModel.getAndroidDataFromNet("Android", 1, mGankDataResponse);
    }

    public void loadMoreData() {
        GET_DATA_TYPE = LOAD_MORE_DATA_TYPE;
        mAndroidModel.getAndroidDataFromNet("Android", page, mGankDataResponse);
    }
}
