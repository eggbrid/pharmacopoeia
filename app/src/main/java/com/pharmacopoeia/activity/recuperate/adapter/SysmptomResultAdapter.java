package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.model.HealthContentShopBean;
import com.pharmacopoeia.bean.reponse.ShopDetailResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.OtherUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 52243 on 2017/8/29.
 */

public class SysmptomResultAdapter extends PBaseAdapter<ShopDetailResponse, SysmptomResultAdapter.ViewHolder> {

    private int mWidth;

    public SysmptomResultAdapter(Context context, List<ShopDetailResponse> list) {
        super(context, list);
        int screenWidth = OtherUtils.getWidthInPx(context);
        mWidth = (screenWidth - OtherUtils.dip2px(context, 50))/2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        ShopDetailResponse healthContentShopBean = list.get(i);

        ImageLoaderUtil.getInstance().loadNomalImage(healthContentShopBean.getItemPic(), viewHolder.image);
        viewHolder.textName.setText(healthContentShopBean.getItemName());
        viewHolder.textMoney.setText("");
        viewHolder.textBuy.setText("");
        setFlowlayout(viewHolder.tagFlowLayout, healthContentShopBean.getTags());

        return view;
    }

    public void setFlowlayout(final TagFlowLayout flowlayout, String tage) {
        if (!TextUtils.isEmpty(tage)) {
            flowlayout.setVisibility(View.VISIBLE);

            ArrayList<String> list = new ArrayList<String>();
            Collections.addAll(list, tage.split(","));
            flowlayout.setAdapter(new TagAdapter<String>(list) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.view_flowlayout_item, flowlayout, false);
                    tv.setText(s);
                    return tv;
                }
            });
        } else {
            flowlayout.setVisibility(View.GONE);
        }
    }
    @Override
    public ViewHolder setViewHolder(View view,int pos) {

        return new ViewHolder(view);
    }

    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {

        return inflater.inflate(R.layout.recuperate_sysmptomresult_acitivity_item, null);
    }

    public void setFlowlayout(final TagFlowLayout flowlayout, List<String> list) {
        flowlayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.view_flowlayout_item, flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }


    public class ViewHolder extends BaseViewHolder {
        private TextView textName, textMoney, textBuy;
        private TagFlowLayout tagFlowLayout;
        private ImageView image;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View view) {
            image = (ImageView) view.findViewById(R.id.image);
            textName = (TextView) view.findViewById(R.id.text_name);
            textMoney = (TextView) view.findViewById(R.id.text_money);
            textBuy = (TextView) view.findViewById(R.id.text_buy);
            tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);

            ViewGroup.LayoutParams s = image.getLayoutParams();
            s.height = mWidth;
            s.width = mWidth;
            image.setLayoutParams(s);
        }
    }
}
