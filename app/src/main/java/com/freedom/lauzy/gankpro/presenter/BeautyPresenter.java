package com.freedom.lauzy.gankpro.presenter;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.LoadData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;
import com.freedom.lauzy.gankpro.model.BeautyModel;
import com.freedom.lauzy.gankpro.view.BeautyView;

import java.util.List;

import static com.freedom.lauzy.gankpro.function.LoadData.INIT_DATA;
import static com.freedom.lauzy.gankpro.function.LoadData.LOAD_MORE_DATA;
import static com.freedom.lauzy.gankpro.function.LoadData.REFRESH_DATA;

/**
 * Created by Lauzy on 2017/1/20.
 */

public class BeautyPresenter extends BasePresenter<BeautyView> {

    private BeautyModel mBeautyModel;

    public BeautyPresenter(BeautyView beautyView) {
        attachView(beautyView);
        mBeautyModel = new BeautyModel();
    }

    public void loadData(int page, final LoadData GET_DATA_TYPE) {
        mBeautyModel.getBeautyDataFromNet("福利", page, new OnResponse<GankData>() {
            @Override
            public void onSuccess(GankData gankData) {
                List<GankData.ResultsBean> gankDataResults = gankData.getResults();
                if (GET_DATA_TYPE == INIT_DATA) {
                    getMvpBaseView().initRvData(gankDataResults);
                } else if (GET_DATA_TYPE == REFRESH_DATA) {
                    getMvpBaseView().refreshRvData(gankDataResults);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA) {
                    getMvpBaseView().loadMoreRvData(gankDataResults);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (GET_DATA_TYPE == INIT_DATA) {
                } else if (GET_DATA_TYPE == REFRESH_DATA) {
                    getMvpBaseView().refreshError(e);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA) {
                    getMvpBaseView().loadMoreError(e);
                }
            }
        });
    }
}
