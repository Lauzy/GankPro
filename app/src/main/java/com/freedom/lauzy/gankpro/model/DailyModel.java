package com.freedom.lauzy.gankpro.model;

import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;
import com.freedom.lauzy.gankpro.function.net.LySubscriber;
import com.freedom.lauzy.gankpro.function.net.OnResponse;

/**
 * Created by Lauzy on 2017/1/23.
 */

public class DailyModel {

    public void getDailyDataFromNet(final OnResponse<DailyData> response, int year, int month, int day) {
        ApiFactory.getInstance().getDailyData(new LySubscriber<DailyData>() {
            @Override
            public void onNext(DailyData o) {
                if (o == null) {
                    response.onError(new Throwable("data is null"));
                } else {
                    response.onSuccess(o);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                response.onError(e);
            }
        }, year, month, day);
    }
}
