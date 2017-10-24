package com.pharmacopoeia.activity.health;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.CollectionListFragment;
import com.pharmacopoeia.base.CommentActivity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by 52243 on 2017/7/19.
 */

public class ShopActivity extends CommentActivity implements View.OnClickListener {

    protected SmartTabLayout viewpagertab;
    protected ViewPager viewPager;
    String s[] = {"详情", "评价", "应用"};
    private FragmentPagerItemAdapter adapter;
    private RelativeLayout relative;
    private Class fragments[] = {ShopDetailFragment.class, ShopCommentFragment.class, ShopApplyFragment.class};

    public String itemId;

    @Override
    public int setContentView() {
        return R.layout.health_shop_activity;
    }

    @Override
    public void initView() throws Exception {
        relative = (RelativeLayout) findViewById(R.id.relative);
        relative.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.vPager);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        FragmentPagerItems.Creator item = FragmentPagerItems.with(this);
        viewpagertab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        for (int i = 0; i < s.length; i++) {
            item = item.add(s[i], fragments[i]);
        }
        initpage(item);
    }

    public void initpage(FragmentPagerItems.Creator item) {
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), item.create());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewpagertab.setViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.relative:
                finish();
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShopApplyFragment.appContent = null;
    }
}
