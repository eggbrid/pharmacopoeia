package com.pharmacopoeia.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.pharmacopoeia.interfaces.views.ViewPageSelectListener;

import java.util.HashMap;
import java.util.List;

/**
 * Tab妞ょ敻娼伴幍瀣◢濠婃垵濮╅崚鍥ㄥ床娴犮儱寮烽崝銊ф暰閺佸牊鐏�
 *
 * @author D.Winter
 */
public class CrmZanViewPage {

    public ViewPager mPager;//
    public List<View> listViews; //
    public HashMap<View, Boolean> loadTag;
    private ViewPageSelectListener viewPageSelectListener;

    public void clear() {

        if (mPager != null) {
            mPager.clearFocus();
            mPager = null;
        }
        if (listViews != null) {
            listViews.clear();
            listViews = null;
        }
        if (loadTag != null) {
            loadTag.clear();
            loadTag = null;
        }
    }

    /**
     * 閸掓繂顬婇崠鏈ewPager
     */
    public void InitViewPager(ViewPager mPager, List<View> listView, List<View> listButton,
                              ViewPageSelectListener viewPageSelectListener) {

        this.viewPageSelectListener=viewPageSelectListener;
        if (listView == null) {
            throw new IllegalArgumentException("listView 参数不能为空");
        }
        if (listButton == null) {
            throw new IllegalArgumentException("listButton 参数不能为空");
        }
        loadTag = new HashMap<View, Boolean>();
        this.mPager = mPager;
        listViews = listView;
        for (int i = 0; i < listView.size(); i++) {
            loadTag.put(listView.get(i), false);
        }
        if (mPager != null && listViews != null) {
            mPager.setAdapter(new MyPagerAdapter(listViews));
            mPager.setCurrentItem(0);
            mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        }

        for (int i = 0; i < listButton.size(); i++) {
            listButton.get(i).setOnClickListener(new MyOnClickListener(i));
        }
    }

    /**
     * ViewPager闁倿鍘ら崳锟�
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));

        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            super.setPrimaryItem(container, position, object);

            if (listViews != null && loadTag != null) {
                if (loadTag.get(listViews.get(position)) == false) {
                    loadTag.put(listViews.get(position), true);
                    if(viewPageSelectListener!=null){
                        viewPageSelectListener.setPrimaryItem(position);
                    }
                }
            }
        }
    }

    /**
     * 婢跺瓨鐖ｉ悙鐟板毊閻╂垵鎯�
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {

            mPager.setCurrentItem(index);
        }
    }

    ;

    /**
     * 妞ら潧宕遍崚鍥ㄥ床閻╂垵鎯�
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        public void onPageSelected(int arg0) {

            if(viewPageSelectListener!=null){
                viewPageSelectListener.select(arg0);
            }

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    }
}