package com.freedom.lauzy.gankpro.common.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class GankBottomBehavior extends CoordinatorLayout.Behavior<View> {

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

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        if (Math.abs(dy) > 10){

            if (dy < 0) {
            /*if (mGankBehaviorAnim.getCurrentState() == GankBehaviorAnim.STATE_HIDE) {
                mGankBehaviorAnim.showTitle();
                mGankBehaviorAnim.setCurrentState(GankBehaviorAnim.STATE_SHOW);
            }*/
                if (isHide) {
                    mGankBottomBehaviorAnim.showBottom();
                    isHide = false;
                }
            } else if (dy > 0) {
            /*if (mGankBehaviorAnim.getCurrentState() == GankBehaviorAnim.STATE_SHOW){
                mGankBehaviorAnim.hideTitle();
                mGankBehaviorAnim.setCurrentState(GankBehaviorAnim.STATE_HIDE);
            }*/
                if (!isHide) {
                    mGankBottomBehaviorAnim.hideBottom();
                    isHide = true;
                }
            }
        }
    }

    public void show() {
        if (mGankBottomBehaviorAnim !=null){
            isHide = false;
            mGankBottomBehaviorAnim.showBottom();
        }
    }

    public void hide() {
        if (mGankBottomBehaviorAnim !=null){
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
            throw new IllegalArgumentException("The view is not associated with ByeBurgerBehavior");
        }
        return (GankBottomBehavior) behavior;
    }

}
