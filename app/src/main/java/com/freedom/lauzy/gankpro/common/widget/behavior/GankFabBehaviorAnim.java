package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * fab Anim
 * Created by Lauzy on 2017/3/15.
 */

public class GankFabBehaviorAnim {

    private View mFabView;

    public GankFabBehaviorAnim(View fabView) {
        mFabView = fabView;
    }

    public void hideFab() {

        ValueAnimator va = ValueAnimator.ofFloat(mFabView.getScaleX(), 0);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (Float) valueAnimator.getAnimatedValue();
                mFabView.setScaleX(scale);
                mFabView.setScaleY(scale);
            }
        });
        va.setDuration(300);
        va.start();
    }

    public void showFab() {

        ValueAnimator va = ValueAnimator.ofFloat(mFabView.getScaleX(), 1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (Float) valueAnimator.getAnimatedValue();
                mFabView.setScaleX(scale);
                mFabView.setScaleY(scale);
            }
        });
        va.setDuration(300);
        va.start();

    }
}
