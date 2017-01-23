package com.freedom.lauzy.gankpro.function.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.afa.tourism.greendao.gen.CollectionEntityDao;
import com.afa.tourism.greendao.gen.DaoMaster;

/**
 * Created by Lauzy on 2017/1/23.
 */

public class LyDaoHelper extends DaoMaster.OpenHelper {

    public LyDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //操作数据库的更新
        MigrationHelper.migrate(db, CollectionEntityDao.class);
    }
}
