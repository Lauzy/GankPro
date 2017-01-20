package com.freedom.lauzy.gankpro.model;

/**
 * Created by Lauzy on 2017/1/20.
 */

public interface OnModelListener<T> {
    void onSuccess(T data);
    void onError(Throwable e);
}
