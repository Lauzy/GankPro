package com.freedom.lauzy.gankpro.common.base;

/**
 * basePresenter
 * Created by Lauzy on 2017/1/20.
 */

public class BasePresenter<T extends BaseView> implements IBasePresenter<T> {
    private T mBaseView;

    @Override
    public void attachView(T mvpBaseView) {
        mBaseView = mvpBaseView;
    }

    @Override
    public void detachView() {
        mBaseView = null;
    }

    public T getMvpBaseView() {
        return mBaseView;
    }

    /**
     * 是否附加view接口
     *
     * @return true baseView != null
     */
    public boolean isViewAttached() {
        return mBaseView != null;
    }

    /**
     * 检查是否绑定view接口
     */
    public void checkViewAttached() {
        if (!isViewAttached())
            throw new RuntimeException(
                    "Please call BasePresenter.attachView(baseView) before"
                            + " requesting data to the Presenter");
    }
}
