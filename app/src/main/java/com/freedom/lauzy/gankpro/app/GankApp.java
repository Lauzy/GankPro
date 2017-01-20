package com.freedom.lauzy.gankpro.app;

import android.app.Application;

import com.freedom.lauzy.gankpro.function.net.ApiFactory;

/**
 * App
 * Created by Lauzy on 2017/1/18.
 */

public class GankApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.getInstance().init(this);
    }
}
