package com.freedom.lauzy.gankpro.common.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;

/**
 * 添加头部，脚部的recyclerView
 * Created by Lauzy on 2017/2/6.
 */
@SuppressWarnings({"unused"})
public class LyRecyclerView1 extends RecyclerView {
    private static final String LYTAG = LyRecyclerView1.class.getSimpleName();
    // 包裹了一层的头部底部Adapter
    private LyWrapAdapter mLyWrapAdapter;
    // 这个是列表数据的Adapter
    private Adapter mAdapter;

    // 增加一些通用功能
    // 空列表数据应该显示的空View
    // 正在加载数据页面，也就是正在获取后台接口页面
    private View mEmptyView, mLoadingView;
    private View mLoadMoreView;
    private ProgressBar mPbFooter;
    private TextView mTxtFooter;
    private int mLastVisibleItemPosition;
    private Context mContext;

    public LyRecyclerView1(Context context) {
        super(context);
        mContext = context;
    }

    public LyRecyclerView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public LyRecyclerView1(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private void setLoadMoreFooter() {
        mLoadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_loadmore_footer, this, false);
//        mFooterLoadingView = View.inflate(mActivity, R.layout.layout_loadmore_footer, null);
        mPbFooter = (ProgressBar) mLoadMoreView.findViewById(R.id.pb_footer);
        mTxtFooter = (TextView) mLoadMoreView.findViewById(R.id.txt_footer);
    }

    @Override
    public void setAdapter(Adapter adapter) {
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

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
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

    OnLoadMoreListener mLoadMoreListener;

    public void setLoadMore(final int headerCount, final OnLoadMoreListener loadMore) {
        setLoadMoreFooter();
        this.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                addFooterView(mLoadMoreView);//position + 1
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == mAdapter.getItemCount() + headerCount + 1) {
                    loadMore.loadMore();
                    Log.e(LYTAG, "onScrollStateChanged: ");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getLayoutManager() instanceof LinearLayoutManager) {
                    mLastVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                }
                if (getLayoutManager() instanceof GridLayoutManager) {
                    mLastVisibleItemPosition = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                }
            }
        });
    }

    public void enableLoadMore(){
        mPbFooter.setVisibility(VISIBLE);
        mTxtFooter.setText("加载中……");
    }

    public void loadMoreFinish() {
        mPbFooter.setVisibility(GONE);
        mTxtFooter.setText("全部加载完毕");
//        mLoadMoreListener.loadMoreComplete();
    }

    public void loadMoreError() {
        mPbFooter.setVisibility(GONE);
        mTxtFooter.setText("加载失败");
    }
}
