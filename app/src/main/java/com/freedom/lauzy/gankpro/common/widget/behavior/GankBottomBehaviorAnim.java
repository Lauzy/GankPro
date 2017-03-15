package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBottomBehaviorAnim {

    private View mBottomView;
    private float mOriginalY;

    public GankBottomBehaviorAnim(View bottomView) {
        mBottomView = bottomView;
        mOriginalY = mBottomView.getY();
    }

    public void hideBottom() {
//        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mBottomView.getY() + mBottomView.getHeight());
        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY + mBottomView.getHeight());
        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    public void showBottom() {
//        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mBottomView.getY() - mBottomView.getHeight());
        ValueAnimator va = ValueAnimator.ofFloat(mBottomView.getY(), mOriginalY);
        va.setDuration(400);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBottomView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

}
