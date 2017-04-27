package com.freedom.lauzy.gankpro.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.freedom.lauzy.gankpro.function.db.LyDaoHelper;
import com.freedom.lauzy.gankpro.function.greendao.DaoMaster;
import com.freedom.lauzy.gankpro.function.greendao.DaoSession;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;
import com.freedom.lauzy.gankpro.function.utils.SharePrefUtils;
import com.lauzy.freedom.tinkerlib.Log.MyLogImp;
import com.lauzy.freedom.tinkerlib.util.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(application = "com.freedom.lauzy.gankpro.app.GankTApp",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)

public class GankWithTinkerApp extends DefaultApplicationLike {

    private static final String SHARED_PREF_NAME = "gank_config_share_pref";
    private static Context sContext;
    private SQLiteDatabase db;
    private DaoSession mDaoSession;
    public static SharedPreferences sConfigPreferences;
    private static GankWithTinkerApp sGankWithTinkerApp;

    public static GankWithTinkerApp getGankWithTinkerApp() {
        return sGankWithTinkerApp;
    }

    public GankWithTinkerApp(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                             long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGankWithTinkerApp = this;
        sContext = getApplication();
        ApiFactory.getInstance().init(sContext);
        initDataBase();
        initPref();
        AppCompatDelegate.setDefaultNightMode(SharePrefUtils.isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void initPref() {
        sConfigPreferences = getApplication().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    private void initDataBase() {
        LyDaoHelper helper = new LyDaoHelper(getApplication(), "gank-db", null);
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

    public static Context getInstance() {
        return sContext;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        sContext = getApplication();
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());
        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

}
