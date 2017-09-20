package com.pharmacopoeia.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.TestData;
import com.pharmacopoeia.activity.health.ArticleActivity;
import com.pharmacopoeia.activity.health.ShopActivity;
import com.pharmacopoeia.activity.me.adapter.ActicleAdapter;
import com.pharmacopoeia.base.BaseFragment;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/26.
 */

public class ActicleCollectionFragment extends BaseLazyFragment implements RefreshListener, AdapterView.OnItemClickListener {
    protected ListView list;
    private ActicleAdapter adapter;
    private List<HealthContentArticleBean> lists = new ArrayList<>();
    private RefreshLayout refresh;
    private TextView btn_delete;
    private TextView no_data;

    public void setDelete() {
        adapter.setDelete();
        btn_delete.setVisibility(adapter.isDelete() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.me_acticle_collection, container, false);
        initView(mView);
        adapter = new ActicleAdapter(getActivity(), lists);
        list.setAdapter(adapter);

        return mView;

    }

    @Override
    protected void lazyLoad() {
        setRefresh(refresh, this.list, true, true, this);
    }

    private void initView(View rootView) {
        list = (ListView) rootView.findViewById(R.id.list);
        no_data = (TextView) findViewById(R.id.no_data);
        btn_delete = (TextView) rootView.findViewById(R.id.btn_delete);
        list.setOnItemClickListener(this);
        refresh = (RefreshLayout) rootView.findViewById(R.id.refresh);
    }


    @Override
    public void onLoadMore() {
        List<HealthContentArticleBean> l = adapter.getList();
        getData(l.get(l.size() - 1).getArticleId());
    }

    @Override
    public void onRefresh() {
        getData("0");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", lists.get(position).getArticleId());
        IntentUtils.openActivity(getContext(), ArticleActivity.class, bundle);
    }

    public void getData(final  String id) {
        Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
        if (APP.getInstance().getUser(getActivity()) != null) {
            User user = APP.getInstance().getUser(getActivity());
            map.put("userCode", user.getUserCode());
        }
        if (TextUtils.isEmpty(id) || id.equals("0")) {
            map.put("cursor", "0");
        } else {
            map.put("cursor", id);
        }
        map.put("cursor", TextUtils.isEmpty(id) ? "0" : id);
        map.put("pageSize", "20");
        OkHttpUtil.doPost(getActivity(), UrlUtil.articleList, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                if (TextUtils.isEmpty(id) || id.equals("0")) {
                    lists.clear();
                }
                List<HealthContentArticleBean> list = (List<HealthContentArticleBean>) o;
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
        }, HealthContentArticleBean.class);
    }
}
