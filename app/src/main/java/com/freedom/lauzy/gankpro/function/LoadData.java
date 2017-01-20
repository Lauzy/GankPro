package com.freedom.lauzy.gankpro.function;

/**
 * Created by Lauzy on 2017/1/20.
 */

public enum LoadData {
    INIT_DATA(1), REFRESH_DATA(2), LOAD_MORE_DATA(3);

    private int mLoadType;

    LoadData(int loadType) {
        mLoadType = loadType;
    }

    @Override
    public String toString() {
        return "LoadData{" +
                "mLoadType=" + mLoadType +
                '}';
    }
}
