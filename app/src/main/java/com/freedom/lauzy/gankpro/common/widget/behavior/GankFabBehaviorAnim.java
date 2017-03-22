package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

/**
 * fab Anim
 * Created by Lauzy on 2017/3/15.
 */

public class GankFabBehaviorAnim {

    private View mFabView;
    public static final LinearOutSlowInInterpolator INTERPOLATOR = new LinearOutSlowInInterpolator();

    public GankFabBehaviorAnim(View fabView) {
        mFabView = fabView;
    }

    public void hideFab() {

        /*ValueAnimator anim = ValueAnimator.ofFloat(mFabView.getY(), mOriginalY + mFabView.getHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFabView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(400);
        anim.start();*/

        ViewCompat.animate(mFabView).scaleX(0f).scaleY(0f)
                .setDuration(300)
                .setInterpolator(INTERPOLATOR)
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
        /*ValueAnimator anim = ValueAnimator.ofFloat(mFabView.getY(), 1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (Float) valueAnimator.getAnimatedValue();
                if (scale == 0) {
                    mFabView.setVisibility(View.VISIBLE);
                }
            }
        });
        anim.setDuration(300);
        anim.start();*/


        ViewCompat.animate(mFabView).scaleX(1f).scaleY(1f)
                .setDuration(300)
                .setInterpolator(INTERPOLATOR)
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
