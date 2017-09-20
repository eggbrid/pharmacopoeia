package com.pharmacopoeia.activity.health;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.util.ImageLoaderUtil;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xus on 2017/8/28.
 */

public class OnLineDetailActivity extends CommentActivity {
    protected JCVideoPlayerStandard videoplayer;
    protected ImageView lefts;

    @Override
    public int setContentView() {
        return R.layout.health_online_detail_activity;
    }

    @Override
    public void initView() throws Exception {
        lefts = (ImageView) findViewById(R.id.left);
        videoplayer = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        lefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLineDetailActivity.this.finish();
            }
        });
        videoplayer.setUp("http://mvideo.spriteapp.cn/video/2017/0629/d5c26b9c-5c92-11e7-a4e7-1866daeb0df1cut_wpc.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        ImageLoaderUtil.getInstance().loadNomalImage("http://img0.imgtn.bdimg.com/it/u=3074975953,1037799635&fm=26&gp=0.jpg", videoplayer.thumbImageView);
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
}
