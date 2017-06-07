package com.freedom.lauzy.gankpro.presenter;

import android.util.Log;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.constants.LoadData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.OnResponse;
import com.freedom.lauzy.gankpro.model.BeautyModel;
import com.freedom.lauzy.gankpro.view.BeautyView;

import java.util.List;

import static com.freedom.lauzy.gankpro.function.constants.LoadData.INIT_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.constants.LoadData.LOAD_MORE_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.constants.LoadData.REFRESH_DATA_TYPE;

/**
 * Created by Lauzy on 2017/1/20.
 */

public class BeautyPresenter extends BasePresenter<BeautyView> {

    private static final String LYTAG = BeautyPresenter.class.getSimpleName();
    private BeautyModel mBeautyModel;
    private LoadData GET_DATA_TYPE;
    private OnResponse<GankData> mGankDataResponse;
    private int page = 1;

    public BeautyPresenter(BeautyView beautyView) {
        attachView(beautyView);
        mBeautyModel = new BeautyModel();
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
                    getMvpBaseView().loadMoreRvData(gankDataResults);
                }
                if (gankDataResults == null || gankDataResults.isEmpty() || gankDataResults.size() == 0) {
                    Log.i(LYTAG, "There is no beauty data");//可以设置添加View接口方法，以便更简话Activity，此处只设置了初始化，刷新，加载更多等。
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (GET_DATA_TYPE == INIT_DATA_TYPE) {
                    getMvpBaseView().initError(e);
                } else if (GET_DATA_TYPE == REFRESH_DATA_TYPE) {
                    getMvpBaseView().refreshError(e);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA_TYPE) {
                    getMvpBaseView().loadMoreError(e);
                }
            }
        };
        mBeautyModel.getBeautyDataFromNet("福利", 1, mGankDataResponse);
    }

    public void refreshData() {
        page = 1;
        GET_DATA_TYPE = REFRESH_DATA_TYPE;
        mBeautyModel.getBeautyDataFromNet("福利", 1, mGankDataResponse);
    }

    public void loadMoreData() {
        GET_DATA_TYPE = LOAD_MORE_DATA_TYPE;
        mBeautyModel.getBeautyDataFromNet("福利", ++page, mGankDataResponse);
    }
}
