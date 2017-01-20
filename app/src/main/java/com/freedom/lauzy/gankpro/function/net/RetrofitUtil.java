package com.freedom.lauzy.gankpro.function.net;

import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;

/**
 * retrofit工具类
 * 此类可省略，直接调用ApiFactory中的方法即可。
 * 保留此类主要便于封装onStart onCompleted等方法，例如添加loading动画
 * Created by Lauzy on 2017/1/18.
 */

@Deprecated
public class RetrofitUtil {

    public static void loadGankData(String type, int page, final OnResponse<GankData> response) {
        /*sApiFactory.setUseCache(true);
        sApiFactory.setMaxCacheTime(24 * 60);*/
        ApiFactory.getInstance().getGankData(new LySubscriber<GankData>() {

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

    public static void loadDailyData(int year, int month, int day, final OnResponse<DailyData> response) {
        ApiFactory.getInstance().getDailyData(new LySubscriber<DailyData>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                response.onError(e);
            }

            @Override
            public void onNext(DailyData dailyData) {
                response.onSuccess(dailyData);
            }
        }, year, month, day);
    }
}
