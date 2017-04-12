package com.freedom.lauzy.gankpro.common.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.freedom.lauzy.gankpro.common.utils.TransitionHelper;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * base
 * Created by Lauzy on 2017/1/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CompositeSubscription mCompositeSubscription;
    protected RxPermissions mRxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onCreate: ");
        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        mRxPermissions = new RxPermissions(this);
        loadFragment(savedInstanceState);
        initViews();
        loadData();
    }

    @Override
    public void recreate() {
        super.recreate();
        Log.i(getClass().getSimpleName(), "recreate: ");
    }

    protected void loadFragment(Bundle savedInstanceState) {

    }


    protected abstract int getLayoutResId();

    protected abstract void initViews();

    protected abstract void loadData();

    protected Observable<Permission> reQuestEachPermission(String... permissions) {
        return mRxPermissions.requestEach(permissions);
    }

    protected Observable<Boolean> reQuestPermission(String... permissions) {
        return mRxPermissions.request(permissions);
    }

    /**
     * 若存在toolBar，可使用
     */
   /* protected void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        if (toolbar != null) {
            toolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(RrlApp.getInstance());
            toolbar.setPadding(0, ScreenUtils.getStatusHeight(RrlApp.getInstance()), 0, 0);
            setSupportActionBar(toolbar);
        }
    }*/
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(), "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume: ");
        /*MobclickAgent.onPageStart("BaseActivity");
        MobclickAgent.onResume(this);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause: ");
        /*MobclickAgent.onPageEnd("BaseActivity");
        MobclickAgent.onPause(this);*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(getClass().getSimpleName(), "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(), "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy: ");
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void transitionTo(Intent intent) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }
}