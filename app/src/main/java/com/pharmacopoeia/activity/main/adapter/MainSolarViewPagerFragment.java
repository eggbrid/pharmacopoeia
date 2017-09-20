package com.pharmacopoeia.activity.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.reponse.MainViewSolarModelResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 52243 on 2017/8/15.
 */

public class MainSolarViewPagerFragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private Activity context;
    private ImagePagerAdapter ImagePagerAdapter;
    private int count;

    public MainSolarViewPagerFragment(ViewPager viewPager, Activity context) {
        this.viewPager = viewPager;
        this.context = context;

    }


    public void setList(int count) {
        this.count = count;
        initView();
    }

    private void initView() {
        if (ImagePagerAdapter == null) {
            ImagePagerAdapter = new ImagePagerAdapter(context);
            viewPager.setAdapter(ImagePagerAdapter);
            viewPager.setOnPageChangeListener(this);
        } else {
            ImagePagerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * viewpage 适配照片
     *
     * @author Shire Yin
     */
    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        ImagePagerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(
                    R.layout.pic_item_fullscreen_layout_fragment, view, false);

            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int paramInt) {

    }

}
