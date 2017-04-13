package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBottomBehavior extends CoordinatorLayout.Behavior<View> implements Serializable {

    private static final String LYTAG = GankBottomBehavior.class.getSimpleName();
    private GankBottomBehaviorAnim mGankBottomBehaviorAnim;
    private boolean isHide;
    private boolean isInit = true;
    //    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();


    public GankBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return true;
//        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (isInit) {
            mGankBottomBehaviorAnim = new GankBottomBehaviorAnim(child);
            isInit = false;
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    private int mY;

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (canScroll){
            mY += dy;//总滑动距离超过指定值后，即便当前滑动距离<10，也实现滑动
            if (Math.abs(dy) > 10 || Math.abs(mY) > 50) {
                if (dy < 0) {
                    if (isHide) {
                        mGankBottomBehaviorAnim.showBottom();
                        isHide = false;
                    }
                } else if (dy > 0) {
                    if (!isHide) {
                        mGankBottomBehaviorAnim.hideBottom();
                        isHide = true;
                    }
                }
                mY = 0;////总滑动距离重置0
            }
        }
    }

    public void show() {
        if (mGankBottomBehaviorAnim != null) {
            isHide = false;
            mGankBottomBehaviorAnim.showBottom();
        }
    }

    public void hide() {
        if (mGankBottomBehaviorAnim != null) {
            isHide = true;
            mGankBottomBehaviorAnim.hideBottom();
        }
    }

    public static GankBottomBehavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof GankBottomBehavior)) {
            throw new IllegalArgumentException("The view is not associated with GankBottomBehavior");
        }
        return (GankBottomBehavior) behavior;
    }

    private boolean canScroll = true;
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}
