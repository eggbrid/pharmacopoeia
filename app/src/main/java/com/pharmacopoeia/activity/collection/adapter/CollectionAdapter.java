package com.pharmacopoeia.activity.collection.adapter;

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
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xus on 2017/7/20.
 */

public class CollectionAdapter extends PBaseAdapter<CollectionBean, CollectionAdapter.ViewHolder> {
    private boolean isDelete = false;

    public CollectionAdapter(Context context, List<CollectionBean> list) {
        super(context, list);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup, final ViewHolder viewHolder) {
        CollectionBean collectionBean = list.get(i);
        ImageLoaderUtil.getInstance().loadNomalImage(collectionBean.getItemPic(), viewHolder.imageView);

        viewHolder.name.setText(collectionBean.getItemName());
        viewHolder.use_num.setText(collectionBean.getClickNum()+"人已用");
        if (!TextUtils.isEmpty(collectionBean.getTags())){
            viewHolder.flowlayout.setVisibility(View.VISIBLE);

            ArrayList<String> list=new ArrayList<String>();
            Collections.addAll(list, collectionBean.getTags().split(","));
            viewHolder.flowlayout.setAdapter(new TagAdapter<String>(list) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.view_flowlayout_item, viewHolder.flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        }else{
            viewHolder.flowlayout.setVisibility(View.GONE);
        }


        return view;
    }

    @Override
    public CollectionAdapter.ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.collection_list_item, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView imageView;
        private TextView name, use_num;
        private TagFlowLayout flowlayout;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            imageView = (ImageView) root.findViewById(R.id.image);
            name = (TextView) root.findViewById(R.id.name);
            use_num = (TextView) root.findViewById(R.id.use_num);
            flowlayout = (TagFlowLayout) root.findViewById(R.id.flowlayout);
        }
    }
}
