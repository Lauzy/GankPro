package com.freedom.lauzy.gankpro.app;

import android.app.Application;
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

    private static GankApp sContext;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.getInstance().init(this);
        initDataBase();
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
