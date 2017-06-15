package com.freedom.lauzy.gankpro.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.freedom.lauzy.gankpro.ui.fragment.CategoryChildFragment;

/**
 * Created by Lauzy on 2017/3/20.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    private String[] mTypes;
    private String[] mTitle;
//    private GankBottomBehavior mBottomBehavior;

    public CategoryPagerAdapter(FragmentManager fm, String[] types) {
        super(fm);
        mTypes = types;
//        mBottomBehavior = bottomBehavior;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryChildFragment.newInstance(mTypes[position]);
    }

    @Override
    public int getCount() {
        return mTypes == null ? 0 : mTypes.length;
    }

    public void setTitle(String[] title) {
        mTitle = title;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
