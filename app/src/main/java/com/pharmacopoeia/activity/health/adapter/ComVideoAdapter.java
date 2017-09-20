package com.pharmacopoeia.activity.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xus on 2017/8/15.
 */

public class ComVideoAdapter extends PBaseAdapter<VideoListResponse, ComVideoAdapter.ViewHolder> {

    public ComVideoAdapter(Context context, List<VideoListResponse> list) {
        super(context, list);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ComVideoAdapter.ViewHolder viewHolder) {
        VideoListResponse VideoListResponse = list.get(i);
        viewHolder.play_times.setText(VideoListResponse.getPlayNum()+"次播放");
        viewHolder.videoplayer.setUp(VideoListResponse.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        viewHolder.text_video_name.setText(VideoListResponse.getVideoTitle());
        ImageLoaderUtil.getInstance().loadNomalImage(VideoListResponse.getVideoPic(), viewHolder.videoplayer.thumbImageView);
        viewHolder.times.setText(VideoListResponse.getCreateTime());
        return view;
    }

    @Override
    public ComVideoAdapter.ViewHolder setViewHolder(View view,int pos) {
        return new ComVideoAdapter.ViewHolder(view);
    }
    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.health_com_video, null);
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView play_times, times;
        private JCVideoPlayerStandard videoplayer;
        private TextView text_video_name;

        public ViewHolder(View root) {
            super(root);
        }
        @Override
        public void initView(View root) {
            play_times = (TextView) root.findViewById(R.id.play_times);
            times = (TextView) root.findViewById(R.id.times);
            videoplayer = (JCVideoPlayerStandard) root.findViewById(R.id.videoplayer);
            text_video_name = (TextView) root.findViewById(R.id.text_video_name);

        }
    }
}
