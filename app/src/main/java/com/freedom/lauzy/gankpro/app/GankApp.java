package com.freedom.lauzy.gankpro.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.freedom.lauzy.gankpro.function.db.LyDaoHelper;
import com.freedom.lauzy.gankpro.function.greendao.DaoMaster;
import com.freedom.lauzy.gankpro.function.greendao.DaoSession;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;

/**
 * App
 * Created by Lauzy on 2017/1/18.
 */
@SuppressWarnings("unused")
public class GankApp extends Application {

    private static Context sContext;
    private static GankApp sGankApp;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.getInstance().init(this);
        initContext();
        initDataBase();
        initApplication();
    }

    private void initApplication() {
        sGankApp = this;
    }

    public static GankApp getGankApp(){
        return sGankApp;
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

    private void initContext() {
        sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sContext;
    }
}
