package com.freedom.lauzy.gankpro.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.IntentUtils;
import com.freedom.lauzy.gankpro.common.utils.ToastUtils;
import com.freedom.lauzy.gankpro.function.utils.RxSavePic;
import com.freedom.lauzy.gankpro.function.utils.SDCardUtils;
import com.freedom.lauzy.gankpro.function.utils.SnackBarUtils;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbruyelle.rxpermissions.Permission;

import java.io.File;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
        ViewCompat.setTransitionName(mImgBeauty, getString(R.string.string_img_share_elements));
        setupWindowAnimations();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        getWindow().setEnterTransition(TransitionUtils.buildExplodeExitAnim(new LinearOutSlowInInterpolator()));
        getWindow().setExitTransition(TransitionUtils.buildExplodeExitAnim(new FastOutLinearInInterpolator()));
        getWindow().setReturnTransition(TransitionUtils.buildExplodeExitAnim(new LinearInterpolator()));
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

                /*reQuestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE)//will emit 2 Permission objects*/
                reQuestEachPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Permission>() {
                            @Override
                            public void call(Permission permission) {
                                if (permission.granted) {
                                    savePicture();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    SnackBarUtils.shortSnackbar(findViewById(android.R.id.content)
                                            , "您拒绝了权限，无法保存妹纸哦", Color.WHITE, Color.DKGRAY).show();
                                } else {
                                    gotoSetting();
                                }
                            }
                        });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoSetting() {
        AlertDialog alertDialog = new AlertDialog.Builder(ImgBeautyActivity.this)
                .setMessage("当前应用缺少必要权限\n请点击“设置” - “权限”-打开所需权限。")
                .setCancelable(true)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(IntentUtils.openSetting(ImgBeautyActivity.this));
                    }
                })
                .setNegativeButton("取消",null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {                    //
                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(ImgBeautyActivity.this,
                                R.color.color_positive));

                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(ImgBeautyActivity.this,
                                R.color.color_negative));

            }
        });
        alertDialog.show();
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
