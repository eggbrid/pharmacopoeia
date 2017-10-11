package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.model.ResultTabBean;
import com.pharmacopoeia.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 52243 on 2017/9/20.
 */

public class SysmptomResultReaseAdapter extends PBaseAdapter<ResultTabBean, SysmptomResultReaseAdapter.ViewHolder> {
    public SysmptomResultReaseAdapter(Context context, List<ResultTabBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, final View view, ViewGroup viewGroup, final ViewHolder viewHolder) {
        final ResultTabBean resultTabBean = list.get(i);
        ImageLoaderUtil.getInstance().loadNomalImage(resultTabBean.getTabLogo(), viewHolder.imageView);
        viewHolder.text_type.setText(resultTabBean.getTabName());
        if (!resultTabBean.isSelect()) {
            viewHolder.image_res.setBackgroundResource(R.drawable.up_icon);
            viewHolder.text_comment.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(resultTabBean.getTabContent())){
                viewHolder.text_comment.setText(Html.fromHtml(resultTabBean.getTabContent()));
            }else{
                viewHolder.text_comment.setText("");

            }
        } else {
            viewHolder.image_res.setBackgroundResource(R.drawable.down_icon);
            viewHolder.text_comment.setVisibility(View.GONE);
        }

        viewHolder.xingchengyuanyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resultTabBean.isSelect()) {
                    resultTabBean.setSelect(true);
                    viewHolder.text_comment.setVisibility(View.GONE);
                    viewHolder.image_res.setBackgroundResource(R.drawable.down_icon);
                } else {
                    viewHolder.image_res.setBackgroundResource(R.drawable.up_icon);
                    viewHolder.text_comment.setVisibility(View.VISIBLE);
                    resultTabBean.setSelect(false);
                }
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
        return inflater.inflate(R.layout.recuperate_sysmptomresult_rease_acitivity_item, null);
    }

    public class ViewHolder extends BaseViewHolder {

        private ImageView imageView;
        private TextView text_type;
        private TextView text_comment;
        private ImageView image_res;
        private View xingchengyuanyi;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            imageView = (ImageView) root.findViewById(R.id.image1);
            text_comment = (TextView) root.findViewById(R.id.text_comment);
            text_type = (TextView) root.findViewById(R.id.text_type);
            image_res = (ImageView) root.findViewById(R.id.image_res);
            xingchengyuanyi = root.findViewById(R.id.xingchengyuanyi);


        }
    }
}
