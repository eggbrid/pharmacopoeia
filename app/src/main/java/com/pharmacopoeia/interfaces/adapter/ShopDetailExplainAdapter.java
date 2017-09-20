package com.pharmacopoeia.interfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.health.ShopDetailFragment;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 52243 on 2017/7/24.
 */

public class ShopDetailExplainAdapter extends PBaseAdapter<ShopDetailFragment.ExplainModel, ShopDetailExplainAdapter.Holder> {

    public ShopDetailExplainAdapter(Context context, List<ShopDetailFragment.ExplainModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, Holder holder) {
        ShopDetailFragment.ExplainModel explainModel=getList().get(i);
        holder.textName.setText(explainModel.getName());
        holder.textDesc.setText(explainModel.getDesc());
        return view;
    }

    @Override
    public Holder setViewHolder(View view,int pos) {
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.health_shop_detail_explain_fragment_item, null);
    }

    class Holder extends BaseViewHolder {

        private TextView textName, textDesc;

        public Holder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            textName = (TextView) root.findViewById(R.id.text_name);
            textDesc = (TextView) root.findViewById(R.id.text_desc);

        }
    }
}
