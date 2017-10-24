package com.pharmacopoeia.activity.collection;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.WebActivity;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.ShopType;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.GridDialog;
import com.pharmacopoeia.view.PinnedSectionListView;
import com.pharmacopoeia.view.blurbitmap.BlurredView;

import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/12.
 * 典藏
 */

public class CollectionFragment extends BaseLazyFragment {
    protected SmartTabLayout viewpagertab;
    protected ViewPager viewPager;
    private RelativeLayout tab;
//    String s[] = {"全部", "美颜", "疏肝", "养胃", "养气血", "滋补", "养生", "舒筋活骨", "补肾", "减肥", "降三高", "好睡眠", "通便", "好身材"};
    private ImageView down;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.collection_fragment, container, false);
        return mView;
    }

    @Override
    protected void lazyLoad() {
        getData();
    }

    private void initView(View rootView) {
        initTitleView();
        left.setText("典藏说明");

        FragmentPagerItems.Creator item = FragmentPagerItems.with(getActivity());
        viewpagertab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        down= (ImageView) rootView.findViewById(R.id.down);
        tab = (RelativeLayout) rootView.findViewById(R.id.tab);
        titleText.setText("典藏");

      final   List<ShopType> list = LiteOrmDBUtil.getInstance(getActivity()).getQueryAll(ShopType.class);
        for (int i = 0; i < list.size(); i++) {
            Bundle args =new Bundle();
            args.putString("id",list.get(i).getCateId());
            item = item.add(list.get(i).getCateName(), CollectionListFragment.class,args);
        }
        if (list!=null&&list.size()>0){
            tab.setVisibility(View.VISIBLE);
        }else{
            tab.setVisibility(View.GONE);

        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), item.create());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewpagertab.setViewPager(viewPager);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "典藏说明");
                bundle.putString("url", "file:///android_asset/dcsm.html");
                IntentUtils.openActivity(getActivity(), WebActivity.class, bundle);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridDialog dialog=new GridDialog(getActivity(),list,viewPager.getCurrentItem());
                dialog.show(getActivity(),v);
                dialog.setOnPhotoClickListener(new GridDialog.OnTabItemClickListener() {
                    @Override
                    public void onTabItemClick(int pos) {
                        viewPager.setCurrentItem(pos);
                    }
                });
            }
        });
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.INVISIBLE);
//        right.setImageResource(R.drawable.sousuo);
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentUtils.openActivity(getActivity(), SearchCollectionActivity.class);
//            }
//        });
    }

    public void getData() {
        Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
        OkHttpUtil.doPost(getActivity(), UrlUtil.CATELIST, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<ShopType> list = (List<ShopType>) o;
                LiteOrmDBUtil.getInstance(getActivity()).deleteAll(ShopType.class);
                LiteOrmDBUtil.getInstance(getActivity()).insertAll(list);
                initView(mView);
            }
            @Override
            public void onError(String s) {
            }
        }, ShopType.class);
    }
}
