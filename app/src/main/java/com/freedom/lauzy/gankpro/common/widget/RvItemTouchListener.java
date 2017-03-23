package com.freedom.lauzy.gankpro.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/23.
 */

public class RvItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mDetector;
    private RecyclerView mRecyclerView;
    private RvItemClickListener mListener;

    public RvItemTouchListener(Context context, RecyclerView recyclerView, RvItemClickListener listener) {
        mRecyclerView = recyclerView;
        mListener = listener;
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                int position = mRecyclerView.getChildAdapterPosition(view);
                if (mListener != null) {
                    mListener.rvItemLongClick(position);
                }
            }
        });
    }

    /**
     * 对触屏事件进行拦截，拦截之前先将MotionEvent传递给GestureDetector
     * 去进行手势判断，只有onSingleTapUp方法中返回了true
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //如果detector.onTouchEvent(e)返回的是true，说明监听到了onSigleTapUp事件并返回true
        if (mDetector.onTouchEvent(e) && mListener != null) {
            /**
             * 通过RecyclerView获取当前点击的位置信息
             */
            //1 先通过MotionEvent的x/y坐标找到点击的View对象
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = mRecyclerView.getChildAdapterPosition(view);
            mListener.rvItemClick(position);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface RvItemClickListener {

        void rvItemClick(int position);

        void rvItemLongClick(int position);
    }
}

