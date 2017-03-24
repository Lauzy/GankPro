package com.freedom.lauzy.gankpro.presenter;

import com.freedom.lauzy.gankpro.common.base.BasePresenter;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;
import com.freedom.lauzy.gankpro.model.CollectionModel;
import com.freedom.lauzy.gankpro.view.CollectionView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lauzy on 2017/3/24.
 */

public class CollectionPresenter extends BasePresenter<CollectionView> implements CollectionPresenterImpl {

    private CollectionModel mCollectionModel;

    public CollectionPresenter(CollectionView collectionView) {
        attachView(collectionView);
        mCollectionModel = new CollectionModel();
    }

    @Override
    public void initData() {
        mCollectionModel.getCollectionData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CollectionEntity>>() {
                    @Override
                    public void call(List<CollectionEntity> collectionEntities) {
                        getMvpBaseView().initRvData(collectionEntities);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        getMvpBaseView().initError(throwable);
                    }
                });
    }

    @Override
    public void refreshData() {
        mCollectionModel.getCollectionData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CollectionEntity>>() {
                    @Override
                    public void call(List<CollectionEntity> collectionEntities) {
                        getMvpBaseView().refreshRvData(collectionEntities);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        getMvpBaseView().refreshError(throwable);
                    }
                });
    }
}
