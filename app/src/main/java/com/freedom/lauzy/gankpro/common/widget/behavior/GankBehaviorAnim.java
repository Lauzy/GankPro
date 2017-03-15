package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBehaviorAnim {

    private View mHeadView;

    public GankBehaviorAnim(View headView) {
        mHeadView = headView;
    }

    public void hideTitle() {
        ValueAnimator va = ValueAnimator.ofFloat(mHeadView.getY(), -mHeadView.getHeight());
        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mHeadView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    public void showTitle() {

        ValueAnimator va = ValueAnimator.ofFloat(mHeadView.getY(), 0);
        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mHeadView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

}
