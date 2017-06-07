package com.freedom.lauzy.gankpro.model;

import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;
import com.freedom.lauzy.gankpro.function.net.LySubscriber;
import com.freedom.lauzy.gankpro.function.net.OnResponse;

/**
 * Created by Lauzy on 2017/1/20.
 */

public class BeautyModel {

    public void getBeautyDataFromNet(String type, int page, final OnResponse<GankData> response) {
        ApiFactory.getInstance().getGankData(new LySubscriber<GankData>() {
            @Override
            public void onNext(GankData o) {
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
        }, type, page);
    }
}
