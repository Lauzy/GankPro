package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBehaviorAnim {

    private View mHeadView;
    private static final LinearOutSlowInInterpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    public GankBehaviorAnim(View headView) {
        mHeadView = headView;
    }

    public void hideTitle() {
        ValueAnimator animator = ValueAnimator.ofFloat(mHeadView.getY(), -mHeadView.getHeight());
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mHeadView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void showTitle() {

        ValueAnimator animator = ValueAnimator.ofFloat(mHeadView.getY(), 0);
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mHeadView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

}
