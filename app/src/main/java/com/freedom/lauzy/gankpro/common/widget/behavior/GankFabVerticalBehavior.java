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

public class GankFabVerticalBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean isHide;
    private GankFabVerticalBehaviorAnim mFabVerticalBehaviorAnim;
    private boolean canScroll = true;
    private boolean isInit = true;//防止new Anim导致的parent 和child坐标变化

    public GankFabVerticalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        mFabVerticalBehaviorAnim = new GankFabVerticalBehaviorAnim(coordinatorLayout,child);
        if (isInit) {
            mFabVerticalBehaviorAnim = new GankFabVerticalBehaviorAnim(coordinatorLayout, child);
            isInit = false;
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    private int mY;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
       /* if (dy < 0) {
            if (isHide) {
                mFabVerticalBehaviorAnim.showFab();
                isHide = false;
            }
        } else if (dy > 0) {
            if (!isHide) {
                mFabVerticalBehaviorAnim.hideFab();
                isHide = true;
            }
        }*/

        if (canScroll) {
            mY += dy;//总滑动距离超过指定值后，即便当前滑动距离<10，也实现滑动
            if (Math.abs(dy) > 10 || Math.abs(mY) > 50) {
                if (dy < 0) {
                    if (isHide) {
                        mFabVerticalBehaviorAnim.showFab();
                        isHide = false;
                    }
                } else if (dy > 0) {
                    if (!isHide) {
                        mFabVerticalBehaviorAnim.hideFab();
                        isHide = true;
                    }
                }
                mY = 0;////总滑动距离重置0
            }
        }
    }

    /*private void hide(View child) {
        ViewPropertyAnimator animator = child.animate().translationY(mViewY).setInterpolator(INTERPOLATOR).setDuration(400);
        animator.start();
    }

    private void show(final View child) {
        ViewPropertyAnimator animator = child.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(400);
        animator.start();
    }*/

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }


    public void show() {
        if (mFabVerticalBehaviorAnim != null) {
            mFabVerticalBehaviorAnim.showFab();
        }
    }

    public void hide() {
        if (mFabVerticalBehaviorAnim != null) {
            mFabVerticalBehaviorAnim.hideFab();
        }
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
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
