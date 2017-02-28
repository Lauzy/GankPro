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
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        LyDaoHelper helper = new LyDaoHelper(this, "gank-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
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
