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
import com.pharmacopoeia.bean.model.Commentbean;

import java.util.List;

/**
 * Created by xus on 2017/7/25.
 */

public class CommentAdapter extends PBaseAdapter<Commentbean, CommentAdapter.ViewHolder> {


    public CommentAdapter(Context context, List<Commentbean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        viewHolder.nick_name.setText(list.get(i).getNickName());
        viewHolder.content.setText(list.get(i).getCommentContent()+"");
        viewHolder.time.setText(list.get(i).getCreateTime()+"");

        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.health_comment_list_item, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView avatar;
        private TextView nick_name;
        private TextView content;
        private TextView time;


        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            avatar= (ImageView) root.findViewById(R.id.avatar);
            nick_name= (TextView) root.findViewById(R.id.nick_name);
            content= (TextView) root.findViewById(R.id.content);
            time= (TextView) root.findViewById(R.id.time);
            avatar= (ImageView) root.findViewById(R.id.avatar);

        }
    }
}
