package com.freedom.lauzy.gankpro.function.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.freedom.lauzy.gankpro.function.entity.ItemBean;

import java.util.List;

/**
 * Created by Lauzy on 2017/3/15.
 */

public class DailyItemDecoration extends RecyclerView.ItemDecoration {

    private static final int COLOR_TITLE_BG = Color.parseColor("#FFFFFF");
    private static final int COLOR_TITLE_FONT = Color.parseColor("#000000");
    private final Paint mPaint;
    private final Rect mTextRect;
    private int mTitleFontSize;
    private int mTitleHeight;
    private List<ItemBean> mItemBeen;

    public DailyItemDecoration(Context context, List<ItemBean> itemBeen) {
        mItemBeen = itemBeen;
        mPaint = new Paint();
        mTextRect = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
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
    }
}
