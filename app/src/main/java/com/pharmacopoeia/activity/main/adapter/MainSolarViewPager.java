package com.pharmacopoeia.activity.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.bean.reponse.MainViewSolarModelResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 52243 on 2017/8/15.
 */

public class MainSolarViewPager implements ViewPager.OnPageChangeListener {

    private List<MainViewSolarModelResponse> list;
    private ViewPager viewPager;
    private MainActivity context;
    private ImagePagerAdapter ImagePagerAdapter;

    public MainSolarViewPager(ViewPager viewPager, MainActivity context) {
        this.viewPager = viewPager;
        this.context = context;

    }

    public void getListData() {

        if (!OkHttpUtil.isNetworkAvailable(context)) {
            List<MainViewSolarModelResponse> list = LiteOrmDBUtil.getInstance(context).getQueryByWhereALl(MainViewSolarModelResponse.class, "type=?", new Object[]{1});
            if (!list.isEmpty()) {
                context.setViewPagerFragment(list.size());
                setList(list);
            }
            return;
        }

        OkHttpUtil.doGet(context, UrlUtil.SOLAR, OkHttpUtil.getFromMap(context), new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<MainViewSolarModelResponse> list = (ArrayList<MainViewSolarModelResponse>) o;
                if (!list.isEmpty()) {
                    LiteOrmDBUtil.getInstance(context).deleteAll(MainViewSolarModelResponse.class);
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        list.get(i).setType(1);
                    }
                    LiteOrmDBUtil.getInstance(context).insertAll(list);
                    context.setViewPagerFragment(list.size());
                    setList(list);
                }
            }

            @Override
            public void onError(String s) {
            }
        }, MainViewSolarModelResponse.class);
    }

    public void setList(List<MainViewSolarModelResponse> list) {
        this.list = list;
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
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(
                    R.layout.pic_item_fullscreen_layout, view, false);
            final ImageView imageView = (ImageView) imageLayout
                    .findViewById(R.id.image);
            final MainViewSolarModelResponse dy = list.get(position);
            String path = ImageLoaderUtil.getInstance().isUsIP(dy.getFileUrl());

            ImageLoaderUtil.getInstance().loadNomalImage(path, imageView);

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
