package com.freedom.lauzy.gankpro.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.freedom.lauzy.gankpro.common.widget.behavior.GankBehavior;
import com.freedom.lauzy.gankpro.common.widget.behavior.GankBottomBehavior;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.ui.fragment.AndroidFragment;
import com.freedom.lauzy.gankpro.ui.fragment.BeautyFragment;
import com.freedom.lauzy.gankpro.ui.fragment.CategoryFragment;
import com.freedom.lauzy.gankpro.ui.fragment.MineFragment;

import butterknife.BindView;
import butterknife.OnClick;

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
    private GankBottomBehavior mGankBottomBehavior;
    private String mTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
    }

    /**
     * 解决重启Activity时fragment重叠的问题
     *
     * @param savedInstanceState
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
        } else if (MineFragment.class.getSimpleName().equals(tag)) {
            mBottomMainNavigation.getMenu().getItem(3).setChecked(true);
            onNavigationItemSelected(mBottomMainNavigation.getMenu().getItem(3));
        }
    }

    @Override
    protected void initViews() {
        mGankBehavior = GankBehavior.from(mToolbar);
        mGankBottomBehavior = GankBottomBehavior.from(mBottomMainNavigation);
        MenuItem item = mBottomMainNavigation.getMenu().getItem(0);
        onNavigationItemSelected(item);//默认选中第一个
        mBottomMainNavigation.setOnNavigationItemSelectedListener(this);
        /*mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
        mToolbar.setPadding(0, ScreenUtils.getStatusHeight(GankApp.getInstance()), 0, 0);*/
        setSupportActionBar(mToolbar);
        mTxtToolbarTitle.setText("妹纸");
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
        } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }
}
