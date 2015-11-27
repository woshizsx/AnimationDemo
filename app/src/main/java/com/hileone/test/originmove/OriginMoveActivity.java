package com.hileone.test.originmove;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hileone.test.R;
import com.hileone.test.transmove.ChangeAlpha;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Scene;
import com.transitionseverywhere.TransitionInflater;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;
import java.util.List;

public class OriginMoveActivity extends Activity implements ViewPager.OnPageChangeListener {

    RelativeLayout rootView;
    RelativeLayout textRoot;
    ViewPager viewPager;
    TextView startBtn;

    private AlphaAnimation mShowAnimation;
    private AlphaAnimation mHideAnimation;
    private AlphaAnimation mTextHideAnimation;

    private Scene pageScene1;
    private Scene pageScene2;
    private Scene pageScene3;
    private Scene pageScene4;

    private Scene textScene1;
    private Scene textScene2;
    private Scene textScene3;
    private Scene textScene4;

    CustomTransition transition1 = new CustomTransition();
    CustomTransition transition2 = new CustomTransition();
    CustomTransition transition3 = new CustomTransition();
    CustomTransition transition4 = new CustomTransition();

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
        setContentView(R.layout.activity_origin_move);
        rootView = (RelativeLayout) findViewById(R.id.root_view);
        textRoot = (RelativeLayout) findViewById(R.id.text_root_view);
        viewPager = (ViewPager) findViewById(R.id.extra_pager);
        startBtn = (TextView) findViewById(R.id.start);

        startBtn.setVisibility(View.INVISIBLE);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OriginMoveActivity.this, "Last Page!", Toast.LENGTH_SHORT).show();
            }
        });

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

        pageScene1 = Scene.getSceneForLayout(rootView, R.layout.scene1, this);
        pageScene2 = Scene.getSceneForLayout(rootView, R.layout.scene2, this);
        pageScene3 = Scene.getSceneForLayout(rootView, R.layout.scene3, this);
        pageScene4 = Scene.getSceneForLayout(rootView, R.layout.scene4, this);

        textScene1 = Scene.getSceneForLayout(textRoot, R.layout.scene1_text, this);
        textScene2 = Scene.getSceneForLayout(textRoot, R.layout.scene2_text, this);
        textScene3 = Scene.getSceneForLayout(textRoot, R.layout.scene3_text, this);
        textScene4 = Scene.getSceneForLayout(textRoot, R.layout.scene4_text, this);

        inflaterText = TransitionInflater.from(this)
                .inflateTransitionManager(R.transition.scene_text_manager, textRoot);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private int lastPosition = 0;
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                TransitionManager.go(pageScene1, transition1);
                inflaterText.transitionTo(textScene1);
                break;
            case 1:
                TransitionManager.go(pageScene2, transition2);
                inflaterText.transitionTo(textScene2);
                break;
            case 2:
                TransitionManager.go(pageScene3, transition3);
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
                    startBtn.startAnimation(mHideAnimation);
                    mShowAnimation.setDuration(400);
                    inflaterText.transitionTo(textScene3);
                    startBtn.setClickable(false);
                }
                break;
            case 3:
                TransitionManager.go(pageScene4, transition4);
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
                startBtn.startAnimation(mShowAnimation);
                startBtn.setClickable(true);
                break;
            default:
                break;
        }
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==ViewPager.SCROLL_STATE_DRAGGING){
            textRoot.startAnimation(mTextHideAnimation);
        }else{
            textRoot.clearAnimation();
        }
    }

    class CustomTransition extends TransitionSet {
        public CustomTransition() {
            super();
            init();
        }
        private void init() {
            setDuration(400);
            setOrdering(ORDERING_TOGETHER);

            addTransition(new Fade(Fade.OUT)).
                    addTransition(new ChangeAlpha()).
                    addTransition(new ChangeBounds()).
                    addTransition(new Fade(Fade.IN));

        }
    }
}
