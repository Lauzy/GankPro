package com.freedom.lauzy.gankpro.function.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.freedom.lauzy.gankpro.function.net.LySubscriber;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lauzy on 2017/3/17.
 */

public class RxSavePic {

    public static Observable<Uri> savePic(final Context context, final String imgUrl, final String picDesc) {
        return Observable
                .create(new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Picasso.with(context).load(imgUrl).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                        if (bitmap == null) {
                            subscriber.onError(new Throwable("can not download the picture"));
                        }
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                })
                /*.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                    }
                })*/
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        String fileName = "GankPic" + picDesc.replace('-', '_') + ".jpg";
                        SDCardUtils.saveImage(context, bitmap, fileName);
                        File file = new File(SDCardUtils.getDownloadPath(context), fileName);
                        Uri uri = Uri.fromFile(file);
                        // 通知图库更新
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                        return Observable.just(uri);
                    }
                });
    }
}

/*
*  Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(ImgBeautyActivity.this).load(mImgUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                if (bitmap == null) {
                    subscriber.onError(new Throwable("can not download the picture"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Bitmap, Observable<Uri>>() {
            @Override
            public Observable<Uri> call(Bitmap bitmap) {
                String fileName = "GankPic" + mPicDesc.replace('-', '_') + ".jpg";
                SDCardUtils.saveImage(ImgBeautyActivity.this, bitmap, fileName);
                File file = new File(SDCardUtils.getDownloadPath(ImgBeautyActivity.this), fileName);
                Uri uri = Uri.fromFile(file);
                // 通知图库更新
                Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                sendBroadcast(scannerIntent);
                return Observable.just(uri);
            }
        }).subscribeOn(Schedulers.io())
* */