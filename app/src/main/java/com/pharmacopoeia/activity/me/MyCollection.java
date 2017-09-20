package com.pharmacopoeia.activity.me;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.CollectionListFragment;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.view.PImageButton;

/**
 * Created by xus on 2017/7/26.
 */

public class MyCollection extends CommentActivity {
    protected SmartTabLayout viewpagertab;
    protected ViewPager viewPager;
    protected  FragmentPagerItemAdapter adapter;
    @Override
    public int setContentView() {
        return R.layout.me_my_collection;
    }

    @Override
    public void initView() throws Exception {
        viewpagertab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setCommentTitleView("我的收藏");
        right.setVisibility(View.VISIBLE);
        right.setImageResource(R.drawable.delete);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= adapter.getPage(viewPager.getCurrentItem());
                if (fragment instanceof ActicleCollectionFragment ){
                    ActicleCollectionFragment  acticleCollectionFragment=(ActicleCollectionFragment)fragment;
                    acticleCollectionFragment.setDelete();
                }else if(fragment instanceof VideoCollectionFragment ){
                    VideoCollectionFragment  VideoCollectionFragment= (VideoCollectionFragment) fragment;
                    VideoCollectionFragment.setDelete();
                }
            }
        });
        FragmentPagerItems.Creator item = FragmentPagerItems.with(this);
        item = item.add("文章", ActicleCollectionFragment.class);
        item = item.add("视频", VideoCollectionFragment.class);
         adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), item.create());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewpagertab.setViewPager(viewPager);

    }
}
