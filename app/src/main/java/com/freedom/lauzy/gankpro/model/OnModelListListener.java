package com.freedom.lauzy.gankpro.model;

import java.util.List;

/**
 * Created by Lauzy on 2017/1/20.
 */

public interface OnModelListListener<T> {
    void onSuccess(List<T> data);
    void onError(Throwable e);
}
