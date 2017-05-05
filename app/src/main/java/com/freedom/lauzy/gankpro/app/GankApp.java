package com.freedom.lauzy.gankpro.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatDelegate;

import com.freedom.lauzy.gankpro.function.db.LyDaoHelper;
import com.freedom.lauzy.gankpro.function.greendao.DaoMaster;
import com.freedom.lauzy.gankpro.function.greendao.DaoSession;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;
import com.freedom.lauzy.gankpro.function.utils.SharePrefUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * App
 * Created by Lauzy on 2017/1/18.
 */
@SuppressWarnings("unused")
public class GankApp extends Application {

    private static final String SHARED_PREF_NAME = "gank_config_share_pref";
    private static GankApp sContext;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;
    public static SharedPreferences sConfigPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        ApiFactory.getInstance().init(this);
        initDataBase();
        initPref();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        AppCompatDelegate.setDefaultNightMode(SharePrefUtils.isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void initPref() {
        sConfigPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    private void initDataBase() {
        LyDaoHelper helper = new LyDaoHelper(this, "gank-db", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public static GankApp getInstance() {
        return sContext;
    }
}
