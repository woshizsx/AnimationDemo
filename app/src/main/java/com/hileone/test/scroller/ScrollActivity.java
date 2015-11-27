package com.hileone.test.scroller;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hileone.test.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 15/10/12
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class ScrollActivity extends FragmentActivity {

    ViewPager mViewPager;
    LinearLayout mHeaderView;
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    boolean mShow;
    private int headerHeight;
    ViewGroup.MarginLayoutParams params;
    Animator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sticky_view);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mHeaderView = (LinearLayout) findViewById(R.id.headerView);

        fragmentList.add(new FirstFragment());
        fragmentList.add(new SecondFragment());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.isEmpty() ? 0 : fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.isEmpty() ? null : fragmentList.get(position);
            }

        });

        mShow = true;
        headerHeight = (int) (getResources().getDisplayMetrics().density * 56 + 0.5f);
        params = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();

        mHeaderView.findViewById(R.id.firstPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        mHeaderView.findViewById(R.id.secondPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
    }

    public void componentsScroll(int direction, int deltY) {
        int margin = params.topMargin;
        if (direction == 1) { // 表示向上滑动
            if (margin >= -headerHeight) {
                margin -= deltY;
                margin = Math.max(-headerHeight, margin);
            }
        } else { // 表示向下滑动
            if (margin <= 0) {
                margin -= deltY;
                margin = Math.min(0, margin);
            }
        }
        if (margin <= 0 && margin >= -headerHeight) {
            params.topMargin = margin;
            mViewPager.requestLayout();
        }

        int transitionY = (int) ViewHelper.getTranslationY(mHeaderView) - deltY;
        transitionY = Math.min(0, transitionY);
        transitionY = Math.max(-headerHeight, transitionY);
        if (transitionY <= 0 && transitionY >= -headerHeight) {
            ViewHelper.setTranslationY(mHeaderView, transitionY);
        }
    }

    public void headViewSlid(int direction) {
        if (direction == 1) {
            if (mShow) {
                toolbarAnim(1);
                mShow = !mShow;
            }
        } else if (direction == 0) {
            if (!mShow) {
                toolbarAnim(0);
                mShow = !mShow;
            }
        }
    }

    private void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator = ObjectAnimator.ofFloat(mHeaderView,
                "translationY",
                mHeaderView.getTranslationY(),
                flag == 0 ? 0 : -mHeaderView.getHeight());
        mAnimator.start();
    }

}
