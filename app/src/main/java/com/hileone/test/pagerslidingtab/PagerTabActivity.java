package com.hileone.test.pagerslidingtab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hileone.test.R;

import java.util.ArrayList;
import java.util.List;

public class PagerTabActivity extends Activity implements ViewPager.OnPageChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_tab);

        final List<TextView> textViewList = new ArrayList<TextView>();
        final List<String> titleList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setText("Page " + (i + 1) + (i + 1) + (i + 1));
            textViewList.add(textView);
            titleList.add("Page" + (i + 1));
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.extra_pager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return textViewList != null ? textViewList.size() : 0;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return textViewList.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return false;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        });

        PagerSlidingTab slidingTab = (PagerSlidingTab) findViewById(R.id.slidingTab);
        slidingTab.setViewPager(viewPager);
        slidingTab.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
