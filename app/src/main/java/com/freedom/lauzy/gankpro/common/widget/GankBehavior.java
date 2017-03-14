package com.freedom.lauzy.gankpro.common.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String LYTAG = GankBehavior.class.getSimpleName();
    private BehaviorAnim mBehaviorAnim;
    //    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();

    public GankBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        mBehaviorAnim = new BehaviorAnim(child);
        return super.layoutDependsOn(parent, child, dependency);
    }

    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            if (mBehaviorAnim.getCurrentState() == BehaviorAnim.STATE_HIDE) {
                mBehaviorAnim.showTitle();
                mBehaviorAnim.setCurrentState(BehaviorAnim.STATE_SHOW);
            }
        } else if (dy > 0) {
            if (mBehaviorAnim.getCurrentState() == BehaviorAnim.STATE_SHOW){
                mBehaviorAnim.hideTitle();
                mBehaviorAnim.setCurrentState(BehaviorAnim.STATE_HIDE);
            }
        }
    }
}
