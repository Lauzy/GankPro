package com.freedom.lauzy.gankpro.function.view;


import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ImageOnTouchListener implements View.OnTouchListener {

    private int mOriginalX;
    private int mOriginalY;
    private int mMotionX;
    private int mMotionY;
    private ImageEventListener mImageEventListener;
    private ViewGroup mContentLayout;
    private int mCurAlpha = 0;

    public ImageOnTouchListener(ViewGroup contentLayout, ImageEventListener eventListener) {
        mImageEventListener = eventListener;
        mContentLayout = contentLayout;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
//        layoutParams.removeRule(RelativeLayout.CENTER_IN_PARENT);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mOriginalY = (int) event.getRawY();
                mOriginalX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                mMotionY = (int) (event.getRawY() - mOriginalY);
                mMotionX = (int) (event.getRawX() - mOriginalX);
//                float ratioX = mMotionX <= v.getWidth() ? mMotionX * 1.0f / v.getWidth() : 1;
                float ratio = Math.abs(mMotionY) * 1.0f / v.getHeight();
                float ratioY = Math.abs(mMotionY) <= v.getHeight() ? (ratio <= 0.5f ? ratio : 0.5f) : 0.5f;
//                mCurRatio = 1 - ratioY;
                v.setScaleX(1 - ratioY);
                v.setScaleY(1 - ratioY);
                layoutParams.topMargin = mMotionY / 2;
                layoutParams.leftMargin = mMotionX / 2;
                layoutParams.bottomMargin = -mMotionY / 2;
                layoutParams.rightMargin = -mMotionX / 2;
//                layoutParams.setMargins(mMotionX / 2, mMotionY / 2, -mMotionX / 2, mMotionY / 2);
                v.setLayoutParams(layoutParams);

                v.requestLayout();
                if (Math.abs(mMotionY) > 500) {
                    mContentLayout.getBackground().setAlpha(100);
                    mCurAlpha = 100;
                } else {
                    float ratioAlpha = (Math.abs(mMotionY) / 500.0f) * (255 - 100);
                    mContentLayout.getBackground().setAlpha(255 - (int) ratioAlpha);
                    mCurAlpha = 255 - (int) ratioAlpha;
                }
                mContentLayout.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (Math.abs(mMotionY) > 400) {
                    mImageEventListener.onActionBack();
                } else {
                    //无动画
                    /*v.setScaleY(1);
                    v.setScaleX(1);
                    layoutParams.topMargin = 0;
                    layoutParams.bottomMargin = 0;
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 0;
                    mContentLayout.getBackground().setAlpha(255);*/
                    setScaleAnim(v);
                    setMarginAnim(v, layoutParams, Direction.LEFT);
                    setMarginAnim(v, layoutParams, Direction.RIGHT);
                    setMarginAnim(v, layoutParams, Direction.TOP);
                    setMarginAnim(v, layoutParams, Direction.BOTTOM);
                    v.requestLayout();
                }
                ValueAnimator animator = ValueAnimator.ofInt(mCurAlpha, 255);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mContentLayout.getBackground().setAlpha((Integer) animation.getAnimatedValue());
                    }
                });
                animator.setDuration(300).start();
                break;
        }
        return true;
    }

    private void setScaleAnim(View view) {
        view.animate().scaleX(1).scaleY(1).setDuration(300).start();
    }

    private enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private void setMarginAnim(final View view, final FrameLayout.LayoutParams layoutParams, final Direction direction) {
        int originalMargin = 0;
        switch (direction) {
            case LEFT:
                originalMargin = layoutParams.leftMargin;
                break;
            case RIGHT:
                originalMargin = layoutParams.rightMargin;
                break;
            case TOP:
                originalMargin = layoutParams.topMargin;
                break;
            case BOTTOM:
                originalMargin = layoutParams.bottomMargin;
                break;
        }
        ValueAnimator animator = ValueAnimator.ofInt(originalMargin, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                switch (direction) {
                    case LEFT:
                        layoutParams.leftMargin = (int) animation.getAnimatedValue();
                        break;
                    case RIGHT:
                        layoutParams.rightMargin = (int) animation.getAnimatedValue();
                        break;
                    case TOP:
                        layoutParams.topMargin = (int) animation.getAnimatedValue();
                        break;
                    case BOTTOM:
                        layoutParams.bottomMargin = (int) animation.getAnimatedValue();
                        break;
                }
                view.requestLayout();
            }
        });
        animator.setDuration(300).start();
    }

    public interface ImageEventListener {
        void onActionBack();
    }
}
