package com.pharmacopoeia.activity.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.reponse.ActivityResponse;
import com.pharmacopoeia.bean.reponse.MainViewSolarModelResponse;
import com.pharmacopoeia.util.DateUtil;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by xus on 2017/8/16.
 */

public class ActivitiesAdapter extends PBaseAdapter<ActivityResponse,ActivitiesAdapter.ViewHolder> {
    public ActivitiesAdapter(Context context, List list) {
        super(context, list);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {

        if (i>=1){
            String d1=DateUtil.getInstance().simMY(DateUtil.getInstance().parseY_M_D_H_M_S(list.get(i).getCreateDate()));
            String d2=DateUtil.getInstance().simMY(DateUtil.getInstance().parseY_M_D_H_M_S(list.get(i-1).getCreateDate()));

            if (d1.equals(d2)){
                viewHolder.text_layout.setVisibility(View.GONE);

            }else{
                viewHolder.text_layout.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.text_layout.setVisibility(View.VISIBLE);
        }
        ImageLoaderUtil.getInstance().loadConnerImage(list.get(i).getFileUrl(),viewHolder.image,R.drawable.gray_conner_btn_pcomment,10);
        viewHolder.time.setText(DateUtil.getInstance().simMY(DateUtil.getInstance().parseY_M_D_H_M_S(list.get(i).getCreateDate())));

        return view;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.activities_item,null);
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }
    public class ViewHolder extends BaseViewHolder {
        private ImageView image;
        private TextView time;
        private LinearLayout text_layout;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            image = (ImageView) root.findViewById(R.id.image);
            time = (TextView) root.findViewById(R.id.time);
            text_layout = (LinearLayout) root.findViewById(R.id.text_layout);
        }
    }
}
