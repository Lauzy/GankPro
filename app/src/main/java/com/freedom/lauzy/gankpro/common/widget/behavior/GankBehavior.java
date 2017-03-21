package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String LYTAG = GankBehavior.class.getSimpleName();
    private GankBehaviorAnim mGankBehaviorAnim;
    private boolean isHide;
    private boolean isInit = true;
    private boolean canScroll = true;
    //    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();


    public GankBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (isInit) {
            mGankBehaviorAnim = new GankBehaviorAnim(child);
            isInit = false;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }


    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    private int mY;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (canScroll) {
            mY += dy;
            if (Math.abs(dy) > 10 || Math.abs(mY) > 50) {
                if (dy < 0) {
                    if (isHide) {
                        mGankBehaviorAnim.showTitle();
                        isHide = false;
                    }
                } else if (dy > 0) {
                    if (!isHide) {
                        mGankBehaviorAnim.hideTitle();
                        isHide = true;
                    }
                }
                /*if (child.getY() >= 0 || Math.abs(child.getY()) <= child.getHeight()) {
                    mY = 0;
                }*/
                mY = 0;
            }
        }
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        /*if (Math.abs(dyConsumed) > 10) {
            if (dyConsumed < 0) {
                if (isHide) {
                    mGankBehaviorAnim.showTitle();
                    isHide = false;
                }
            } else if (dyConsumed > 0) {
                if (!isHide) {
                    mGankBehaviorAnim.hideTitle();
                    isHide = true;
                }
            }
        }*/
    }

    public void show() {
        if (mGankBehaviorAnim != null) {
            isHide = false;
            mGankBehaviorAnim.showTitle();
        }
    }

    public void hide() {
        if (mGankBehaviorAnim != null) {
            isHide = true;
            mGankBehaviorAnim.hideTitle();
        }
    }

    public static GankBehavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof GankBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ByeBurgerBehavior");
        }
        return (GankBehavior) behavior;
    }

}
