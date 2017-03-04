package com.freedom.lauzy.gankpro.common.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
 * 加载更多RecyclerView
 * Created by Lauzy on 2017/2/6.
 */
@SuppressWarnings({"unused"})
public class LyLoadMoreRecyclerView extends LyRecyclerView {

    private static final String LYTAG = LyLoadMoreRecyclerView.class.getSimpleName();
    private View mLoadMoreView;
    private ProgressBar mPbFooter;
    private TextView mTxtFooter;
    private int mLastVisibleItemPosition;
    private Context mContext;

    public LyLoadMoreRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public LyLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public LyLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private void setLoadMoreFooter() {
        mLoadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_loadmore_footer, this, false);
//        mFooterLoadingView = View.inflate(mActivity, R.layout.layout_loadmore_footer, null);
        mPbFooter = (ProgressBar) mLoadMoreView.findViewById(R.id.pb_footer);
        mTxtFooter = (TextView) mLoadMoreView.findViewById(R.id.txt_footer);
    }

    OnLoadMoreListener mLoadMoreListener;

    /**
     * 设置加载更多
     *
     * @param headerCount 添加的头布局数量（暂未封装，需注意position变化）
     * @param loadMore    加载更多接口
     */

    public void setLoadMore(final int headerCount, final OnLoadMoreListener loadMore) {
        setLoadMoreFooter();
        mLoadMoreListener = loadMore;
        this.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                addFooterView(mLoadMoreView);//position + 1
                enableLoadMore();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == mAdapter.getItemCount() + headerCount + 1) {
                    mLoadMoreListener.loadMore();
                    Log.i(LYTAG, "onScrollStateChanged: ");
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

    public void setLoadMore(final SwipeRefreshLayout swipeRefreshLayout, final int headerCount, final OnLoadMoreListener loadMore) {
        setLoadMoreFooter();
        mLoadMoreListener = loadMore;
        this.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                addFooterView(mLoadMoreView);//position + 1
                enableLoadMore();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == mAdapter.getItemCount() + headerCount + 1&& !swipeRefreshLayout.isRefreshing()) {
                    mLoadMoreListener.loadMore();
                    Log.i(LYTAG, "onScrollStateChanged: ");
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

    public void enableLoadMore() {
        mLoadMoreView.setVisibility(VISIBLE);
        mPbFooter.setVisibility(VISIBLE);
        mTxtFooter.setText("加载中……");
    }

    public void loadMoreFinish() {
        mLoadMoreView.setVisibility(VISIBLE);
        mPbFooter.setVisibility(GONE);
        mTxtFooter.setText("全部加载完毕");
//        mLoadMoreListener.loadMoreComplete();
    }

    public void hideLoadMoreFooter(){
        mPbFooter.setVisibility(VISIBLE);
        /*mTxtFooter.setText("加载中……");
        mLoadMoreView.setVisibility(GONE);*/
    }

    public void loadMoreError() {
        mLoadMoreView.setVisibility(VISIBLE);
        mPbFooter.setVisibility(GONE);
        mTxtFooter.setText("加载失败点击重试");
        mTxtFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLoadMore();
                mLoadMoreListener.loadMore();
            }
        });
    }
}
