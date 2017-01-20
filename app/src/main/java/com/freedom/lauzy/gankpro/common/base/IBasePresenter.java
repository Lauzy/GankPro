package com.freedom.lauzy.gankpro.common.base;

/**
 * Created by Lauzy on 2017/1/20.
 */

public interface IBasePresenter<V extends BaseView> {
    void attachView(V baseView);
    void detachView();
}
