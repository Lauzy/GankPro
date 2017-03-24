package com.freedom.lauzy.gankpro.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;
import com.freedom.lauzy.gankpro.function.view.AndroidItemDecoration;
import com.freedom.lauzy.gankpro.function.view.DailyItemDecoration;
import com.freedom.lauzy.gankpro.model.CollectionModel;
import com.freedom.lauzy.gankpro.ui.adapter.CollectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CollectionFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_collection)
    RecyclerView mRvCollection;
    private List<CollectionEntity> mCollectionEntities = new ArrayList<>();
    private CollectionAdapter mAdapter;


    public CollectionFragment() {
    }

    private MineTitleListener mMineTitleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MineTitleListener) {
            mMineTitleListener = (MineTitleListener) context;
        }
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
        mAdapter = new CollectionAdapter
                (R.layout.layout_collection_item, mCollectionEntities);
        mRvCollection.setAdapter(mAdapter);
        mRvCollection.addItemDecoration(new AndroidItemDecoration(mActivity));
    }

    @Override
    protected void loadData() {
        CollectionModel collectionModel = new CollectionModel();
        collectionModel.getCollectionData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<CollectionEntity>>() {
                    @Override
                    public void call(List<CollectionEntity> collectionEntities) {
                        mCollectionEntities.addAll(collectionEntities);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        if (mMineTitleListener != null) {
            mMineTitleListener.setTitle("我的收藏");
        }
    }

}
