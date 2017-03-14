package com.freedom.lauzy.gankpro.presenter;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.LoadData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;
import com.freedom.lauzy.gankpro.model.CategoryGankModel;
import com.freedom.lauzy.gankpro.view.CategoryGankView;

import java.util.List;

import static com.freedom.lauzy.gankpro.function.LoadData.INIT_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.LoadData.LOAD_MORE_DATA_TYPE;
import static com.freedom.lauzy.gankpro.function.LoadData.REFRESH_DATA_TYPE;

/**
 * 分类Presenter
 * Created by Lauzy on 2017/2/4.
 */

public class CategoryGankPresenter extends BasePresenter<CategoryGankView> {
    private static final String LYTAG = CategoryGankPresenter.class.getSimpleName();
    private CategoryGankModel mGankModel;
    private LoadData GET_DATA_TYPE;
    private OnResponse<GankData> mGankDataResponse;
    private int page = 2;
    private String mType;

    public CategoryGankPresenter(CategoryGankView gankView, String type) {
        attachView(gankView);
        mType = type;
        mGankModel = new CategoryGankModel();
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
                    page++;
                }
            }

            @Override
            public void onError(Throwable e) {
                if (GET_DATA_TYPE == INIT_DATA_TYPE) {
                    getMvpBaseView().initError(e);
                } else if (GET_DATA_TYPE == REFRESH_DATA_TYPE) {
                    getMvpBaseView().refreshError(e);
                } else if (GET_DATA_TYPE == LOAD_MORE_DATA_TYPE) {
                    getMvpBaseView().loadMoreError(e);
                }
            }
        };
        mGankModel.getCategoryGankData(mType, 1, mGankDataResponse);
    }

    public void refreshData() {
        page = 2;
        GET_DATA_TYPE = REFRESH_DATA_TYPE;
        mGankModel.getCategoryGankData(mType, 1, mGankDataResponse);
    }

    public void loadMoreData() {
        GET_DATA_TYPE = LOAD_MORE_DATA_TYPE;
        mGankModel.getCategoryGankData(mType, page, mGankDataResponse);
    }

}
