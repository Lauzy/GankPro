package com.freedom.lauzy.gankpro.common.widget;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class BehaviorAnim {

    public static final int STATE_HIDE = 10001;
    public static final int STATE_SHOW = 10002;
    public int mCurrentState = STATE_SHOW;
    private View mHeadView;

    public BehaviorAnim(View headView) {
        mHeadView = headView;
    }

    public void hideTitle() {
        ValueAnimator va = ValueAnimator.ofFloat(mHeadView.getY(), -mHeadView.getHeight());
        va.setDuration(300);
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
        va.setDuration(300);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mHeadView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }


    public int getCurrentState() {
        return mCurrentState;
    }

    public void setCurrentState(int currentState) {
        mCurrentState = currentState;
    }
}
