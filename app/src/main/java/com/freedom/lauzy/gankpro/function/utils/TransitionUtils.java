package com.freedom.lauzy.gankpro.function.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.freedom.lauzy.gankpro.common.utils.TransitionHelper;

import okhttp3.Interceptor;

/**
 * Created by Lauzy on 2017/3/20.
 */

public class TransitionUtils {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void transitionTo(Activity context, Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(context, false);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                pairs);
        context.startActivity(i, transitionActivityOptions.toBundle());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void transitionTo(Activity activity, Intent intent, Pair<View, String>[] pairs) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(intent, optionsCompat.toBundle());
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition buildEnterTransition() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(500);
        // 修饰动画，定义动画的变化率
        enterTransition.setInterpolator(new BounceInterpolator());
        return enterTransition;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Visibility buildReturnTransition() {
        Explode exitTransition = new Explode();
        exitTransition.setDuration(200);
        // 修饰动画，定义动画的变化率
        exitTransition.setInterpolator(new AccelerateInterpolator());
        return exitTransition;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Slide buildSlideEnterTrans(Interpolator interpolator){
        Slide enterSlide = new Slide(Gravity.RIGHT);
        enterSlide.setDuration(400);
        enterSlide.setInterpolator(interpolator);
        return enterSlide;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Slide buildSlideExitTrans(Interpolator interpolator){
        Slide exitSlide = new Slide(Gravity.LEFT);
        exitSlide.setDuration(200);
        exitSlide.setInterpolator(interpolator);
        return exitSlide;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition buildExplodeEnterAnim(Interpolator interpolator) {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(500);
        // 修饰动画，定义动画的变化率
        enterTransition.setInterpolator(interpolator);
        return enterTransition;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition buildExplodeExitAnim(Interpolator interpolator){
        Explode exitTranstion = new Explode();
        exitTranstion.setDuration(200);
        exitTranstion.setInterpolator(interpolator);
        return exitTranstion;
    }
}
