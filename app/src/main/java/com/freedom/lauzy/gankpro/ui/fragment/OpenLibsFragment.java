package com.freedom.lauzy.gankpro.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.function.view.AndroidItemDecoration;
import com.freedom.lauzy.gankpro.model.LibsModel;
import com.freedom.lauzy.gankpro.ui.adapter.OpenLibsAdapter;

import butterknife.BindView;


public class OpenLibsFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_open_libs)
    RecyclerView mRvOpenLibs;

    private String mParam1;
    private String mParam2;

    private MineTitleListener mMineTitleListener;
    private OpenLibsAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MineTitleListener) {
            mMineTitleListener = (MineTitleListener) context;
        }
    }

    public OpenLibsFragment() {
    }

    public static OpenLibsFragment newInstance(String param1, String param2) {
        OpenLibsFragment fragment = new OpenLibsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_open_libs;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvOpenLibs.setLayoutManager(manager);
        LibsModel libsModel = new LibsModel();
        mAdapter = new OpenLibsAdapter(R.layout.layout_libs_item, libsModel.getLibsData(mActivity));
        mRvOpenLibs.setAdapter(mAdapter);
        mRvOpenLibs.addItemDecoration(new AndroidItemDecoration(mActivity));
        mRvOpenLibs.setNestedScrollingEnabled(false);
    }

    @Override
    protected void loadData() {
        if (mMineTitleListener != null) {
            mMineTitleListener.setTitle("开源库");
        }
        View headView = View.inflate(mActivity, R.layout.layout_head_lib_view, null);
        mAdapter.addHeaderView(headView);
    }

}
