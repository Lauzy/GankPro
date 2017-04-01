package com.freedom.lauzy.gankpro.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;

public class MainFragmentActivity extends BaseToolbarActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main_fragment;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, MainFragment.newInstance(""))
                    .commit();
        }
    }

    @Override
    protected void initViews() {
    }
}
