package com.freedom.lauzy.gankpro.function.net.callback;

/**
 * res
 * Created by Lauzy on 2017/1/18.
 */

public interface OnResponse<T> {
    void onSuccess(T t);
    void onError(Throwable e);
}
