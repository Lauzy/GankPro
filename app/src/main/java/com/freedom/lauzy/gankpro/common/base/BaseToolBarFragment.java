package com.freedom.lauzy.gankpro.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;


/**
 * Fragment基类
 * Created by Lauzy on 2017/1/20.
 */

public abstract class BaseToolBarFragment extends BaseFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_common);
        if (toolbar != null) {
            toolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(GankApp.getInstance());
            toolbar.setPadding(0, ScreenUtils.getStatusHeight(GankApp.getInstance()), 0, 0);
        }
    }
}
