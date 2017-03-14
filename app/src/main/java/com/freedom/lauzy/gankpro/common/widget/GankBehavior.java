package com.freedom.lauzy.gankpro.common.widget;

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
    private BehaviorAnim mBehaviorAnim;
    private boolean isHide;
    private boolean canInit = true;
    //    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();


    public GankBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (canInit) {
            mBehaviorAnim = new BehaviorAnim(child);
            canInit = false;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    //判断垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (Math.abs(dy) > 10){

            if (dy < 0) {
            /*if (mBehaviorAnim.getCurrentState() == BehaviorAnim.STATE_HIDE) {
                mBehaviorAnim.showTitle();
                mBehaviorAnim.setCurrentState(BehaviorAnim.STATE_SHOW);
            }*/
                if (isHide) {
                    mBehaviorAnim.showTitle();
                    isHide = false;
                }
            } else if (dy > 0) {
            /*if (mBehaviorAnim.getCurrentState() == BehaviorAnim.STATE_SHOW){
                mBehaviorAnim.hideTitle();
                mBehaviorAnim.setCurrentState(BehaviorAnim.STATE_HIDE);
            }*/
                if (!isHide) {
                    mBehaviorAnim.hideTitle();
                    isHide = true;
                }
            }
        }
    }

    public void show() {
        if (mBehaviorAnim !=null){
            isHide = false;
            mBehaviorAnim.showTitle();
        }
    }

    public void hide() {
        if (mBehaviorAnim !=null){
            isHide = true;
            mBehaviorAnim.hideTitle();
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
