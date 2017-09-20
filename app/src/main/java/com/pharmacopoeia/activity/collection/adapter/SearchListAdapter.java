package com.pharmacopoeia.activity.collection.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.cache.CollectionSearch;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xus on 2017/7/24.
 */

public class SearchListAdapter extends PBaseAdapter<CollectionSearch, SearchListAdapter.ViewHolder> {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key="";
    public SearchListAdapter(Context context, List<CollectionSearch> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        viewHolder.text.setText(matcherSearchText(list.get(i).getName(),key));
        return view;
    }

    @Override
    public ViewHolder setViewHolder(View view,int pos) {
        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.collection_search_list_item, viewGroup, false);
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView text;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            text = (TextView) root.findViewById(R.id.text);
        }
    }

    public  SpannableString matcherSearchText( String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.app_green)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }


}
