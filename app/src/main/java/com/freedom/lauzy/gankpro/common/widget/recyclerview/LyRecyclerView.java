package com.freedom.lauzy.gankpro.common.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 添加头部，脚部的recyclerView
 * Created by Lauzy on 2017/2/6.
 */
@SuppressWarnings({"unused"})
public class LyRecyclerView extends RecyclerView {
    private static final String LYTAG = LyRecyclerView.class.getSimpleName();
    // 包裹了一层的头部底部Adapter
    private LyWrapAdapter mLyWrapAdapter;
    // 这个是列表数据的Adapter
    protected RecyclerView.Adapter mAdapter;

    // 增加一些通用功能
    private View mEmptyView, mLoadingView;

    public LyRecyclerView(Context context) {
        super(context);
    }

    public LyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        // 为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        this.mAdapter = adapter;

        if (adapter instanceof LyWrapAdapter) {
            mLyWrapAdapter = (LyWrapAdapter) adapter;
        } else {
            mLyWrapAdapter = new LyWrapAdapter(adapter);
        }

        super.setAdapter(mLyWrapAdapter);

        // 注册一个观察者
        mAdapter.registerAdapterDataObserver(mDataObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mLyWrapAdapter.adjustSpanSize(this);

        // 加载数据页面
        if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.setVisibility(View.GONE);
        }
        checkIfEmpty();
    }

    // 添加头部
    public void addHeaderView(View view) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
        if (mLyWrapAdapter != null) {
            mLyWrapAdapter.addHeaderView(view);
        }
    }

    // 添加底部
    public void addFooterView(View view) {
        if (mLyWrapAdapter != null) {
            mLyWrapAdapter.addFooterView(view);
        }
    }

    // 移除头部
    public void removeHeaderView(View view) {
        if (mLyWrapAdapter != null) {
            mLyWrapAdapter.removeHeaderView(view);
        }
    }

    // 移除底部
    public void removeFooterView(View view) {
        if (mLyWrapAdapter != null) {
            mLyWrapAdapter.removeFooterView(view);
        }
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyDataSetChanged();
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyItemRemoved(positionStart);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemMoved没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyItemMoved(fromPosition, toPosition);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyItemChanged(positionStart);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyItemChanged(positionStart, payload);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemInserted没效果
            if (mLyWrapAdapter != mAdapter)
                mLyWrapAdapter.notifyItemInserted(positionStart);
            checkIfEmpty();
        }
    };

    /**
     * 添加一个空列表数据页面
     */
    public void addEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        checkIfEmpty();
    }

    /**
     * 添加一个正在加载数据的页面
     */
    public void addLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    /**
     * Adapter数据改变的方法
     */
    private void checkIfEmpty() {
        /*if (mAdapter.getItemCount() == 0) {
            // 没有数据
            if (mEmptyView != null) {
                mEmptyView.setVisibility(VISIBLE);
            } else {
                mEmptyView.setVisibility(GONE);
            }
        }*/
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            this.setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }
}
