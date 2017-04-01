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

/**
 * Created by Lauzy on 2017/2/10.
 */

public abstract class LazyLoadFragment extends Fragment {

    private boolean isVisible;
    //setUserVisibleHint比onCreateView优先执行，用在ViewPager中会空指针，所以设置标记判断。
    private boolean isPrepared;
    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onUserVisible();
        } else {
            isVisible = false;
            onUserInVisible();
        }
    }

    protected Activity mActivity;
//    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    /**
     * call this method while the fragment is visible
     */
    private void onUserVisible() {
        if (isVisible && isPrepared) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        loadData();
    }

    protected abstract void loadData();

    /**
     * call this method while the fragment is invisible
     */
    private void onUserInVisible() {
        stopLoad();
    }

    /**
     * call this method while the fragment is invisible，
     * attention the nullPointException
     */
    protected void stopLoad() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
