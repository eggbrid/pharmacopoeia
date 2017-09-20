package com.pharmacopoeia.util;

import android.content.Context;
import android.icu.text.MessageFormat;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;

import com.pharmacopoeia.view.FixedSpeedScroller;

import java.lang.reflect.Field;

/**
 * Created by 52243 on 2017/7/31.
 */

public class Utils {

    private static FixedSpeedScroller mScroller = null;

    /**
     * 设置ViewPager的滑动时间
     *
     * @param context
     * @param viewpager      ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    public static void controlViewPagerSpeed(Context context, ViewPager viewpager, int DurationSwitch) {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(context,
                    new AccelerateInterpolator());
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 时间截取
     *
     * @param datetime
     * @param splite
     * @return
     */
    public static int[] getYMDArray(String datetime, String splite) {
        int date[] = {0, 0, 0, 0, 0};


        if (datetime != null && datetime.length() > 0) {
            String[] dates = datetime.split(splite);

            int position = 0;
            for (String temp : dates) {
                date[position] = Integer.valueOf(temp);
                position++;
            }
        }
        return date;
    }

}
