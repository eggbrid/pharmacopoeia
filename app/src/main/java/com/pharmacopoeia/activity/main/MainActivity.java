package com.pharmacopoeia.activity.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.CollectionFragment;
import com.pharmacopoeia.activity.health.HealthFragment;
import com.pharmacopoeia.activity.main.adapter.MainFragmentPageAdapter;
import com.pharmacopoeia.activity.main.adapter.MainSolarViewPager;
import com.pharmacopoeia.activity.me.MeFragment;
import com.pharmacopoeia.activity.recuperate.RecuperateFragment;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.MainViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends CommentActivity implements View.OnClickListener {
    protected MainViewPager mainViewpager;
    protected Button btnYangsheng;
    protected ImageView unreadYangsheng;
    protected Button btnDiancang;
    protected ImageView unreadDiancang;
    protected Button btnTiaoyang;
    protected ImageView unreadTiaoyang;
    protected Button btnMe;
    protected ImageView unreadMe;
    private ArrayList<Fragment> fragemnts = new ArrayList<>();
    private MainFragmentPageAdapter adapter;
    private HealthFragment healthFragment;//养生
    private CollectionFragment collectionFragment;//典藏
    private RecuperateFragment recuperateFragment;//调养
    private MeFragment meFragment;//我的
    private int currentIndex = 0;

    private View Bottom;
    private ViewPager viewPager;
    private MainSolarViewPager mainViewPager;

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    public View getBottom() {
        return Bottom;
    }


    public ViewPager getViewPager() {
        return viewPager;
    }

    public void getSolar() {
        if (mainViewPager == null) {
            mainViewPager = new MainSolarViewPager(viewPager, MainActivity.this);
        }
        mainViewPager.getListData();
    }

    public void setBottom(boolean bottom) {

        btnYangsheng.setEnabled(bottom);
        btnDiancang.setEnabled(bottom);
        btnTiaoyang.setEnabled(bottom);
        btnMe.setEnabled(bottom);
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        Bottom = findViewById(R.id.rl_main_bottom);
        mainViewpager = (MainViewPager) findViewById(R.id.main_viewpager);
        btnYangsheng = (Button) findViewById(R.id.btn_yangsheng);
        btnYangsheng.setOnClickListener(MainActivity.this);
        unreadYangsheng = (ImageView) findViewById(R.id.unread_yangsheng);
        btnDiancang = (Button) findViewById(R.id.btn_diancang);
        btnDiancang.setOnClickListener(MainActivity.this);
        unreadDiancang = (ImageView) findViewById(R.id.unread_diancang);
        btnTiaoyang = (Button) findViewById(R.id.btn_tiaoyang);
        btnTiaoyang.setOnClickListener(MainActivity.this);
        unreadTiaoyang = (ImageView) findViewById(R.id.unread_tiaoyang);
        btnMe = (Button) findViewById(R.id.btn_me);
        btnMe.setOnClickListener(MainActivity.this);
        unreadMe = (ImageView) findViewById(R.id.unread_me);
        adapter = new MainFragmentPageAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        initFragments();
        getData();
    }

    public void setViewPagerFragment(int count) {
        if (healthFragment != null) {
            healthFragment.getSolar(count);
        }
    }


    public void initFragments() {
        fragemnts.clear();
        healthFragment = new HealthFragment();
        healthFragment.setmContext(this);

        collectionFragment = new CollectionFragment();
        collectionFragment.setmContext(this);
        recuperateFragment = new RecuperateFragment();
        recuperateFragment.setmContext(this);
        meFragment = new MeFragment();
        meFragment.setmContext(this);
        fragemnts.add(healthFragment);
        fragemnts.add(collectionFragment);
        fragemnts.add(recuperateFragment);
        fragemnts.add(meFragment);
        btnYangsheng.setSelected(true);
        mainViewpager.setOffscreenPageLimit(4);
        adapter = new MainFragmentPageAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        adapter.appendList(fragemnts);
        setListener();
    }


    public void setListener() {
        mainViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                btnYangsheng.setSelected(position == 0);
                btnDiancang.setSelected(position == 1);
                btnTiaoyang.setSelected(position == 2);
                btnMe.setSelected(position == 3);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            String index = intent.getExtras().getString("index");
            if (index != null) {
                currentIndex = 0;
                mainViewpager.setCurrentItem(currentIndex, false);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_yangsheng) {
            currentIndex = 0;

        } else if (view.getId() == R.id.btn_diancang) {
            currentIndex = 1;

        } else if (view.getId() == R.id.btn_tiaoyang) {
            currentIndex = 2;

        } else if (view.getId() == R.id.btn_me) {
            currentIndex = 3;
        }
        mainViewpager.setCurrentItem(currentIndex, false);

    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    public void getData() {
        Map<String, String> map = new HashMap<>();
        OkHttpUtil.doPost(this, UrlUtil.TEST, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(String s) {

            }
        }, Object.class);
    }
}

