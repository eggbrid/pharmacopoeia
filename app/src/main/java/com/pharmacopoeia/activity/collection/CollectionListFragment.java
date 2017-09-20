package com.pharmacopoeia.activity.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.adapter.CollectionAdapter;
import com.pharmacopoeia.activity.health.ShopActivity;
import com.pharmacopoeia.base.BaseFragment;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.bean.model.ShopType;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/20.
 */

public class CollectionListFragment extends BaseLazyFragment implements RefreshListener, AdapterView.OnItemClickListener {
    protected ListView list;
    private CollectionAdapter adapter;
    private List<CollectionBean> lists = new ArrayList<>();
    private RefreshLayout refresh;
    private String type = "";
    private TextView no_data;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.collection_list_fragment, container, false);
        type = getArguments().getString("id");
        initView(mView);
        return mView;

    }

    @Override
    protected void lazyLoad() {
        adapter = new CollectionAdapter(getActivity(), lists);
        list.setAdapter(adapter);

        setRefresh(refresh, this.list, true, true, this);
    }

    private void initView(View rootView) {
        list = (ListView) rootView.findViewById(R.id.list);
        no_data = (TextView) findViewById(R.id.no_data);

        list.setOnItemClickListener(this);
        refresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
    }

    @Override
    public void onLoadMore() {
        List<CollectionBean> l = adapter.getList();
        getData(l.get(l.size() - 1).getVideoId());
    }

    @Override
    public void onRefresh() {
        lists.clear();
        getData("0");
    }

//    public void getData() {
//        Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
//        OkHttpUtil.doPost(getActivity(), UrlUtil.CATELIST, map, new CallBack() {
//            @Override
//            public void onSuccess(Object o) {
//                List<ShopType> list = (List<ShopType>) o;
//                LiteOrmDBUtil.getInstance(getActivity()).deleteAll(ShopType.class);
//                LiteOrmDBUtil.getInstance(getActivity()).insertAll(list);
//                initView(mView);
//            }
//            @Override
//            public void onError(String s) {
//            }
//        }, ShopType.class);
//    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CollectionBean collectionBean = adapter.getList().get(position);
        Intent intent = new Intent();
        intent.setClass(getContext(), ShopActivity.class);
        intent.putExtra("itemId", collectionBean.getItemId());
        startActivity(intent);
    }


    public void getData(final String id) {
        Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
        map.put("cateId", type);
        if (TextUtils.isEmpty(id) || id.equals("0")) {
            map.put("cursor", "0");
        } else {
            map.put("cursor", id);
        }
        map.put("cursor", TextUtils.isEmpty(id) ? "0" : id);
        map.put("pageSize", "20");
        OkHttpUtil.doPost(getActivity(), UrlUtil.SHOPLIST, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                if (TextUtils.isEmpty(id) || id.equals("0")) {
                    lists.clear();
                }
                List<CollectionBean> list = (List<CollectionBean>) o;
                lists.addAll(list);
                adapter.setList(lists);
                adapter.notifyDataSetChanged();
                if (list.size() < 20) {
                    stopRefresh(refresh, true);
                } else {
                    stopRefresh(refresh, false);
                }
                if (lists.size() <= 0) {
                    refresh.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String s) {
                stopRefresh(refresh, true);
                if (lists.size() <= 0) {
                    refresh.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                }
            }
        }, CollectionBean.class);
    }


}




