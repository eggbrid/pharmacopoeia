package com.pharmacopoeia.activity.health;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.me.adapter.VideoAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.cycleView.ImageCycleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by xus on 2017/8/28.
 */

public class OnLineVideoActivity extends CommentActivity implements RefreshListener {
    protected GridViewWithHeaderAndFooter list;
    private VideoAdapter adapter;
    private List<VideoListResponse> lists = new ArrayList<>();
    private ImageCycleView m_image_cycle;
    private ImageView image;
    private ImageView avatar;
    private TextView name;
    private TextView num;
    private TextView no_data;


    @Override
    public int setContentView() {
        return R.layout.health_online_activity;
    }

    @Override
    public void initView() throws Exception {
        list = (GridViewWithHeaderAndFooter) findViewById(R.id.list);
        no_data = (TextView) findViewById(R.id.no_data);
        View view = LayoutInflater.from(this).inflate(R.layout.health_online_header, null);
        m_image_cycle = (ImageCycleView) view.findViewById(R.id.m_image_cycle);
        image = (ImageView) view.findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("videoId", lists.get(0).getVideoId());
                bundle.putString("authorId", lists.get(0).getAuthorId());
                IntentUtils.openActivity(OnLineVideoActivity.this, VideoDetailActivity.class, bundle);

            }
        });
        avatar = (ImageView) view.findViewById(R.id.avatar);
        name = (TextView) view.findViewById(R.id.name);
        num = (TextView) view.findViewById(R.id.num);
        adapter = new VideoAdapter(this, lists);
        list.addHeaderView(view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("videoId", lists.get(position).getVideoId());
                bundle.putString("authorId", lists.get(position).getAuthorId());

                IntentUtils.openActivity(OnLineVideoActivity.this, VideoDetailActivity.class, bundle);
            }
        });
        setCommentTitleView("直播");
        getData("0");
        getDataLunBo();
    }

    public void startImageCycle(ImageCycleView mImageCycle, final List<CarouselResponse> webBeanModelList) {
        ArrayList<String> imageDescList = new ArrayList<>();
        /**装在数据的集合  图片地址*/
        ArrayList<String> urlList = new ArrayList<>();
        int size = webBeanModelList.size();
        for (int i = 0; i < size; i++) {
            urlList.add(webBeanModelList.get(i).getFileUrl());
            imageDescList.add(webBeanModelList.get(i).getFileUrl());
        }
        mImageCycle.setImageResources(imageDescList, urlList, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                ImageLoaderUtil.getInstance().loadNomalImage(imageURL, imageView, R.drawable.default_avatar);
            }

            @Override
            public void onImageClick(int position, View imageView) {
            }
        });
        mImageCycle.startImageCycle();
    }

    public void getData(final String id) {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        if (APP.getInstance().getUser(this) != null) {
            User user = APP.getInstance().getUser(this);
//            map.put("userCode", user.getUserCode());
        }
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

                if (TextUtils.isEmpty(id) || id.equals("0")) {
                    if (list == null || list.size() <= 0) {
                        no_data.setText("暂无直播");
                        no_data.setVisibility(View.VISIBLE);
                        OnLineVideoActivity.this.list.setVisibility(View.GONE);
                        return;
                    } else {
                        no_data.setVisibility(View.GONE);
                        OnLineVideoActivity.this.list.setVisibility(View.VISIBLE);
                    }
                    lists.clear();
                    ImageLoaderUtil.getInstance().loadNomalImage(list.get(0).getVideoPic(), image, R.drawable.gray_conner_btn_pcomment);
                    ImageLoaderUtil.getInstance().loadNomalImage("", avatar, R.drawable.default_avatar);
                    name.setText(list.get(0).getAuthorName());
                    num.setText(list.get(0).getPlayNum() + "人");
                }


                lists.addAll(list);
                adapter.setList(lists);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String s) {
            }
        }, VideoListResponse.class);
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


    private void getDataLunBo() {

        Map<String, String> map = OkHttpUtil.getFromMap(this);
        OkHttpUtil.doGet(this, UrlUtil.CAROUSEL, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<CarouselResponse> list = (ArrayList<CarouselResponse>) o;
                startImageCycle(m_image_cycle, list);
            }

            @Override
            public void onError(String s) {

            }
        }, CarouselResponse.class);
    }
}
