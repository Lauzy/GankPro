package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBottomBehaviorAnim {

    private View mBottomView;
    private float mOriginalY;
    private static final LinearOutSlowInInterpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    public GankBottomBehaviorAnim(View bottomView) {
        mBottomView = bottomView;
        mOriginalY = mBottomView.getY();
    }

    public void hideBottom() {
//        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mBottomView.getY() + mBottomView.getHeight());
        ValueAnimator animator = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY + mBottomView.getHeight());
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void showBottom() {
//        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mBottomView.getY() - mBottomView.getHeight());//Y值会发生变化，采用全局OriginalY
        ValueAnimator animator = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY);
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

}
