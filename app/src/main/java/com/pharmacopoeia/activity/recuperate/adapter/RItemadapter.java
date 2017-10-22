package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.model.RecuperateModel;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/7/19.
 */

public class RItemadapter extends PBaseAdapter<String, RItemadapter.ViewHolder> {

    private List<Boolean> value;
    private OnValueChange onValueChange;

    public RItemadapter(Context context, List<String> list, OnValueChange onValueChange,String mvalue,List<String> vlist) {
        super(context, list);
        if(TextUtils.isEmpty(mvalue)){
            mvalue="";
        }
        value = new ArrayList<>();
        this.onValueChange = onValueChange;
        for (int j = 0; j < list.size(); j++) {
            if (mvalue.contains(vlist.get(j))){
                value.add(true);

            }else{
                value.add(false);

            }
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup, final ViewHolder viewHolder) {
        viewHolder.q.setText(list.get(i));
        viewHolder.q.setChecked(value.get(i));
        viewHolder.q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < value.size(); j++) {
                        value.set(j, false);
                }
                value.set(i, true);
                onValueChange.onChange(i);

                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view, int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.r_item, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private RadioButton q;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            q = (RadioButton) root.findViewById(R.id.q);
        }
    }

    public interface OnValueChange {
        void onChange(int i);
    }
}
