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
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 52243 on 2017/7/24.
 */

public class ShopDetailMethodAdapter extends PBaseAdapter<String, ShopDetailMethodAdapter.Holder> {

    public ShopDetailMethodAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, Holder holder) {
        ImageLoaderUtil.getInstance().loadNomalImage(getList().get(i), holder.image);
        if (i == 0) {
            holder.textDesc.setText("首先取出药丸");
        } else if (i == 1) {
            holder.textDesc.setText("把药送入口中");
        } else if (i == 2) {
            holder.textDesc.setText("最后用水送服");
        }
        return view;
    }

    @Override
    public Holder setViewHolder(View view,int pos) {
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.health_shop_detail_method_fragment_photo_item, null);
    }

    class Holder extends BaseViewHolder {
        private ImageView image;
        private TextView textDesc;

        public Holder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            image = (ImageView) root.findViewById(R.id.image);
            textDesc = (TextView) root.findViewById(R.id.text_desc);
        }
    }
}
