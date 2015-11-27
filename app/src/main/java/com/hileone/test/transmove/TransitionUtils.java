package com.hileone.test.transmove;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionSet;

import java.lang.reflect.Field;
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
public class TransitionUtils {

    public static float getAnimatorDurationScale() {
        try {
            Method method = ValueAnimator.class.getDeclaredMethod("getDurationScale");
            method.setAccessible(true);
            float scale = ((Float) method.invoke(ValueAnimator.class)).floatValue();
            if(scale<=0){
                method = ValueAnimator.class.getDeclaredMethod("setDurationScale", float.class);
                method.setAccessible(true);
                method.invoke(ValueAnimator.class, 1);
                return 1;
            }
            return scale;
        } catch (Exception ignore) {
        }
        return 1;
    }

    public static List<ValueAnimator> collectValueAnimators(Animator animator){
        ArrayList<ValueAnimator> valueAnimators = new ArrayList<ValueAnimator>();
        for(Animator anim : collectAnimators(animator)){
            if(anim instanceof ValueAnimator){
                valueAnimators.add((ValueAnimator) anim);
            }
        }
        return valueAnimators;
    }

    public static List<Animator> collectAnimators(Animator animator){
        if(animator instanceof AnimatorSet){
            return collectAnimatorsFromSet((AnimatorSet) animator);

        }else{
            ArrayList<Animator> animators = new ArrayList<Animator>();
            animators.add(animator);
            return animators;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static List<Animator> collectAnimatorsFromSet(AnimatorSet animatorSet){
        ArrayList<Animator> animators = new ArrayList<Animator>();
        for(Animator animator : animatorSet.getChildAnimations()){
            animators.addAll(collectAnimators(animator));
        }
        return animators;
    }

    public static List<Animator> collectAnimators(Transition transition){
        if(transition instanceof TransitionSet){
            return collectAnimatorsFromSet((TransitionSet) transition);
        }
        return collectAnimatorsFromTransition(transition);
    }

    private static List<Animator> collectAnimatorsFromSet(TransitionSet transitionSet){
        ArrayList<Animator> animators = new ArrayList<Animator>();
        for(int i=0, count = transitionSet.getTransitionCount(); i<count; i++){
            animators.addAll(collectAnimators(transitionSet.getTransitionAt(i)));
        }
        return animators;
    }

    private static List<Animator> collectAnimatorsFromTransition(Transition transition){
        try {
            Field field = Transition.class.getDeclaredField("mAnimators");
            field.setAccessible(true);
            return (List<Animator>) field.get(transition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Animator>();
    }
}
