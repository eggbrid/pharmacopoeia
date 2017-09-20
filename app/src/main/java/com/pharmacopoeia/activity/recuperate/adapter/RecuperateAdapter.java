package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.model.RecuperateModel;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by xus on 2017/7/19.
 */

public class RecuperateAdapter extends PBaseAdapter<RecuperateModel, RecuperateAdapter.ViewHolder> {


    public RecuperateAdapter(Context context, List<RecuperateModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        ImageLoaderUtil.getInstance().loadNomalImage(list.get(i).getType2LogoUrl(), viewHolder.imageView);
        viewHolder.text.setText(list.get(i).getType2Name());
        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.recuperate_fragment_item, null);
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView imageView;
        private TextView text;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            imageView = (ImageView) root.findViewById(R.id.image);
            text = (TextView) root.findViewById(R.id.text);
        }
    }
}
