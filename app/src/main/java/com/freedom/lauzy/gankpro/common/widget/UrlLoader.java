package com.freedom.lauzy.gankpro.common.widget;

import android.content.Context;

import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

/**
 * Created by Lauzy on 2017/1/22.
 */

public class UrlLoader extends BaseGlideUrlLoader<UrlLoader.BitmapModel> {

    public UrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(BitmapModel model, int width, int height) {
        return model.buildUrl(width, height);
    }

    interface BitmapModel {
        String buildUrl(int width, int height);
    }
}
