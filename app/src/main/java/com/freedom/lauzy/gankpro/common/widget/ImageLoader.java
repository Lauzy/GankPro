package com.freedom.lauzy.gankpro.common.widget;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Picasso
 * Created by Lauzy on 2017/1/24.
 */

public class ImageLoader {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }
}
