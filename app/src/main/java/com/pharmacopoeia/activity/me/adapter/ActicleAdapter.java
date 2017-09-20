package com.pharmacopoeia.activity.me.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xus on 2017/7/20.
 */

public class ActicleAdapter extends PBaseAdapter<HealthContentArticleBean, ActicleAdapter.ViewHolder> {
    private boolean isDelete = false;

    public ActicleAdapter(Context context, List<HealthContentArticleBean> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup, final ViewHolder viewHolder) {
        HealthContentArticleBean healthContentArticleBean = list.get(i);
        viewHolder.textName.setText(healthContentArticleBean.getArticleTitle());
        viewHolder.textUser.setText(healthContentArticleBean.getAuthorName());
        viewHolder.textSelect.setText(healthContentArticleBean.getArticleView());
        ImageLoaderUtil.getInstance().loadNomalImage(healthContentArticleBean.getPicUrl(), viewHolder.image);
        setFlowlayout(viewHolder.tagFlowLayout, healthContentArticleBean.getTags());
        viewHolder.delete.setVisibility(isDelete?View.VISIBLE:View.GONE);
        return view;
    }
    public void setFlowlayout(final TagFlowLayout flowlayout, String tage) {
        if (!TextUtils.isEmpty(tage)){
            flowlayout.setVisibility(View.VISIBLE);

            ArrayList<String> list=new ArrayList<String>();
            Collections.addAll(list,tage.split(","));
            flowlayout.setAdapter(new TagAdapter<String>(list) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.view_flowlayout_item, flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }else{
            flowlayout.setVisibility(View.GONE);
        }
    }
    @Override
    public ActicleAdapter.ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.health_fragment_content, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView textName, textUser, textSelect;
        private ImageView image;
        private TagFlowLayout tagFlowLayout;
        private CheckBox delete;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            textUser = (TextView) root.findViewById(R.id.text_user);
            textName = (TextView) root.findViewById(R.id.text_name);
            textSelect = (TextView) root.findViewById(R.id.text_select);
            image = (ImageView) root.findViewById(R.id.image);
            tagFlowLayout = (TagFlowLayout) root.findViewById(R.id.flowlayout);
            delete = (CheckBox) root.findViewById(R.id.delete);
        }
    }
}
