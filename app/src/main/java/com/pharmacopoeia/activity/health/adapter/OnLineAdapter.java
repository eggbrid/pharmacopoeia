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
import com.pharmacopoeia.bean.reponse.AliveListResponse;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/8/28.
 */

public class OnLineAdapter extends PBaseAdapter<AliveListResponse, OnLineAdapter.ViewHolder> {


    public OnLineAdapter(Context context, List<AliveListResponse> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        AliveListResponse vr=list.get(i);
        ImageLoaderUtil.getInstance().loadNomalImage(vr.getAlivePic(),viewHolder.image,R.drawable.gray_conner_btn_pcomment);
        ImageLoaderUtil.getInstance().loadNomalImage("",viewHolder.avatar,R.drawable.default_avatar);
        viewHolder.name.setText(vr.getAuthorName());
        viewHolder.title.setText(vr.getAliveTitle());
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
