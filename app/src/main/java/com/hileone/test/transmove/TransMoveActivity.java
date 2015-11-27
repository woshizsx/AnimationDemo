package com.hileone.test.transmove;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.hileone.test.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionInflater;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TransMoveActivity extends Activity implements ViewPager.OnPageChangeListener {

    RelativeLayout rootView;
    RelativeLayout rootView3_4;
    RelativeLayout rootView2_3;
    RelativeLayout rootView1_2;
    RelativeLayout textView;
    ViewPager viewPager;
    TextView start;

    private AlphaAnimation mShowAnimation;
    private AlphaAnimation mHideAnimation;
    private AlphaAnimation mTextHideAnimation;

    CustomTransition customTransition1_2 = new CustomTransition();
    CustomTransition customTransition2_3 = new CustomTransition();
    CustomTransition customTransition3_4 = new CustomTransition();

    private Scene pageScene1_2;
    private Scene pageScene2_3;
    private Scene pageScene3_4;

    private Scene textScene1;
    private Scene textScene2;
    private Scene textScene3;
    private Scene textScene4;

    private TransitionManager inflaterText;
    private int[] resIdArray3 = new int[] {
            R.id.low_price_item, R.id.real_source_item,
            R.id.keep_privacy_item, R.id.has_deposit_item,
            R.id.is_profession_item, R.id.best_server_item
    };

    private int[] resIdArray4 = new int[] {
            R.id.fast_ask_item, R.id.fase_price_item,
            R.id.fast_sign_item, R.id.fast_take_item
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_move);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
        rootView1_2 = (RelativeLayout) findViewById(R.id.root_view_1_to_2);
        rootView2_3 = (RelativeLayout) findViewById(R.id.root_view_2_to_3);
        rootView3_4 = (RelativeLayout) findViewById(R.id.root_view_3_to_4);
        textView = (RelativeLayout) findViewById(R.id.text_root_view);
        viewPager = (ViewPager) findViewById(R.id.extra_pager);
        start = (TextView) findViewById(R.id.start);

        initSmoothScrollToViewPager(viewPager);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransMoveActivity.this, "Last Page!", Toast.LENGTH_SHORT).show();
            }
        });
        start.setVisibility(View.INVISIBLE);

        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mTextHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mShowAnimation.setDuration(400);
        mHideAnimation.setDuration(400);
        mTextHideAnimation.setDuration(400);
        mShowAnimation.setFillAfter(true);
        mHideAnimation.setFillAfter(true);
        mTextHideAnimation.setFillAfter(true);

        final List<View> viewArray = new ArrayList<View>();
        View view1 = View.inflate(this, R.layout.indictor_page_content_view, null);
        ((ImageView) view1.findViewById(R.id.title)).setImageResource(R.drawable.ic_guide_top);
        view1.findViewById(R.id.description).setVisibility(View.VISIBLE);
        viewArray.add(view1);

        View view2 = View.inflate(this, R.layout.indictor_page_content_view, null);
        ((ImageView) view2.findViewById(R.id.title)).setImageResource(R.drawable.ic_guide_top2);
        view2.findViewById(R.id.description).setVisibility(View.GONE);
        viewArray.add(view2);

        View view3 = View.inflate(this, R.layout.indictor_page_content_view, null);
        ((ImageView) view3.findViewById(R.id.title)).setImageResource(R.drawable.ic_guide_top3);
        view3.findViewById(R.id.description).setVisibility(View.GONE);
        viewArray.add(view3);

        View view4 = View.inflate(this, R.layout.indictor_page_content_view, null);
        ((ImageView) view4.findViewById(R.id.title)).setImageResource(R.drawable.ic_guide_top4);
        view4.findViewById(R.id.description).setVisibility(View.GONE);
        viewArray.add(view4);

        pageScene1_2 = Scene.getSceneForLayout((ViewGroup) findViewById(R.id.root_view_1_to_2), R.layout.scene2, this);
        pageScene2_3 = Scene.getSceneForLayout((ViewGroup)findViewById(R.id.root_view_2_to_3), R.layout.scene3, this);
        pageScene3_4 = Scene.getSceneForLayout((ViewGroup)findViewById(R.id.root_view_3_to_4), R.layout.scene4, this);
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.go(pageScene1_2, customTransition1_2);
                TransitionManager.go(pageScene2_3, customTransition2_3);
                TransitionManager.go(pageScene3_4, customTransition3_4);
            }
        }, 50);

        textScene1 = Scene.getSceneForLayout(textView, R.layout.scene1_text, this);
        textScene2 = Scene.getSceneForLayout(textView, R.layout.scene2_text, this);
        textScene3 = Scene.getSceneForLayout(textView, R.layout.scene3_text, this);
        textScene4 = Scene.getSceneForLayout(textView, R.layout.scene4_text, this);

        inflaterText = TransitionInflater.from(this)
                .inflateTransitionManager(R.transition.scene_text_manager, textView);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewArray.get(position));
                return viewArray.get(position);
            }
        });
        viewPager.addOnPageChangeListener(this);
    }

    //TODO 通过反射的方式改变ViewPager的Scroller滑动时间为原来的2倍
    private void initSmoothScrollToViewPager(ViewPager viewPager){
        try {
            Interpolator sInterpolator = new Interpolator() {
                public float getInterpolation(float t) {
                    t -= 1.0f;
                    return t * t * t * t * t + 1.0f;
                }
            };
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, new Scroller(this, sInterpolator){
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * 2);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //避免一页结束时，细微的闪移
        if(positionOffset == 0 && position>0){
            return;
        }
        switch (position){
            case -1:
            case 0:
                pageScene1_2.getSceneRoot().setVisibility(View.VISIBLE);
                pageScene2_3.getSceneRoot().setVisibility(View.INVISIBLE);
                pageScene3_4.getSceneRoot().setVisibility(View.INVISIBLE);
                customTransition1_2.setCurrentFraction(positionOffset);
                break;
            case 1:
                pageScene2_3.getSceneRoot().setVisibility(View.VISIBLE);
                pageScene1_2.getSceneRoot().setVisibility(View.INVISIBLE);
                pageScene3_4.getSceneRoot().setVisibility(View.INVISIBLE);
                customTransition2_3.setCurrentFraction(positionOffset);
                break;
            case 2:
                pageScene3_4.getSceneRoot().setVisibility(View.VISIBLE);
                pageScene1_2.getSceneRoot().setVisibility(View.INVISIBLE);
                pageScene2_3.getSceneRoot().setVisibility(View.INVISIBLE);
                customTransition3_4.setCurrentFraction(positionOffset);
                break;
            case 3:
                pageScene3_4.getSceneRoot().setVisibility(View.VISIBLE);
                pageScene1_2.getSceneRoot().setVisibility(View.INVISIBLE);
                pageScene2_3.getSceneRoot().setVisibility(View.INVISIBLE);
                customTransition3_4.setCurrentFraction(1);
                break;
        }
    }

    private int lastPosition = 0;
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                inflaterText.transitionTo(textScene1);
                break;
            case 1:
                inflaterText.transitionTo(textScene2);
                break;
            case 2:
                if (lastPosition != 3) {
                    TransitionSet set = new TransitionSet();
                    for (int i = 0; i < resIdArray3.length ;i++) {
                        Fade fade = new Fade(Fade.MODE_IN);
                        fade.addTarget(resIdArray3[i]);
                        set.addTransition(fade);
                    }
                    set.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
                    set.setDuration(400);
                    set.setStartDelay(400);
                    TransitionManager.go(textScene3, set);
                } else {
                    start.startAnimation(mHideAnimation);
                    mShowAnimation.setDuration(400);
                    inflaterText.transitionTo(textScene3);
                    start.setClickable(false);
                }
                break;
            case 3:
                TransitionSet set4 = new TransitionSet();
                for (int i = 0; i < resIdArray4.length ;i++) {
                    Fade fade = new Fade(Fade.MODE_IN);
                    fade.addTarget(resIdArray4[i]);
                    set4.addTransition(fade);
                }
                set4.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
                set4.setDuration(400);
                set4.setStartDelay(400);
                TransitionManager.go(textScene4, set4);
                mShowAnimation.setDuration(1000);
                start.startAnimation(mShowAnimation);
                start.setClickable(true);
                break;
            default:
                break;
        }
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==ViewPager.SCROLL_STATE_DRAGGING){
            textView.startAnimation(mTextHideAnimation);
        }else{
            textView.clearAnimation();
        }
    }

    class CustomTransition extends TransitionController {
        public CustomTransition() {
            super();
            init();
        }
        private void init() {
            setDuration(400);
            setOrdering(ORDERING_TOGETHER);

            addTransition(new Fade(Fade.OUT))
                    .addTransition(new ChangeAlpha())
                    .addTransition(new ChangeBounds())
                    .addTransition(new Fade(Fade.IN));

        }
    }
}
