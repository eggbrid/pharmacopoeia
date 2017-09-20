package com.pharmacopoeia.interfaces.adapter;

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
import com.pharmacopoeia.bean.model.ShopCommentModel;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 52243 on 2017/7/24.
 */

public class ShopDetailCommentAdapter extends PBaseAdapter<Commentbean, ShopDetailCommentAdapter.Holder> {

    public ShopDetailCommentAdapter(Context context, List<Commentbean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, Holder holder) {
        holder.textTime.setText(getList().get(i).getCreateTime());
        holder.textCommtent.setText(getList().get(i).getCommentContent());
        holder.textUserName.setText(getList().get(i).getNickName());
        ImageLoaderUtil.getInstance().loadCircleImage(getList().get(i).getNickName(), holder.imageView);
        return view;
    }

    @Override
    public Holder setViewHolder(View view, int pos) {

        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.health_shop_comment_fragment_item, null);
    }

    class Holder extends BaseViewHolder {

        private ImageView imageView;
        private TextView textUserName, textCommtent, textTime;


        public Holder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            imageView = (ImageView) root.findViewById(R.id.image);
            textUserName = (TextView) root.findViewById(R.id.text_name);
            textCommtent = (TextView) root.findViewById(R.id.text_comment);
            textTime = (TextView) root.findViewById(R.id.time);
        }
    }
}
