package com.freedom.lauzy.gankpro.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.common.widget.behavior.GankBehavior;
import com.freedom.lauzy.gankpro.common.widget.behavior.GankBottomBehavior;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.ui.fragment.AndroidFragment;
import com.freedom.lauzy.gankpro.ui.fragment.BeautyFragment;
import com.freedom.lauzy.gankpro.ui.fragment.CategoryFragment;
import com.freedom.lauzy.gankpro.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment implements BottomNavigationView
        .OnNavigationItemSelectedListener, MineTitleListener {

    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.bottom_main_navigation)
    BottomNavigationView mBottomMainNavigation;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.txt_toolbar_title)
    TextView mTxtToolbarTitle;
    @BindView(R.id.fab_mode)
    FloatingActionButton mFabMode;

    private List<Fragment> mFragments = new ArrayList<>();
    private BeautyFragment mBeautyFragment;
    private CategoryFragment mCategoryFragment;
    private AndroidFragment mAndroidFragment;
    private MineFragment mMineFragment;
    private GankBehavior mGankBehavior;
    private GankBottomBehavior mGankBottomBehavior;
    private String mTitle;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews() {
        mGankBehavior = GankBehavior.from(mToolbar);
        mGankBottomBehavior = GankBottomBehavior.from(mBottomMainNavigation);
//        mGankBehavior.show();
//        setStatusColor(R.color.colorPrimaryDark);
        MenuItem item = mBottomMainNavigation.getMenu().getItem(0);
        onNavigationItemSelected(item);//默认选中第一个
        mBottomMainNavigation.setOnNavigationItemSelectedListener(this);
        /*mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
        mToolbar.setPadding(0, ScreenUtils.getStatusHeight(GankApp.getInstance()), 0, 0);*/
        ((AppCompatActivity) mActivity).setSupportActionBar(mToolbar);
        mTxtToolbarTitle.setText("妹纸");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);

        switch (item.getItemId()) {
            case R.id.menu_main_item_beauty:
                setNavigationItem("妹纸", true, View.VISIBLE);
                if (mBeautyFragment == null) {
                    mBeautyFragment = new BeautyFragment();
                    transaction.add(R.id.main_frame, mBeautyFragment);
//                    mFragments.add(mBeautyFragment);
                    addToFragments(mBeautyFragment);
                } else {
                    transaction.show(mBeautyFragment);
                }
                break;
            case R.id.menu_main_item_android:
                setNavigationItem("Android", true, View.VISIBLE);
                if (mAndroidFragment == null) {
                    mAndroidFragment = new AndroidFragment();
                    transaction.add(R.id.main_frame, mAndroidFragment);
//                    mFragments.add(mAndroidFragment);
                    addToFragments(mAndroidFragment);
                } else {
                    transaction.show(mAndroidFragment);
                }
                break;
            case R.id.menu_main_item_category:
                setNavigationItem("分类", false, View.VISIBLE);
                if (mCategoryFragment == null) {
                    mCategoryFragment = new CategoryFragment();
                    transaction.add(R.id.main_frame, mCategoryFragment);
//                    mFragments.add(mCategoryFragment);
                    addToFragments(mCategoryFragment);
                } else {
                    transaction.show(mCategoryFragment);
                }
                break;
            case R.id.menu_main_item_mine:
                setNavigationItem(mTitle, true, View.GONE);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.main_frame, mMineFragment);
//                    mFragments.add(mMineFragment);
                    addToFragments(mMineFragment);
                } else {
                    transaction.show(mMineFragment);
                }
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

    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : mFragments) {
            transaction.hide(fragment);
        }
    }

    private void addToFragments(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public void setTitle(String title) {
        mTxtToolbarTitle.setText(title);
        mTitle = title;
    }

}
