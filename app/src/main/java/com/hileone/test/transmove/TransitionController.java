package com.hileone.test.transmove;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;

import com.transitionseverywhere.TransitionSet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 15/10/12
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TransitionController extends TransitionSet {
    List<ValueAnimator> valueAnimators = new ArrayList<ValueAnimator>();
    public TransitionController() {
    }

    private void reflectSetupStartEndListeners() {
        try {
            Method method = TransitionSet.class.getDeclaredMethod("setupStartEndListeners");
            method.setAccessible(true);
            method.invoke(this);
        } catch (Exception ignore) {
        }
    }
    @Override
    protected void runAnimators() {
        reflectSetupStartEndListeners();
        valueAnimators.clear();
        List<Animator> animators = TransitionUtils.collectAnimators(TransitionController.this);
        for (Animator animator : animators) {
            valueAnimators.addAll(TransitionUtils.collectValueAnimators(animator));
        }

        long duration = (long) (getDuration()/TransitionUtils.getAnimatorDurationScale());
        long startDelay = getStartDelay();
        TimeInterpolator interpolator = getInterpolator();
        for (ValueAnimator animator : valueAnimators) {
            if (duration >= 0) {
                animator.setDuration(duration);
            }
            if (startDelay >= 0) {
                animator.setStartDelay(startDelay + animator.getStartDelay());
            }
            if (interpolator != null) {
                animator.setInterpolator(interpolator);
            }

        }
    }
    public void setCurrentFraction(float fraction){
        setCurrentPlayTime((long) (fraction * getDuration()));
    }
    public void setCurrentPlayTime(long playTime){
        for (ValueAnimator valueAnimator : valueAnimators) {
            valueAnimator.setCurrentPlayTime(playTime);
        }
    }


}
