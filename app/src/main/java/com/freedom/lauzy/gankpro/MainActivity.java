package com.freedom.lauzy.gankpro;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.freedom.lauzy.gankpro.common.base.BaseActivity;
import com.freedom.lauzy.gankpro.ui.fragment.AndroidFragment;
import com.freedom.lauzy.gankpro.ui.fragment.BeautyFragment;
import com.freedom.lauzy.gankpro.ui.fragment.CategoryFragment;
import com.freedom.lauzy.gankpro.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.bottom_main_navigation)
    BottomNavigationView mBottomMainNavigation;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;

    private List<Fragment> mFragments = new ArrayList<>();
    private BeautyFragment mBeautyFragment;
    private CategoryFragment mCategoryFragment;
    private AndroidFragment mAndroidFragment;
    private MineFragment mMineFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initViews() {
        setStatusColor(R.color.colorPrimaryDark);
        MenuItem item = mBottomMainNavigation.getMenu().getItem(0);
        onNavigationItemSelected(item);//默认选中第一个
        mBottomMainNavigation.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏标题
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);

        switch (item.getItemId()) {
            case R.id.menu_main_item_beauty:
                setActTitle(R.string.bottom_main_item_beauty, Color.WHITE);
                if (mBeautyFragment == null) {
                    mBeautyFragment = new BeautyFragment();
                    transaction.add(R.id.main_frame, mBeautyFragment);
                    mFragments.add(mBeautyFragment);
                } else {
                    transaction.show(mBeautyFragment);
                }
                break;
            case R.id.menu_main_item_android:
                setActTitle(R.string.bottom_main_item_android, Color.WHITE);
                if (mAndroidFragment == null) {
                    mAndroidFragment = new AndroidFragment();
                    transaction.add(R.id.main_frame, mAndroidFragment);
                    mFragments.add(mAndroidFragment);
                } else {
                    transaction.show(mAndroidFragment);
                }
                break;
            case R.id.menu_main_item_category:
                setActTitle(R.string.bottom_main_item_category, Color.WHITE);
                if (mCategoryFragment == null) {
                    mCategoryFragment = new CategoryFragment();
                    transaction.add(R.id.main_frame, mCategoryFragment);
                    mFragments.add(mCategoryFragment);
                } else {
                    transaction.show(mCategoryFragment);
                }
                break;
            case R.id.menu_main_item_mine:
                setActTitle(R.string.bottom_main_item_mine, Color.WHITE);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.main_frame, mMineFragment);
                    mFragments.add(mMineFragment);
                } else {
                    transaction.show(mMineFragment);
                }
                break;
        }

        transaction.commit();

        return true;
    }

    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : mFragments) {
            transaction.hide(fragment);
        }
    }
}
