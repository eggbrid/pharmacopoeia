package com.pharmacopoeia.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2016/12/2.
 */

public abstract class PBaseHoTagAdapter<M, ViewHodler extends BaseViewHolder> extends PBaseAdapter<M,ViewHodler> {
    public PBaseHoTagAdapter(Context context, List<M> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler holder;
            view = getLayoutView(i, LayoutInflater.from(context), viewGroup);
            holder = setViewHolder(view,i);
            if (holder==null){

            }
            view.setTag(holder);
        return getView(i, view, viewGroup, holder);
    }
}
