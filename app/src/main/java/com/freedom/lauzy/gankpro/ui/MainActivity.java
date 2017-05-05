package com.freedom.lauzy.gankpro.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.NetUtils;
import com.freedom.lauzy.gankpro.common.widget.behavior.GankBehavior;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.function.utils.SharePrefUtils;
import com.freedom.lauzy.gankpro.ui.fragment.AndroidFragment;
import com.freedom.lauzy.gankpro.ui.fragment.BeautyFragment;
import com.freedom.lauzy.gankpro.ui.fragment.CategoryFragment;
import com.freedom.lauzy.gankpro.ui.fragment.MineFragment;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends BaseToolbarActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener, MineTitleListener {

    private static final String LYTAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "fragment_key";
    @BindView(R.id.bottom_main_navigation)
    BottomNavigationView mBottomMainNavigation;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.txt_toolbar_title)
    TextView mTxtToolbarTitle;
    @BindView(R.id.fab_mode)
    FloatingActionButton mFabMode;

    private BeautyFragment mBeautyFragment;
    private CategoryFragment mCategoryFragment;
    private AndroidFragment mAndroidFragment;
    private MineFragment mMineFragment;
    private GankBehavior mGankBehavior;
    //    private GankBottomBehavior mGankBottomBehavior;
    private String mTitle;

    private static final int MSG_SET_ALIAS = 0x0011;
    private static final int MSG_SET_TAGS = 0x0012;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        /*TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath()
        +"/gank_patch.apk");*/
        //推送设置别名，实际项目中可根据登录信息，可在登录成功后将token等信息作为标签或别名，此处模拟，设置固定值

        String tag = "TAG1,TAG2,TAG3";
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<>();
        Collections.addAll(tagSet, sArray);

        /*mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "Gank"));
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));*/


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Log.e(LYTAG, "call: ");
                JPushInterface.setAliasAndTags(getApplicationContext(), "Gank2", null, new GankTagAliasCallback(new TagCallback() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext("Gank2");
                        subscriber.onCompleted();
                        Log.e(LYTAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                        Log.e(LYTAG, "onError: ");
                    }
                }));
            }
        }).retry(2).subscribe();

    }


    public class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {
            return attempts.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable throwable) {
                    if (++retryCount <= maxRetries) {
                        return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }

    interface TagCallback {
        void onSuccess();

        void onError(Throwable throwable);
    }

    class GankTagAliasCallback implements TagAliasCallback {

        private TagCallback mTagCallback;

        public GankTagAliasCallback(TagCallback tagCallback) {
            mTagCallback = tagCallback;
        }

        @Override
        public void gotResult(int i, String s, Set<String> set) {
            switch (i) {
                case 0:
                    mTagCallback.onSuccess();
                    break;
                case 6002:
                    mTagCallback.onError(new Throwable("FAIL"));
                    break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(LYTAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_SET_TAGS:
                    Log.d(LYTAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
                default:
                    Log.i(LYTAG, "Unhandled msg - " + msg.what);
            }
            return false;
        }
    });

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag success";
                    Log.i(LYTAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias due to timeout. Try again after 60s.";
                    Log.i(LYTAG, logs);
                    if (NetUtils.isNetworkConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(LYTAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(LYTAG, logs);
            }
        }
    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set alias success";
                    Log.i(LYTAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set tags due to timeout. Try again after 60s.";
                    Log.i(LYTAG, logs);
                    if (NetUtils.isNetworkConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(LYTAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(LYTAG, logs);
            }
        }

    };

    /**
     * 解决重启Activity时fragment重叠的问题
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void loadFragment(Bundle savedInstanceState) {
        super.loadFragment(savedInstanceState);
        if (savedInstanceState == null) {
            mMineFragment = new MineFragment();
            mBeautyFragment = new BeautyFragment();
            mAndroidFragment = new AndroidFragment();
            mCategoryFragment = new CategoryFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_frame, mMineFragment, mMineFragment.getClass().getSimpleName())
                    .add(R.id.main_frame, mBeautyFragment, mBeautyFragment.getClass().getSimpleName())
                    .add(R.id.main_frame, mAndroidFragment, mAndroidFragment.getClass().getSimpleName())
                    .add(R.id.main_frame, mCategoryFragment, mCategoryFragment.getClass().getSimpleName())
                    .show(mBeautyFragment)
                    .hide(mAndroidFragment)
                    .hide(mCategoryFragment)
                    .hide(mMineFragment)
                    .commit();
        } else {
            mBeautyFragment = (BeautyFragment) getSupportFragmentManager()
                    .findFragmentByTag(BeautyFragment.class.getSimpleName());
            mAndroidFragment = (AndroidFragment) getSupportFragmentManager()
                    .findFragmentByTag(AndroidFragment.class.getSimpleName());
            mCategoryFragment = (CategoryFragment) getSupportFragmentManager()
                    .findFragmentByTag(CategoryFragment.class.getSimpleName());
            mMineFragment = (MineFragment) getSupportFragmentManager()
                    .findFragmentByTag(MineFragment.class.getSimpleName());
            getSupportFragmentManager().beginTransaction()
                    .show(mBeautyFragment)
                    .hide(mAndroidFragment)
                    .hide(mCategoryFragment)
                    .hide(mMineFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, BeautyFragment.class.getSimpleName(), mBeautyFragment);
//        getSupportFragmentManager().putFragment(outState, AndroidFragment.class.getSimpleName(), mAndroidFragment);
        if (mBeautyFragment.isVisible()) {
            outState.putString(FRAGMENT_TAG, BeautyFragment.class.getSimpleName());
        } else if (mAndroidFragment.isVisible()) {
            outState.putString(FRAGMENT_TAG, AndroidFragment.class.getSimpleName());
        } else if (mMineFragment.isVisible()) {
            outState.putString(FRAGMENT_TAG, MineFragment.class.getSimpleName());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        mBeautyFragment = (BeautyFragment) getSupportFragmentManager().getFragment(savedInstanceState,BeautyFragment.class.getSimpleName());
//        mAndroidFragment = (AndroidFragment) getSupportFragmentManager().getFragment(savedInstanceState,AndroidFragment.class.getSimpleName());
        String tag = savedInstanceState.getString(FRAGMENT_TAG);
        Log.i(LYTAG, "onRestoreInstanceState: " + tag);
        if (AndroidFragment.class.getSimpleName().equals(tag)) {
            mBottomMainNavigation.getMenu().getItem(1).setChecked(true);
            onNavigationItemSelected(mBottomMainNavigation.getMenu().getItem(1));
            mTxtToolbarTitle.post(new Runnable() {
                @Override
                public void run() {
                    mTxtToolbarTitle.setText("Android");
                }
            });
        } else if (MineFragment.class.getSimpleName().equals(tag)) {
            mBottomMainNavigation.getMenu().getItem(3).setChecked(true);
            onNavigationItemSelected(mBottomMainNavigation.getMenu().getItem(3));
            mTxtToolbarTitle.post(new Runnable() {
                @Override
                public void run() {
                    mTxtToolbarTitle.setText("Mine");
                }
            });
        }
    }

    @Override
    protected void initViews() {
        mGankBehavior = GankBehavior.from(mToolbar);
//        mGankBottomBehavior = GankBottomBehavior.from(mBottomMainNavigation);
        MenuItem item = mBottomMainNavigation.getMenu().getItem(0);
        onNavigationItemSelected(item);//默认选中第一个
        mBottomMainNavigation.setOnNavigationItemSelectedListener(this);
        /*mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
        mToolbar.setPadding(0, ScreenUtils.getStatusHeight(GankApp.getInstance()), 0, 0);*/
        setSupportActionBar(mToolbar);

        mTxtToolbarTitle.post(new Runnable() {
            @Override
            public void run() {
                mTxtToolbarTitle.setText("妹纸");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mTxtToolbarTitle.setText("妹纸");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_main_item_beauty:
                setNavigationItem("妹纸", true, View.VISIBLE);
                transaction.show(mBeautyFragment).hide(mAndroidFragment)
                        .hide(mCategoryFragment).hide(mMineFragment);
                break;
            case R.id.menu_main_item_android:
                setNavigationItem("Android", true, View.VISIBLE);
                transaction.show(mAndroidFragment).hide(mBeautyFragment)
                        .hide(mCategoryFragment).hide(mMineFragment);
                break;
            case R.id.menu_main_item_category:
                setNavigationItem("分类", false, View.GONE);
                transaction.show(mCategoryFragment).hide(mBeautyFragment)
                        .hide(mAndroidFragment).hide(mMineFragment);
                break;
            case R.id.menu_main_item_mine:
                setNavigationItem(mTitle, true, View.GONE);
                transaction.show(mMineFragment).hide(mBeautyFragment)
                        .hide(mAndroidFragment).hide(mCategoryFragment);
                break;
        }
        transaction.commit();
        return true;
    }

    private void setNavigationItem(String title, boolean canScroll, int fabVisible) {
        //                mToolbar.setVisibility(View.VISIBLE);
        mTxtToolbarTitle.setText(title);
        mGankBehavior.show();
        mGankBehavior.setCanScroll(canScroll);
        mFabMode.setVisibility(fabVisible);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
        /*if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (mPosition != 0) {
                onNavigationItemSelected(mBottomMainNavigation.getMenu().getItem(0));//选中第一个
                mBottomMainNavigation.getMenu().getItem(0).setChecked(true);
            } else {
                finish();
            }
        }*/
    }

    @Override
    public void setTitle(String title) {
        mTxtToolbarTitle.setText(title);
        mTitle = title;
    }


    @OnClick(R.id.fab_mode)
    public void onClick() {
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            SharePrefUtils.setNightMode(false);
        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            SharePrefUtils.setNightMode(true);
        }
        getWindow().setWindowAnimations(R.style.WindowSwitchDayNightModeAnimStyle);
        recreate();


        /*final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }*/
    }
}
