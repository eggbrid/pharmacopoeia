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

public abstract class PBaseAdapter<M, ViewHodler extends BaseViewHolder> extends BaseAdapter {
    protected Context context;
    protected List<M> list;

    public PBaseAdapter(Context context, List<M> list) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;

        }
        this.context = context;
    }

    public List<M> getList() {
        return list;
    }

    public void setList(List<M> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public M getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler holder;
        if (view == null) {
            view = getLayoutView(i, LayoutInflater.from(context), viewGroup);
            holder = setViewHolder(view,i);
            if (holder==null){

            }
            view.setTag(holder);
        } else {
            holder = (ViewHodler) view.getTag();
        }
        return getView(i, view, viewGroup, holder);
    }
    public abstract View getView(int i, View view, ViewGroup viewGroup, ViewHodler viewHodler);

    public abstract <ViewHodler extends BaseViewHolder> ViewHodler setViewHolder(View view,int pos);

    public abstract View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup);
}
