package com.pharmacopoeia.activity.health;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.health.adapter.ComVideoAdapter;
import com.pharmacopoeia.activity.me.adapter.VideoAdapter;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.HealthContentVideoBean;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CircleImageView;
import com.pharmacopoeia.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/8/4.
 */

public class ComActivity extends CommentActivity implements View.OnClickListener , RefreshListener,AdapterView.OnItemClickListener {
    protected ListView list;
    protected RefreshLayout refresh;
    protected ImageView left_;
    protected TextView right_;
    protected CircleImageView avatar;
    protected TextView name;
    protected TextView info;
    protected ComVideoAdapter adapter;
    private List<VideoListResponse> lists = new ArrayList<>();

    @Override
    public int setContentView() {
        return R.layout.health_com_activity;
    }

    @Override
    public void initView() throws Exception {

        left_ = (ImageView) findViewById(R.id.left);
        right_ = (TextView) findViewById(R.id.right);
        list = (ListView) findViewById(R.id.list);


        list.setOnItemClickListener(ComActivity.this);
        left_.setOnClickListener(this);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        refresh.setOnClickListener(ComActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.health_com_header, null);
        list.addHeaderView(view);
        avatar = (CircleImageView) view.findViewById(R.id.avatar);
        name = (TextView) view.findViewById(R.id.name);
        info = (TextView) view.findViewById(R.id.info);
        adapter = new ComVideoAdapter(this, lists);
        list.setAdapter(adapter);
        setRefresh(refresh, this.list, true, true, this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.left) {
            this.finish();
        } else if (view.getId() == R.id.right) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore() {
        List<VideoListResponse> l = adapter.getList();
        getData(l.get(l.size() - 1).getVideoId());
    }

    @Override
    public void onRefresh() {
        lists.clear();
        getData("0");
    }


    public void getData(String id) {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
            map.put("authorId", "10000");
        if (TextUtils.isEmpty(id) || id.equals("0")) {
            map.put("cursor", "0");
            lists.clear();
        } else {
            map.put("cursor", id);
        }
        map.put("pageSize", "20");
        OkHttpUtil.doPost(this, UrlUtil.VIDEOLIST, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<VideoListResponse> list = (List<VideoListResponse>) o;
                lists.addAll(list);
                adapter.setList(lists);
                adapter.notifyDataSetChanged();
                if (list.size() < 20) {
                    stopRefresh(refresh, true);
                } else {
                    stopRefresh(refresh, false);
                }

            }
            @Override
            public void onError(String s) {
                stopRefresh(refresh, true);

            }
        }, VideoListResponse.class);
    }
}
