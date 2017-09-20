package com.pharmacopoeia.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.WebViewActivity;
import com.pharmacopoeia.activity.main.adapter.ActivitiesAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.Open;
import com.pharmacopoeia.bean.model.WebBeanModel;
import com.pharmacopoeia.bean.reponse.ActivityResponse;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.bean.reponse.MainViewSolarModelResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/8/16.
 */

public class ActivitiesActivity extends CommentActivity {
    private ActivitiesAdapter activitiesAdapter;
    private ListView list;
    private ArrayList<ActivityResponse> lists = new ArrayList<>();

    @Override
    public int setContentView() {
        return R.layout.main_acitvities;
    }

    @Override
    public void initView() throws Exception {
        list = (ListView) findViewById(R.id.list);
        activitiesAdapter = new ActivitiesAdapter(this, lists);
        list.setAdapter(activitiesAdapter);
        getListData();
        setCommentTitleView("活动");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                WebBeanModel webBeanModel = new WebBeanModel();
                webBeanModel.setTitle("活动");
                webBeanModel.setUrl(activitiesAdapter.getList().get(position).getActivityFileUrl());
                bundle.putSerializable("webBeanModel", webBeanModel);
                IntentUtils.openActivity(ActivitiesActivity.this, WebViewActivity.class, bundle);
            }
        });

    }


    public void getListData() {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        OkHttpUtil.doGet(this, UrlUtil.ACTIVITY, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<ActivityResponse> activityResponse = (List<ActivityResponse>) o;
                activitiesAdapter.setList(activityResponse);
                activitiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {

            }
        }, ActivityResponse.class);
    }


}
