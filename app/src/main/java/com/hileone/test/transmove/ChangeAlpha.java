package com.hileone.test.transmove;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionValues;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 15/10/12
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class ChangeAlpha extends Transition {
    private static final String PROPNAME_ALPHA = "android:alpha:alpha";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ALPHA, transitionValues.view.getAlpha());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ALPHA, transitionValues.view.getAlpha());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View view = endValues.view;
        float startAlpha = (Float) startValues.values.get(PROPNAME_ALPHA);
        float endAlpha = (Float) endValues.values.get(PROPNAME_ALPHA);
        if (startAlpha != endAlpha) {
            view.setAlpha(startAlpha);
            return ObjectAnimator.ofFloat(view, "alpha", startAlpha, endAlpha);
        }
        return null;
    }
}
