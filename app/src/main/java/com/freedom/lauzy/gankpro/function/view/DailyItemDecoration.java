package com.freedom.lauzy.gankpro.function.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.freedom.lauzy.gankpro.function.entity.ItemBean;

import java.util.List;

/**
 * daily悬停分组
 * Created by Lauzy on 2017/3/15.
 */

public class DailyItemDecoration extends RecyclerView.ItemDecoration {

    private static final int COLOR_TITLE_BG = Color.parseColor("#FFFFFE");
    private static final int COLOR_TITLE_FONT = Color.parseColor("#000000");
    private static final String LYTAG = DailyItemDecoration.class.getSimpleName();
    private final Paint mPaint;
    private final Rect mTextRect;
    private int mTitleHeight;
    private List<ItemBean> mItemBeen;

    public DailyItemDecoration(Context context, List<ItemBean> itemBeen) {
        mItemBeen = itemBeen;
        mPaint = new Paint();
        mTextRect = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        int titleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(titleFontSize);
        mPaint.setAntiAlias(true);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position == 0) {
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            if (mItemBeen.get(position).getType() != null && !mItemBeen.get(position).getType().
                    equals(mItemBeen.get(position - 1).getType())) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position == 0) {
                drawTitle(c, left, right, child, params, position);
            } else {
                if (mItemBeen.get(position).getType() != null && !mItemBeen.get(position)
                        .getType().equals(mItemBeen.get(position - 1).getType())) {
                    drawTitle(c, left, right, child, params, position);
                }
            }
        }
    }

    private void drawTitle(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);

        /*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(mItemBeen.get(position).getType(), 0, mItemBeen.get(position).getType().length(), mTextRect);
        c.drawText(mItemBeen.get(position).getType(), child.getPaddingLeft() + 70,
                child.getTop() - params.topMargin - (mTitleHeight - mTextRect.height()) / 2, mPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        if (mItemBeen == null || mItemBeen.size() == 0) {
            return;
        }

        String type = mItemBeen.get(position).getType();
        View itemView = parent.findViewHolderForLayoutPosition(position).itemView;

        boolean flag = false;//定义一个flag，Canvas是否位移过的标志
        if (null != type && !type.equals(mItemBeen.get(position + 1).getType())) {//当前第一个可见的Item的tag，不等于其后一个item的tag，说明悬浮的View要切换了
            Log.i(LYTAG, "onDrawOver ----- " + itemView.getTop());//当getTop开始变负，它的绝对值，是第一个可见的Item移出屏幕的距离，
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (itemView.getHeight() + itemView.getTop() + layoutParams.bottomMargin <= mTitleHeight) {//当第一个可见的item在屏幕中还剩的高度小于title区域的高度时，我们也该开始做悬浮Title的“交换动画”
                c.save();//每次绘制前 保存当前Canvas状态，
                flag = true;
                //上滑时，将canvas上移 （y为负数） ,所以后面canvas 画出来的Rect和Text都上移了，有种切换的“动画”感觉
                c.translate(0, itemView.getHeight() + itemView.getTop() + layoutParams.bottomMargin - mTitleHeight);
            }
        }


        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(type, 0, type.length(), mTextRect);
        c.drawText(type, itemView.getPaddingLeft() + 70,
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mTextRect.height() / 2),
                mPaint);

        if (flag){
            c.restore();
        }
    }
}
