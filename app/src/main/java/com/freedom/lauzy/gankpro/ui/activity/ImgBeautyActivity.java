package com.freedom.lauzy.gankpro.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.ToastUtils;
import com.freedom.lauzy.gankpro.function.utils.RxSavePic;
import com.freedom.lauzy.gankpro.function.utils.SDCardUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class ImgBeautyActivity extends BaseToolbarActivity {

    private static final String IMG_URL = "img_url";
    private static final String PIC_DESC = "pic_desc";
    @BindView(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @BindView(R.id.img_beauty)
    ImageView mImgBeauty;
    @BindView(R.id.pb_beauty)
    ProgressBar mPbBeauty;
    private String mImgUrl;
    private String mPicDesc;

    public static Intent newInstance(Context context, String imgUrl, String picDesc) {
        Intent intent = new Intent(context, ImgBeautyActivity.class);
        intent.putExtra(IMG_URL, imgUrl);
        intent.putExtra(PIC_DESC, picDesc);
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
        ViewCompat.setTransitionName(mImgBeauty, "transitionDetailImg");

    }

    @Override
    protected void loadData() {
        mImgUrl = getIntent().getStringExtra(IMG_URL);
        mPicDesc = getIntent().getStringExtra(PIC_DESC);
        Picasso.with(this).load(mImgUrl).into(new Target() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_save_pic:
                mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            savePicture();
                        } else if (permission.shouldShowRequestPermissionRationale) {

                        } else {

                        }
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePicture() {
        Subscription picSub = RxSavePic.savePic(ImgBeautyActivity.this, mImgUrl, mPicDesc)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Uri>() {
                    @Override
                    public void call(Uri uri) {
                        File appDir = new File(SDCardUtils.getDownloadPath(ImgBeautyActivity.this));
                        String msg = String.format("图片已保存至 %s 文件夹", appDir.getAbsolutePath());
                        ToastUtils.showShort(ImgBeautyActivity.this, msg);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShort(ImgBeautyActivity.this, "出错了，再试试");
                    }
                });
        addSubscription(picSub);
    }
}
