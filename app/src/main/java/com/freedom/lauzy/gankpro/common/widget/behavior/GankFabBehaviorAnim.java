package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

/**
 * fab Anim
 * Created by Lauzy on 2017/3/15.
 */

public class GankFabBehaviorAnim {

    private View mFabView;
    public static final LinearOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();

    public GankFabBehaviorAnim(View fabView) {
        mFabView = fabView;
    }

    public void hideFab() {

       /* ValueAnimator va = ValueAnimator.ofFloat(mFabView.getScaleX(), 0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (Float) valueAnimator.getAnimatedValue();
                mFabView.setScaleX(scale);
                mFabView.setScaleY(scale);
                if (scale == 0){
                    mFabView.setVisibility(View.INVISIBLE);
                }
            }
        });
        va.setDuration(300);
        va.start();*/

        ViewCompat.animate(mFabView).scaleX(0f).scaleY(0f)
                .setDuration(300)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
    }

    public void showFab() {
        /*ValueAnimator va = ValueAnimator.ofFloat(mFabView.getScaleX(), 1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (Float) valueAnimator.getAnimatedValue();
                if (scale == 0){
                    mFabView.setVisibility(View.VISIBLE);
                }
                mFabView.setScaleX(scale);
                mFabView.setScaleY(scale);
            }
        });
        va.setDuration(300);
        va.start();*/


        ViewCompat.animate(mFabView).scaleX(1f).scaleY(1f)
                .setDuration(300)
                .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
    }
}
