package com.pharmacopoeia.activity.health;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.TestData;
import com.pharmacopoeia.activity.main.ResetPasswordActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.model.Item;
import com.pharmacopoeia.bean.model.VideoCommentModel;
import com.pharmacopoeia.bean.model.VideoDetailModel;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.VideoDetailResponse;
import com.pharmacopoeia.interfaces.adapter.VideoDetailAdapter;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.util.share.ShareUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by 52243 on 2017/7/27.
 */

public class VideoDetailActivity extends CommentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //这个页面需要做刷新和加载更多
    private VideoDetailAdapter videoDetailAdapter;
    private ListView list;
    private List<VideoCommentModel> lists = new ArrayList<>();
    private JCVideoPlayerStandard videoplayer;

    private String authorId;
    private String videoId;

    private ImageView image;
    private TextView text_video_name, text_collent, text_comment, text_share, text_play, text_shoucang, text_user, text_select;
    private VideoDetailResponse videoDetailResponse;
    private EditText ed_comment;
    private ImageView image_collent;

    @Override
    public int setContentView() {

        return R.layout.health_video_detail_activity;
    }

    @Override
    public void initView() throws Exception {
        list = (ListView) findViewById(R.id.list);
        findViewById(R.id.send_button).setOnClickListener(this);
        ed_comment = (EditText) findViewById(R.id.ed_comment);
        initData();
    }

    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        authorId = bundle.getString("authorId");
        videoId = bundle.getString("videoId");


        videoDetailAdapter = new VideoDetailAdapter(this);
        list.setAdapter(videoDetailAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.health_video_detail_header, null);
        view.findViewById(R.id.relative).setOnClickListener(this);
        view.findViewById(R.id.com_layout).setOnClickListener(this);

        text_video_name = (TextView) view.findViewById(R.id.text_video_name);
        text_collent = (TextView) view.findViewById(R.id.text_collent);
        text_comment = (TextView) view.findViewById(R.id.text_comment);
        text_share = (TextView) view.findViewById(R.id.text_share);
        text_play = (TextView) view.findViewById(R.id.text_play);
        text_shoucang = (TextView) view.findViewById(R.id.text_shoucang);
        text_user = (TextView) view.findViewById(R.id.text_user);
        text_select = (TextView) view.findViewById(R.id.text_select);
        text_video_name = (TextView) view.findViewById(R.id.text_video_name);
        image_collent = (ImageView) view.findViewById(R.id.image_collent);
        image = (ImageView) view.findViewById(R.id.image);
        view.findViewById(R.id.line3).setOnClickListener(this);
        text_share.setOnClickListener(this);
        videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        list.addHeaderView(view);
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        list.setOnItemClickListener(this);
        getData();

    }

    public void setData(VideoDetailResponse videoDetailResponse) {
        this.videoDetailResponse = videoDetailResponse;
        videoDetailAdapter.setVideoDetailModel(videoDetailResponse);
        videoDetailAdapter.notifyDataSetChanged();
        text_video_name.setText(videoDetailResponse.getVideoTitle());
        text_collent.setText(videoDetailResponse.getCollectNum());
        text_comment.setText(videoDetailResponse.getCommentNum());

        text_share.setText(videoDetailResponse.getShareNum());
        text_play.setText(videoDetailResponse.getPlayNum() + "次播放");
        text_user.setText(videoDetailResponse.getAuthorName());
        setConllection();

        if ("false".equals(videoDetailResponse.getConcerned())) {
            text_shoucang.setText("关注");
        } else {
            text_shoucang.setText("已关注");
        }
        videoplayer.setUp(videoDetailResponse.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageLoaderUtil.getInstance().loadNomalImage(videoDetailResponse.getVideoPic(), videoplayer.thumbImageView);
    }


    public void setConllection() {
        if ("false".equals(videoDetailResponse.getCollection())) {

            image_collent.setBackgroundResource(R.drawable.shoucang);
        } else {

            image_collent.setBackgroundResource(R.drawable.shoucang_nomal);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            return;
        }
        int type = videoDetailAdapter.getItem(position - 1).type;

        switch (type) {
            case Item.ITEM:
                Intent intent2 = new Intent();
                intent2.setClass(VideoDetailActivity.this, ArticleActivity.class);
                startActivity(intent2);
                break;
            case Item.SHOP:
                Intent intent1 = new Intent();
                intent1.setClass(VideoDetailActivity.this, ShopActivity.class);
                startActivity(intent1);
                break;
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {
            case R.id.com_layout:
                IntentUtils.openActivity(this, ComActivity.class);
                break;
            case R.id.relative:
                finish();
                break;
            case R.id.send_button:
                if (TextUtils.isEmpty(ed_comment.getText())) {
                    T.show(this, "必须输入评论内容");
                    return;
                }
                addComent(ed_comment.getText().toString());
                break;
            case R.id.line3:
                addCollection();
                break;
            case R.id.text_share:
                ShareUtils.showShare(this, view);
                break;
        }
    }

    public void addCollection() {

        if (APP.isLogin(this)) {
            Map<String, String> map = OkHttpUtil.getFromMap(this);
            map.put("contentId", videoId);
            String url=UrlUtil.COLLECTION;
            map.put("contentType", "video");
            if ("false".equals(videoDetailResponse.getCollection())) {
                map.put("contentId", videoId);
            }else{
                map.put("contentIds", videoId);
                url=UrlUtil.COLLECTIONCANCEL;
            }
            map.put("userCode", APP.getUser(this).getUserCode());
            OkHttpUtil.doPost(this,url , map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    dissPross();
                    if ("false".equals(videoDetailResponse.getCollection())) {
                        videoDetailResponse.setCollection("true");
                    } else {
                        videoDetailResponse.setCollection("false");
                    }
                    setConllection();
                }

                @Override
                public void onError(String s) {
                    T.show(VideoDetailActivity.this,s);
                }
            }, NUllValueResponse.class);
        }
    }

    public void addComent(final String commentContent) {

        if (APP.isLogin(this)) {
            Map<String, String> map = OkHttpUtil.getFromMap(this);
            map.put("videoId", videoId);
            map.put("commentContent", commentContent);
            map.put("userId", APP.getUser(this).getUserId());
            OkHttpUtil.doPost(this, UrlUtil.VIDEODETAILCOMMENTINSERT, map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    Commentbean commentbean = new Commentbean();
                    commentbean.setNickName(APP.getUser(VideoDetailActivity.this).getName());
                    commentbean.setCommentContent(commentContent);
                    videoDetailAdapter.setConment(commentbean);
                }

                @Override
                public void onError(String s) {

                }
            }, NUllValueResponse.class);
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

    private void getData() {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        if (APP.getInstance().getUser(this) != null) {
            User user = APP.getInstance().getUser(this);
            map.put("userCode", user.getUserCode());
        }
        map.put("authorId", authorId);
        map.put("videoId", videoId);


        OkHttpUtil.doPost(this, UrlUtil.VIDEODETAIL, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                VideoDetailResponse res = (VideoDetailResponse) o;
                if (res != null) {
                    list.setVisibility(View.VISIBLE);
                    setData(res);
                }
            }

            @Override
            public void onError(String s) {
            }
        }, VideoDetailResponse.class);
    }
}
