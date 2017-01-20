package com.freedom.lauzy.gankpro.function.net;

import android.app.ProgressDialog;
import android.content.Context;

import rx.Subscriber;

/**
 * Created by Lauzy on 2017/1/20.
 */

public abstract class LySubscriber<T> extends Subscriber<T> {

    private ProgressDialog mProgressDialog;

    public LySubscriber(){

    }

    public LySubscriber(Context context) {
        if (context != null) {
            mProgressDialog = new ProgressDialog(context);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public abstract void onNext(T o);
}
