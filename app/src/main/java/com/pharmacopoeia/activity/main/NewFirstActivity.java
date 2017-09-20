package com.pharmacopoeia.activity.main;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.adapter.MyPagerAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.Setting;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/7/13.
 */

public class NewFirstActivity extends CommentActivity {
    protected ViewPager guidepagers;
    protected View view;
    protected Button button;
    protected LinearLayout lin;
    private Integer[] ints = new Integer[]{R.drawable.welcome, R.drawable.welcome, R.drawable.welcome, R.drawable.welcome};
    private List<View> pageViews;
    private ArrayList<View> dots;
    private int oldPosition = 0;// 记录上一次点的位置

    @Override
    public int setContentView() {
        return R.layout.main_newfirst_activity;
    }

    @Override
    public void initView() throws Exception {
        guidepagers = (ViewPager) findViewById(R.id.guidepagers);
        view = (View) findViewById(R.id.view);
        button = (Button) findViewById(R.id.button);
        lin = (LinearLayout) findViewById(R.id.lin);
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<>();
        dots = new ArrayList<View>();

        for (int i = 0; i < ints.length; i++) {
            View view = inflater.inflate(R.layout.main_newfirst_image_view, null);
            View view2 = view.findViewById(R.id.dot_1);
            if (i == 0) {
                view2.setBackgroundResource(R.drawable.xuanzhongyuan);
            }
            lin.addView(view);
            dots.add(view2);
            View view1 = inflater.inflate(R.layout.main_newfirst_viewpage_view, null);
            ImageView image = (ImageView) view1.findViewById(R.id.image);
            image.setBackgroundResource(ints[i]);
            pageViews.add(view1);
        }
        guidepagers.setAdapter(new MyPagerAdapter(pageViews));
        guidepagers.setOnPageChangeListener(new GuidePageChangeListener());
    }

    public void tiyan(View view) {
        Setting setting=  DBUtil.getInstance(this).getSetting();
        setting.setIsfrist(1);
        LiteOrmDBUtil.getInstance(this).set(setting);
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        startActivity(intent);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        finish();
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            dots.get(oldPosition).setBackgroundResource(R.drawable.weixuanzhongyuan);
            dots.get(position).setBackgroundResource(R.drawable.xuanzhongyuan);
            oldPosition = position;

            if (position == (ints.length - 1)) {
                lin.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
            } else {
                lin.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            }
        }
    }
}
