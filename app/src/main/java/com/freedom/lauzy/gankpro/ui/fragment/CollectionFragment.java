package com.freedom.lauzy.gankpro.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;

import butterknife.BindView;

public class CollectionFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_collection)
    RecyclerView mRvCollection;


    public CollectionFragment() {
    }

    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
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
        return R.layout.fragment_collection;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvCollection.setLayoutManager(manager);
    }

    @Override
    protected void loadData() {

    }

}
