package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/22.
 */

public class GankFabVerticalBehaviorAnim {

    private float mViewY;
    private View mFabView;
    private float mOriginalY;
    private static final LinearOutSlowInInterpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    public GankFabVerticalBehaviorAnim(View parentView, View fabView) {
        mFabView = fabView;
        if (parentView != null && fabView != null){
            mViewY = parentView.getHeight() - fabView.getY();
            mOriginalY = fabView.getY();
        }
    }

    public void hideFab() {

        /*ViewCompat.animate(mFabView)
                .translationY(mViewY)
                .setDuration(400)
                .setInterpolator(INTERPOLATOR)
                .start();*/

        ValueAnimator animator = ValueAnimator.ofFloat(mOriginalY, mOriginalY + mViewY);
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFabView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void showFab() {

        /*ViewCompat.animate(mFabView)
                .translationY(0)
                .setDuration(400)
                .setInterpolator(INTERPOLATOR)
                .start();*/


        ValueAnimator animator = ValueAnimator.ofFloat(mFabView.getY(), mOriginalY);
        animator.setDuration(400);
        animator.setInterpolator(INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFabView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

}
