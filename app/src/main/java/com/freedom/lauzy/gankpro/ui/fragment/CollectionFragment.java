package com.freedom.lauzy.gankpro.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;
import com.freedom.lauzy.gankpro.function.greendao.CollectionEntityDao;
import com.freedom.lauzy.gankpro.function.view.AndroidItemDecoration;
import com.freedom.lauzy.gankpro.presenter.CollectionPresenter;
import com.freedom.lauzy.gankpro.ui.adapter.CollectionAdapter;
import com.freedom.lauzy.gankpro.view.CollectionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CollectionFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LYTAG = CollectionFragment.class.getSimpleName();

    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_collection)
    RecyclerView mRvCollection;
    @BindView(R.id.srl_collection)
    SwipeRefreshLayout mSrlCollection;
    private List<CollectionEntity> mCollectionEntities = new ArrayList<>();
    private CollectionAdapter mAdapter;
    private CollectionPresenter mCollectionPresenter;
    private View mEmptyView;


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
        initRecyclerView();
        mSrlCollection.setProgressViewOffset(true, 120, 240);
        mSrlCollection.setRefreshing(true);
        mSrlCollection.setColorSchemeResources(R.color.color_srl_gray);
        mSrlCollection.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCollectionPresenter.refreshData();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvCollection.setLayoutManager(manager);
        mAdapter = new CollectionAdapter
                (R.layout.layout_collection_item, mCollectionEntities);
        mRvCollection.setAdapter(mAdapter);
        mRvCollection.addItemDecoration(new AndroidItemDecoration(mActivity));
        mRvCollection.setNestedScrollingEnabled(false);
        mEmptyView = View.inflate(mActivity, R.layout.layout_empty_view, null);
    }

    @Override
    protected void loadData() {
        if (mMineTitleListener != null) {
            mMineTitleListener.setTitle("我的收藏");
        }
        initPresenter();
        mCollectionPresenter.initData();
        longClickDelete();
    }

    private void longClickDelete() {

        mRvCollection.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                final CollectionEntity entity = (CollectionEntity) adapter.getData().get(position);
                PopupMenu popupMenu = new PopupMenu(mActivity, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_delete_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        removeItem(entity, position);
                        return false;
                    }
                });
                popupMenu.show();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    private void initPresenter() {
        mCollectionPresenter = new CollectionPresenter(new CollectionView() {
            @Override
            public void initRvData(List<CollectionEntity> data) {
                //删除第一个会出现错位bug，所以设置setNestedScrollingEnabled 为false
                if (data != null && data.size() != 0 && !data.isEmpty()) {
                    /*int itemNum = data.size();
                    int totalHeight = DensityUtils.dp2px(mActivity, 50) * itemNum + DensityUtils.dp2px(mActivity, (40 + 52))
                            + ScreenUtils.getStatusHeight(mActivity);
                    Log.i(LYTAG, "call: 总高度为" + totalHeight);
                    if (totalHeight <= ScreenUtils.getScreenHeight(mActivity)) {
                        mRvCollection.setNestedScrollingEnabled(false);
                    } else {
                        mRvCollection.setNestedScrollingEnabled(true);
                    }*/
                    mCollectionEntities.addAll(data);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mAdapter.setEmptyView(mEmptyView);
                }
                mSrlCollection.setRefreshing(false);
            }

            @Override
            public void refreshRvData(List<CollectionEntity> refreshData) {
                if (refreshData != null) {
                    mAdapter.setNewData(refreshData);
                   /* int itemNum = refreshData.size();
                    int totalHeight = DensityUtils.dp2px(mActivity, 50) * itemNum + DensityUtils.dp2px(mActivity, (40 + 52))
                            + ScreenUtils.getStatusHeight(mActivity);
                    Log.i(LYTAG, "call: 总高度为" + totalHeight);
                    if (totalHeight <= ScreenUtils.getScreenHeight(mActivity)) {
                        mRvCollection.setNestedScrollingEnabled(false);
                    } else {
                        mRvCollection.setNestedScrollingEnabled(true);
                    }*/
                }
                mSrlCollection.setRefreshing(false);
            }

            @Override
            public void initError(Throwable e) {
                e.printStackTrace();
                mSrlCollection.setRefreshing(false);
            }

            @Override
            public void refreshError(Throwable e) {
                e.printStackTrace();
                mSrlCollection.setRefreshing(false);
            }
        });
    }

    private void removeItem(final CollectionEntity entity, final int position) {
        Observable.create(new Observable.OnSubscribe<CollectionEntity>() {
            @Override
            public void call(Subscriber<? super CollectionEntity> subscriber) {
                CollectionEntityDao entityDao = GankApp.getInstance().getDaoSession().getCollectionEntityDao();
                entityDao.delete(entity);
                mCollectionEntities.remove(entity);
                subscriber.onNext(entity);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CollectionEntity>() {
                    @Override
                    public void call(CollectionEntity entity) {
                        mAdapter.notifyItemRemoved(position);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}
