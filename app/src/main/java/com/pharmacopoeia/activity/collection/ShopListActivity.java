package com.pharmacopoeia.activity.collection;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.adapter.CollectionAdapter;
import com.pharmacopoeia.activity.health.ShopActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/8/1.
 */

public class ShopListActivity extends CommentActivity implements RefreshListener, AdapterView.OnItemClickListener {
    protected ListView list;
    private CollectionAdapter adapter;
    private List<CollectionBean> lists = new ArrayList<>();
    private RefreshLayout refresh;

    @Override
    public int setContentView() {
        return R.layout.collection_shop_list;
    }

    @Override
    public void initView() throws Exception {
        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(this);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        adapter = new CollectionAdapter(this, lists);
        list.setAdapter(adapter);

        setRefresh(refresh, this.list, true, true, this);
        setCommentTitleView("商品列表");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this, ShopActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        getData();

    }

    @Override
    public void onRefresh() {
        lists.clear();
        getData();
    }

    public void getData() {
//        List<String> list = new ArrayList<>();
//        list.add("癌症");
//        list.add("去痛去肿");
//        for (int i = 0; i <= 20; i++) {
//            CollectionBean collectionBean = new CollectionBean();
//            collectionBean.setImage("http://image.360kad.com/group1/M00/0B/BB/CgAgEFYEKmqAAN04AAKBYBYsOsM93.jpeg");
//            collectionBean.setTitle("悦胜 消癌平胶囊 0.2g*24粒");
//            collectionBean.setTags(list);
//            collectionBean.setUseNum("231人已用");
//            lists.add(collectionBean);
//        }
//        adapter.setList(lists);
//        adapter.notifyDataSetChanged();
//        stopRefresh(refresh, false);

    }
}
