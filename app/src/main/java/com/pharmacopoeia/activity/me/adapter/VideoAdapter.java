package com.pharmacopoeia.activity.me.adapter;

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
import com.pharmacopoeia.bean.model.HealthContentVideoBean;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xus on 2017/7/26.
 */

public class VideoAdapter extends PBaseAdapter<VideoListResponse, VideoAdapter.ViewHolder> {
    private boolean isDelete = false;

    public VideoAdapter(Context context, List<VideoListResponse> list) {
        super(context, list);
    }
    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete() {
        isDelete = !isDelete;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        VideoListResponse healthContentVideoBean = list.get(i);
        viewHolder.textUser.setText(healthContentVideoBean.getAuthorName());
        viewHolder.playNum.setText(healthContentVideoBean.getPlayNum()+"次播放");
        viewHolder.videoplayer.setUp(healthContentVideoBean.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, healthContentVideoBean.getVideoTitle());
        ImageLoaderUtil.getInstance().loadNomalImage(healthContentVideoBean.getVideoPic(), viewHolder.videoplayer.thumbImageView);
        viewHolder.delete.setVisibility(isDelete?View.VISIBLE:View.GONE);
        viewHolder.collectNum.setText(healthContentVideoBean.getCollectNum());
        viewHolder.commentNum.setText(healthContentVideoBean.getCommentNum());
        viewHolder.shareNum.setText(healthContentVideoBean.getShareNum());

        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.health_fragment_video, null);
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView textUser, playNum;
        private ImageView image;
        private JCVideoPlayerStandard videoplayer;
        private CheckBox delete;
        private TextView collectNum, commentNum,shareNum;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            textUser = (TextView) root.findViewById(R.id.text_user);
            playNum = (TextView) root.findViewById(R.id.playNum);
            image = (ImageView) root.findViewById(R.id.image);
            videoplayer = (JCVideoPlayerStandard) root.findViewById(R.id.videoplayer);
            delete = (CheckBox) root.findViewById(R.id.delete);
            collectNum = (TextView) root.findViewById(R.id.collectNum);
            commentNum = (TextView) root.findViewById(R.id.commentNum);
            shareNum = (TextView) root.findViewById(R.id.shareNum);

        }
    }
}
