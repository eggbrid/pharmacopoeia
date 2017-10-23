package com.pharmacopoeia.activity.health.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by xus on 2017/10/23.
 */

public class ImageAdapter extends BaseAdapter {
    String[] images;
    Context context;

    public ImageAdapter(Context context, String[] strings) {
        images = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageLoaderUtil.getInstance().loadNomalImage(images[position],image);
        return convertView;
    }


}
