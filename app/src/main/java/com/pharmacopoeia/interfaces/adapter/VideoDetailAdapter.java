package com.pharmacopoeia.interfaces.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.model.HealthContentShopBean;
import com.pharmacopoeia.bean.model.Item;
import com.pharmacopoeia.bean.reponse.VideoDetailResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 52243 on 2017/7/27.
 */

public class VideoDetailAdapter extends BaseAdapter {

    private Context context;
    private VideoDetailResponse videoDetailModel;
    private List<Item> items;//这个才是真正显示的list

    public VideoDetailResponse getVideoDetailModel() {
        if (videoDetailModel == null) {
            videoDetailModel = new VideoDetailResponse();
        }
        return videoDetailModel;

    }

    public void setConment(Commentbean commentbean) {
        if (videoDetailModel.getComments() == null) {
            List<Commentbean> list = new ArrayList<>();
            videoDetailModel.setComments(list);
        }
        videoDetailModel.getComments().add(commentbean);

        Item item = new Item(Item.COMMENT, null);
        item.setObject(commentbean);
        items.add(item);
        notifyDataSetChanged();
    }

    public void setVideoDetailModel(VideoDetailResponse videoDetailModel) {
        this.videoDetailModel = videoDetailModel;
        addItems();
    }

    public VideoDetailAdapter(Context context) {
        this.context = context;
    }

    public void addItems() {
        items = new ArrayList<>();
        if (videoDetailModel != null) {
            if (videoDetailModel.getItem() != null) {
                int size1 = 1;
                for (int i = 0; i < size1; i++) {
                    Item item = new Item(Item.SHOP, null);
                    item.setObject(videoDetailModel.getItem());
                    items.add(item);
                }
            }
//            if (videoDetailModel.getProperties() != null) {
//                int size1 = videoDetailModel.getProperties().size();
//                for (int i = 0; i < size1; i++) {
//                    Item item = new Item(Item.SHOP, null);
//                    item.setObject(videoDetailModel.getProperties().get(i));
//                    items.add(item);
//                }
//            }
            Item item1 = new Item(Item.COMMENTTITLE, null);
            item1.setObject(null);
            items.add(item1);

            if (videoDetailModel.getComments() != null) {
                int size1 = videoDetailModel.getComments().size();
                for (int i = 0; i < size1; i++) {
                    Item item = new Item(Item.COMMENT, null);
                    item.setObject(videoDetailModel.getComments().get(i));
                    items.add(item);
                }
            }
        }
    }


    @Override
    public int getCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public int getViewTypeCount() {

        return 7;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Holder holder = null;
        Item item = items.get(position);// 从集合中获取当前行的数据
        ViewContentHolder viewContentHolder = null;
        ViewShopHolder viewShopHolder = null;
        if (convertView == null) {
            convertView = setView(type, convertView);
            switch (type) {
                case Item.ITEM:
                    viewContentHolder = new ViewContentHolder();
                    viewContentHolder.textUser = (TextView) convertView.findViewById(R.id.text_user);
                    viewContentHolder.textName = (TextView) convertView.findViewById(R.id.text_name);
                    viewContentHolder.textSelect = (TextView) convertView.findViewById(R.id.text_select);
                    viewContentHolder.image = (ImageView) convertView.findViewById(R.id.image);
                    viewContentHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.flowlayout);
                    convertView.setTag(viewContentHolder);
                    break;
                case Item.SHOP:
                    viewShopHolder = new ViewShopHolder();
                    viewShopHolder.image = (ImageView) convertView.findViewById(R.id.image);
                    viewShopHolder.textName = (TextView) convertView.findViewById(R.id.text_name);
                    viewShopHolder.textMoney = (TextView) convertView.findViewById(R.id.text_money);
                    viewShopHolder.textBuy = (TextView) convertView.findViewById(R.id.text_buy);
                    viewShopHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.flowlayout);
                    convertView.setTag(viewShopHolder);// 将vh存储到行的Tag中
                    break;
                case Item.COMMENT:
                    holder = new Holder(convertView);
                    convertView.setTag(holder);
                    break;
            }
        } else {
            switch (type) {
                case Item.ITEM:
                    viewContentHolder = (ViewContentHolder) convertView.getTag();
                    break;
                case Item.SHOP:
                    viewShopHolder = (ViewShopHolder) convertView.getTag();
                    break;
                case Item.COMMENT:
                    holder = (Holder) convertView.getTag();
                    break;
            }
        }


        // 从ViewHolder缓存的控件中改变控件的值
        // 这里主要是避免多次强制转化目标对象而造成的资源浪费
        switch (type) {
            case Item.ITEM:
                HealthContentArticleBean healthContentArticleBean = (HealthContentArticleBean) item.getObject();
                viewContentHolder.textName.setText(healthContentArticleBean.getArticleTitle());
                viewContentHolder.textUser.setText(healthContentArticleBean.getAuthorName());
                viewContentHolder.textSelect.setText(healthContentArticleBean.getArticleView());
                ImageLoaderUtil.getInstance().loadNomalImage(healthContentArticleBean.getUrl(), viewContentHolder.image);
                setFlowlayout(viewContentHolder.tagFlowLayout, healthContentArticleBean.getTags());
                break;
            case Item.SHOP:
                CollectionBean healthContentShopBean = (CollectionBean) item.getObject();
                ImageLoaderUtil.getInstance().loadNomalImage(healthContentShopBean.getItemPic(), viewShopHolder.image);
                viewShopHolder.textName.setText(healthContentShopBean.getItemName());
                viewShopHolder.textMoney.setText(healthContentShopBean.getClickNum()+"人购买");
                viewShopHolder.textBuy.setText(healthContentShopBean.getClickNum()+"人购买");
                setFlowlayout(viewShopHolder.tagFlowLayout, healthContentShopBean.getTags());
                break;
            case Item.COMMENT:
                Commentbean shopCommentModel = (Commentbean) item.getObject();
                holder.textTime.setText(shopCommentModel.getCreateTime());
                holder.textCommtent.setText(shopCommentModel.getCommentContent());
                holder.textUserName.setText(shopCommentModel.getNickName());
                ImageLoaderUtil.getInstance().loadCircleImage(shopCommentModel.getNickName(), holder.imageView);
                break;
        }

        return convertView;
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

    public View setView(int type, View view) {
        switch (type) {
            case Item.ITEM:
                view = View.inflate(context, R.layout.health_fragment_content, null);
                break;
            case Item.SHOP:
                view = View.inflate(context, R.layout.health_fragment_shop, null);
                break;
            case Item.COMMENTTITLE:
                view = View.inflate(context, R.layout.health_shop_comment_title_fragment_item, null);
                break;
            case Item.COMMENT:
                view = View.inflate(context, R.layout.health_shop_comment_fragment_item, null);
                break;
        }
        return view;
    }

    class ViewContentHolder {
        private TextView textName, textUser, textSelect;
        private ImageView image;
        private TagFlowLayout tagFlowLayout;
    }

    class ViewShopHolder {
        private TextView textName, textMoney, textBuy;
        private TagFlowLayout tagFlowLayout;
        private ImageView image;
    }

    class Holder extends BaseViewHolder {

        private ImageView imageView;
        private TextView textUserName, textCommtent, textTime;


        public Holder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            imageView = (ImageView) root.findViewById(R.id.image);
            textUserName = (TextView) root.findViewById(R.id.text_name);
            textCommtent = (TextView) root.findViewById(R.id.text_comment);
            textTime = (TextView) root.findViewById(R.id.time);
        }
    }
}
