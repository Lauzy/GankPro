package com.freedom.lauzy.gankpro.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImgBeautyActivity extends BaseToolbarActivity {

    private static final String IMG_URL = "img_url";
    @BindView(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @BindView(R.id.img_beauty)
    ImageView mImgBeauty;
    @BindView(R.id.pb_beauty)
    ProgressBar mPbBeauty;

    public static Intent newInstance(Context context, String imgUrl) {
        Intent intent = new Intent(context, ImgBeautyActivity.class);
        intent.putExtra(IMG_URL, imgUrl);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_img_beauty;
    }

    @Override
    protected void initViews() {
        mToolbarCommon.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(mToolbarCommon);
        mToolbarCommon.setTitle("");
        ViewCompat.setTransitionName(mImgBeauty, "transitionImg");
    }

    @Override
    protected void loadData() {
        String imgUrl = getIntent().getStringExtra(IMG_URL);
        Picasso.with(this).load(imgUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mPbBeauty.setVisibility(View.GONE);
                mImgBeauty.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                mPbBeauty.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                mPbBeauty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pic_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
