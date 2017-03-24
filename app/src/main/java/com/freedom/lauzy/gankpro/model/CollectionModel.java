package com.freedom.lauzy.gankpro.model;

import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;
import com.freedom.lauzy.gankpro.function.greendao.CollectionEntityDao;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Lauzy on 2017/3/23.
 */

public class CollectionModel {
    /*public interface CollectionListener{
        void onSuccess(List<CollectionEntity> collectionEntities);
        void onError(Throwable e);
    }*/

    public Observable<List<CollectionEntity>> getCollectionData(){
        return Observable.create(new Observable.OnSubscribe<List<CollectionEntity>>() {
            @Override
            public void call(Subscriber<? super List<CollectionEntity>> subscriber) {
                CollectionEntityDao entityDao = GankApp.getInstance().getDaoSession().getCollectionEntityDao();
                List<CollectionEntity> collectionEntities = entityDao.loadAll();
                subscriber.onNext(collectionEntities);
                subscriber.onCompleted();
            }
        });
    }
}
