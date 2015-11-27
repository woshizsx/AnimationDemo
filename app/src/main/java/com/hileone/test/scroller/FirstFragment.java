package com.hileone.test.scroller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The creator is Leone && E-mail: butleone@163.com
 *
 * @author Leone
 * @date 15/10/12
 * @description Edit it! Change it! Beat it! Whatever, just do it!
 */
public class FirstFragment extends Fragment {

    private float mFirstY;
    private float mFirstX;
    private float mCurrentY;
    private float mCurrentX;
    private float mTouchSlop;
    private float mLastY;

    /**
     * FirstFragment
     */
    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        listView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(6);
        listView.setFadingEdgeLength(0);
        listView.setCacheColorHint(0);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new TextView(getActivity());
                    convertView.setBackgroundColor(getResources().getColor(android.R.color.white));
                    convertView.setLayoutParams(new AbsListView.LayoutParams(-1, 160));
                    ((TextView) convertView).setGravity(Gravity.CENTER);
                }
                ((TextView) convertView).setText("This is position: " + (position + 1));
                return convertView;
            }
        });


        mTouchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        mFirstX = event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        mCurrentX = event.getX();
                        if (Math.abs(mFirstY - mCurrentY) > Math.abs(mFirstX - mCurrentX)) {
                            ((ScrollActivity) getActivity()).componentsScroll(
                                    mCurrentY - mFirstY >= mTouchSlop ? 0 : 1,
                                    (int) (mLastY - mCurrentY));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                mLastY = event.getY();
                return false;
            }
        });

        return listView;
    }
}
