package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lauzy on 2017/3/21.
 */

public class GankFabVertialBehavior extends CoordinatorLayout.Behavior<View> {

    public GankFabVertialBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        mGankFabBehaviorAnim = new GankFabBehaviorAnim(child);
        return super.layoutDependsOn(parent, child, dependency);
    }

    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        /*if (dy < 0) {
            if (isHide) {
                mGankFabBehaviorAnim.showFab();
                isHide = false;
            }
        } else if (dy > 0) {
            if (!isHide) {
                mGankFabBehaviorAnim.hideFab();
                isHide = true;
            }
        }*/
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }


    public void show() {

    }

    public void hide() {

    }

    public static GankFabBehavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof GankBehavior)) {
            throw new IllegalArgumentException("The view is not associated with GankFabBehavior");
        }
        return (GankFabBehavior) behavior;
    }
}
