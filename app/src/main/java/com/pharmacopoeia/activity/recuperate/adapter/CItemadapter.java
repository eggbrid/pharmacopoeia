package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

public class CItemadapter extends PBaseAdapter<String, CItemadapter.ViewHolder> {


    private List<Boolean> value;
    private CItemadapter.OnValueChange onValueChange;
    public CItemadapter(Context context, List<String> list,CItemadapter.OnValueChange onValueChange,String mvalue,List<String> vlist) {
        super(context, list);
        if(TextUtils.isEmpty(mvalue)){
            mvalue="";
        }
        value = new ArrayList<>();
        this.onValueChange=onValueChange;
        for (int j = 0; j <list.size(); j++) {
            value.add( mvalue.contains(vlist.get(j)));
        }
        this.value = value;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup, CItemadapter.ViewHolder viewHolder) {
        viewHolder.q.setText(list.get(i));
        viewHolder.q.setChecked(value.get(i));
        viewHolder.q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < value.size(); j++) {
                    if (i == j) {
                        value.set(j, !value.get(j));
                        onValueChange.onChange(value);
                    }
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public CItemadapter.ViewHolder setViewHolder(View view, int pos) {
        return new CItemadapter.ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.c_item, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private CheckBox q;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            q = (CheckBox) root.findViewById(R.id.q);
        }
    }
    public interface OnValueChange{
        void onChange(List<Boolean> values);
    }
}
