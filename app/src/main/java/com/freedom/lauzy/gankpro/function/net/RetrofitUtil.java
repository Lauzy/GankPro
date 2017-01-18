package com.freedom.lauzy.gankpro.function.net;

import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;

import rx.Subscriber;

/**
 * retrofit工具类
 * Created by Lauzy on 2017/1/18.
 */

public class RetrofitUtil {


    public static void loadGankData(String type, int page, final OnResponse<GankData> response) {
        /*sApiFactory.setUseCache(true);
        sApiFactory.setMaxCacheTime(24 * 60);*/
        ApiFactory.getInstance().getGankData(new Subscriber<GankData>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                response.onError(e);
            }

            @Override
            public void onNext(GankData gankData) {
                response.onSuccess(gankData);
            }
        }, type, page);
    }
}
