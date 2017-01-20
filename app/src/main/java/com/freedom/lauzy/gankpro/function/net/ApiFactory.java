package com.freedom.lauzy.gankpro.function.net;

import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.entity.GankData;

import rx.Observable;
import rx.Subscriber;

/**
 * API
 * Created by Lauzy on 2017/1/18.
 */

public class ApiFactory extends RetrofitHttp {

    public static ApiFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ApiFactory INSTANCE = new ApiFactory();
    }

    public void getGankData(LySubscriber<GankData> subscriber, String type, int page) {
        Observable<GankData> observable = apiService.getGankData(type, page);
        toSubscribe(observable, subscriber);
    }

    public void getDailyData(LySubscriber<DailyData> subscriber, int year, int month, int day) {
        Observable<DailyData> observable = apiService.getDailyData(year, month, day);
        toSubscribe(observable, subscriber);
    }
}
