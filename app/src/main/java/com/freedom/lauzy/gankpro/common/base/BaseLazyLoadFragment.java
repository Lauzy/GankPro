package com.freedom.lauzy.gankpro.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseLazyLoadFragment extends Fragment{

    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;
    protected Activity mActivity;

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    protected abstract void loadData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, convertView);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        isInitView = true;
        lazyLoad();
    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            //如果不是第一次加载、不是可见的、不是初始化View，则不加载数据
            return;
        }
        //加载数据
        loadData();
        //设置已经不是第一次加载
        isFirstLoad = false;
    }
}
