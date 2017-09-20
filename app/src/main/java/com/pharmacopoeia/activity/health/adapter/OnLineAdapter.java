package com.pharmacopoeia.activity.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by xus on 2017/8/28.
 */

public class OnLineAdapter extends PBaseAdapter<VideoListResponse, OnLineAdapter.ViewHolder> {


    public OnLineAdapter(Context context, List<VideoListResponse> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        VideoListResponse vr=list.get(i);
        ImageLoaderUtil.getInstance().loadNomalImage(vr.getVideoPic(),viewHolder.image,R.drawable.gray_conner_btn_pcomment);
        ImageLoaderUtil.getInstance().loadNomalImage("",viewHolder.avatar,R.drawable.default_avatar);
        viewHolder.name.setText(vr.getAuthorName());
        viewHolder.title.setText(vr.getVideoTitle());
        viewHolder.num.setText(vr.getPlayNum()+"人");
        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.health_online_item, null);
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView image;
        private ImageView avatar;
        private TextView name;
        private TextView num;
        private TextView title;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            image=(ImageView)root.findViewById(R.id.image);
            avatar=(ImageView)root.findViewById(R.id.avatar);
            name=(TextView)root.findViewById(R.id.name);
            num=(TextView)root.findViewById(R.id.num);
            title=(TextView)root.findViewById(R.id.title);
        }
    }
}
