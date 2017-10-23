package com.pharmacopoeia.interfaces.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.TestData;
import com.pharmacopoeia.activity.WebViewActivity;
import com.pharmacopoeia.activity.health.OnLineVideoActivity;
import com.pharmacopoeia.activity.health.RecuperateActivity;
import com.pharmacopoeia.activity.main.ActivitiesActivity;
import com.pharmacopoeia.bean.model.HealthTimeBean;
import com.pharmacopoeia.bean.model.HomeBottomModel;
import com.pharmacopoeia.bean.model.Item;
import com.pharmacopoeia.bean.model.WebBeanModel;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.bean.reponse.HealthResponse;
import com.pharmacopoeia.util.DateUtil;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.Lunar;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.view.PinnedSectionListView;
import com.pharmacopoeia.view.cycleView.ImageCycleView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Luhao on 2016/6/3.
 */
public class HealthAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter, SectionIndexer {
    private List<HealthTimeBean> data;
    private List<Item> items;//这个才是真正显示的list
    private Context context;
    private Item[] sections;//头标记数组
    int sectionPosition = 0, listPosition = 0;

    private List<CarouselResponse> list;

    public void setList(List<CarouselResponse> list) {
        this.list = list;
    }

    public void clear() {
        sections = null;
        if (items != null) {
            items.clear();
            items = null;
        }
        if (data != null) {
            data.clear();
        }
        sectionPosition = 0;
        listPosition = 0;
        isFrist = true;
    }

    public HealthAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HealthTimeBean> data, boolean refre) {
        if (refre) {
            clear();
        }
        this.data = data;
        initSection();
    }

    boolean isFrist = true;

    //初始化头信息
    private void initSection() {
        if (items == null) {
            isFrist = true;
            items = new ArrayList<>();
        } else {
            isFrist = false;
        }

        if (sections == null) {
            prepareSections(TestData.getSize(data, isFrist));

        } else {
            Item[] sectio = new Item[TestData.getSize(data, isFrist)];
            Item[] result = Arrays.copyOf(sections, sections.length + sectio.length);
            System.arraycopy(sectio, 0, result, sections.length, sectio.length);
            sections = result;
        }
        if (isFrist) {
            Item section1 = new Item(Item.CAROUSEL, null);
            section1.listPosition = listPosition++;
            section1.sectionPosition = sectionPosition;
            onSectionAdded(section1, section1.listPosition);
            items.add(section1);
            sectionPosition++;
        }
        for (int i = 0; i < data.size(); i++) {
            //添加头信息
            Item section = new Item(Item.SECTION, data.get(i));
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, section.sectionPosition);
            items.add(section);
            for (int j = 0; j < data.get(i).getHealthResponses().size(); j++) {
                HealthResponse healthResponse = data.get(i).getHealthResponses().get(j);
                Item item = null;
                if (healthResponse.getType().equals("article")) {
                    item = new Item(Item.ITEM, data.get(i));
                } else if (healthResponse.getType().equals("video")) {
                    item = new Item(Item.VIDEO, data.get(i));
                } else if (healthResponse.getType().equals("item")) {
                    item = new Item(Item.SHOP, data.get(i));
                }
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                item.setObject(data.get(i).getHealthResponses().get(j));
                items.add(item);


            }
            sectionPosition++;
        }


    }

    //当前view是否属于固定的item
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Item.SECTION;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
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
        if (position < items.size()) {
            return items.get(position);

        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh = null;
        ViewContentHolder viewContentHolder = null;
        ViewVideoHolder viewVideoHolder = null;
        ViewShopHolder viewShopHolder = null;
        ViewHolderImageCycleView viewHolderImageCycleView = null;
        Item item = items.get(position);// 从集合中获取当前行的数据
        int type = getItemViewType(position);
        if (view == null) {
            // 说明当前这一行不是重用的
            // 加载行布局文件，产生具体的一行
            view = setView(type, view,parent);
            // 创建存储一行控件的对象
            switch (type) {
                case Item.SECTION:
                    vh = new ViewHolder();
                    // 将该行的控件全部存储到vh中
                    vh.tvName = (TextView) view.findViewById(R.id.text_text);
                    view.setTag(vh);// 将vh存储到行的Tag中
                    break;
                case Item.ITEM:
                    viewContentHolder = new ViewContentHolder();
                    viewContentHolder.textUser = (TextView) view.findViewById(R.id.text_user);
                    viewContentHolder.textName = (TextView) view.findViewById(R.id.text_name);
                    viewContentHolder.textSelect = (TextView) view.findViewById(R.id.text_select);
                    viewContentHolder.image = (ImageView) view.findViewById(R.id.image);
                    viewContentHolder.tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);
                    view.setTag(viewContentHolder);
                    break;
                case Item.CAROUSEL:
                    viewHolderImageCycleView = new ViewHolderImageCycleView();
                    viewHolderImageCycleView.imageCycleView = (ImageCycleView) view.findViewById(R.id.m_image_cycle);
                    viewHolderImageCycleView.activities = (RelativeLayout) view.findViewById(R.id.activities);
                    viewHolderImageCycleView.online = (RelativeLayout) view.findViewById(R.id.online);
                    viewHolderImageCycleView.recuperate = (RelativeLayout) view.findViewById(R.id.recuperate);
                    viewHolderImageCycleView.tizhi = (RelativeLayout) view.findViewById(R.id.tizhi);
                    viewHolderImageCycleView.nlday = (TextView) view.findViewById(R.id.nlday);
                    viewHolderImageCycleView.year = (TextView) view.findViewById(R.id.year);
                    viewHolderImageCycleView.nlyear = (TextView) view.findViewById(R.id.nlyear);
                    viewHolderImageCycleView.day = (TextView) view.findViewById(R.id.day);
                    viewHolderImageCycleView.bad = (TextView) view.findViewById(R.id.bad);
                    viewHolderImageCycleView.good = (TextView) view.findViewById(R.id.good);
                    viewHolderImageCycleView.goodBadDesc = (TextView) view.findViewById(R.id.good_bad_desc);


                    view.setTag(viewHolderImageCycleView);// 将vh存储到行的Tag中
                    break;
                case Item.VIDEO:
                    viewVideoHolder = new ViewVideoHolder();
                    viewVideoHolder.textUser = (TextView) view.findViewById(R.id.text_user);
                    viewVideoHolder.playNum = (TextView) view.findViewById(R.id.playNum);

                    viewVideoHolder.collectNum = (TextView) view.findViewById(R.id.collectNum);
                    viewVideoHolder.commentNum = (TextView) view.findViewById(R.id.commentNum);
                    viewVideoHolder.shareNum = (TextView) view.findViewById(R.id.shareNum);

                    viewVideoHolder.image = (ImageView) view.findViewById(R.id.image);
                    viewVideoHolder.videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.videoplayer);
                    viewVideoHolder.textVideoName = (TextView) view.findViewById(R.id.text_video_name);
                    view.setTag(viewVideoHolder);// 将vh存储到行的Tag中
                    break;
                case Item.SHOP:
                    viewShopHolder = new ViewShopHolder();
                    viewShopHolder.image = (ImageView) view.findViewById(R.id.image);
                    viewShopHolder.textName = (TextView) view.findViewById(R.id.text_name);
                    viewShopHolder.textMoney = (TextView) view.findViewById(R.id.text_money);
                    viewShopHolder.textBuy = (TextView) view.findViewById(R.id.text_buy);
                    viewShopHolder.tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.flowlayout);
                    view.setTag(viewShopHolder);// 将vh存储到行的Tag中
                    break;
            }

        } else {
            // 取出隐藏在行中的Tag--取出隐藏在这一行中的vh控件缓存对象
            switch (type) {
                case Item.SECTION:
                    vh = (ViewHolder) view.getTag();
                    break;
                case Item.ITEM:
                    viewContentHolder = (ViewContentHolder) view.getTag();
                    break;
                case Item.CAROUSEL:
                    viewHolderImageCycleView = (ViewHolderImageCycleView) view.getTag();
                    break;
                case Item.VIDEO:
                    viewVideoHolder = (ViewVideoHolder) view.getTag();
                    break;
                case Item.SHOP:
                    viewShopHolder = (ViewShopHolder) view.getTag();
                    break;
            }

        }
        // 从ViewHolder缓存的控件中改变控件的值
        // 这里主要是避免多次强制转化目标对象而造成的资源浪费
        switch (type) {
            case Item.SECTION:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateUtil.getInstance().parseDD_SMM_SYY(item.getHealthContentBean().getTime()));
                Lunar lunar = new Lunar(calendar);
                vh.tvName.setText(item.getHealthContentBean().getTime() + "     " + lunar.toString());
                view.setBackgroundColor(parent.getResources().getColor(R.color.white));
                break;
            case Item.ITEM:
                HealthResponse healthContentArticleBean = (HealthResponse) item.getObject();
                viewContentHolder.textName.setText(healthContentArticleBean.getArticleTitle());
                viewContentHolder.textUser.setText(healthContentArticleBean.getAuthorName());
                viewContentHolder.textSelect.setText(healthContentArticleBean.getArticleView());
                ImageLoaderUtil.getInstance().loadNomalImage(healthContentArticleBean.getPicUrl(), viewContentHolder.image);
                setFlowlayout(viewContentHolder.tagFlowLayout, healthContentArticleBean.getTags());
                break;
            case Item.CAROUSEL:

                Calendar calendar1 = Calendar.getInstance();
                Lunar lunar1 = new Lunar(calendar1);
                HomeBottomModel homeBottomModel = OkHttpUtil.getHomeBottomModel();
                if (homeBottomModel != null) {
                    viewHolderImageCycleView.bad.setText(homeBottomModel.getBadevent());
                    viewHolderImageCycleView.good.setText(homeBottomModel.getGoodevent());
                    viewHolderImageCycleView.goodBadDesc.setText(homeBottomModel.getGoodBadDesc());
                }
                viewHolderImageCycleView.nlday.setText(lunar1.toString());
                viewHolderImageCycleView.year.setText(DateUtil.getInstance().simSMM_SYY(new Date()));
                viewHolderImageCycleView.nlyear.setText(lunar1.cyclical() + "年");
                viewHolderImageCycleView.day.setText(DateUtil.getInstance().simdd(new Date()));
                if(list==null){
                    viewHolderImageCycleView.imageCycleView.setVisibility(View.GONE);
                }else if(list.size()==0){
                    viewHolderImageCycleView.imageCycleView.setVisibility(View.GONE);
                }else {
                    viewHolderImageCycleView.imageCycleView.setVisibility(View.VISIBLE);
                    startImageCycle(viewHolderImageCycleView.imageCycleView, list);
                }
                viewHolderImageCycleView.activities.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.openActivity(context, ActivitiesActivity.class);
                    }
                });
                viewHolderImageCycleView.online.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.openActivity(context, OnLineVideoActivity.class);
                    }
                });
                viewHolderImageCycleView.recuperate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.openActivity(context, RecuperateActivity.class);
                    }
                });
                viewHolderImageCycleView.tizhi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();

                        WebBeanModel webBeanModel = new WebBeanModel();
                        webBeanModel.setTitle("体质");
                        webBeanModel.setUrl("http://www.bjtrtwy.com/invest/m/index");

                        bundle.putSerializable("webBeanModel", webBeanModel);

                        IntentUtils.openActivity(context, WebViewActivity.class, bundle);
                    }
                });
                break;
            case Item.VIDEO:

                HealthResponse healthContentVideoBean = (HealthResponse) item.getObject();
                viewVideoHolder.textVideoName.setText(healthContentVideoBean.getVideoTitle());

                viewVideoHolder.textUser.setText(healthContentVideoBean.getAuthorName());
                ImageLoaderUtil.getInstance().loadNomalImage("", viewVideoHolder.image);
                viewVideoHolder.playNum.setText(healthContentVideoBean.getPlayNum() + "次播放");
                viewVideoHolder.collectNum.setText(healthContentVideoBean.getCollectNum());
                viewVideoHolder.commentNum.setText(healthContentVideoBean.getCommentNum());
                viewVideoHolder.shareNum.setText(healthContentVideoBean.getShareNum());

                viewVideoHolder.videoplayer.setUp(healthContentVideoBean.getVideoUrl()
                        , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                ImageLoaderUtil.getInstance().loadNomalImage(healthContentVideoBean.getVideoPic(), viewVideoHolder.videoplayer.thumbImageView);

                break;
            case Item.SHOP:
                HealthResponse healthContentShopBean = (HealthResponse) item.getObject();

                ImageLoaderUtil.getInstance().loadNomalImage(healthContentShopBean.getItemPic(), viewShopHolder.image);
                viewShopHolder.textName.setText(healthContentShopBean.getItemName());
                viewShopHolder.textMoney.setText("100");
                viewShopHolder.textBuy.setText("100"+"人购买");
                setFlowlayout(viewShopHolder.tagFlowLayout, healthContentShopBean.getTags());
                break;
        }
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

    public View setView(int type, View view,ViewGroup parent) {
        switch (type) {
            case Item.SECTION:
                view = LayoutInflater.from(context).inflate(R.layout.health_fragment_item, parent,false);
                break;
            case Item.ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.health_fragment_content, parent,false);

//                view = View.inflate(context, R.layout.health_fragment_content, null);
                break;
            case Item.CAROUSEL:
                view = LayoutInflater.from(context).inflate(R.layout.health_fragment_carousel, parent,false);

//                view = View.inflate(context, R.layout.health_fragment_carousel, null);
                break;
            case Item.VIDEO:
                view = LayoutInflater.from(context).inflate(R.layout.health_fragment_video, parent,false);

//                view = View.inflate(context, R.layout.health_fragment_video, null);
                break;
            case Item.SHOP:
                view = LayoutInflater.from(context).inflate(R.layout.health_fragment_shop, parent,false);

//                view = View.inflate(context, R.layout.health_fragment_shop, null);
                break;
        }
        return view;
    }

    protected void prepareSections(int sectionsNumber) {
        sections = new Item[sectionsNumber];
    }

    protected void onSectionAdded(Item section, int sectionPosition) {
        sections[sectionPosition] = section;
    }


    @Override
    public Item[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex >= sections.length) {
            sectionIndex = sections.length - 1;
        }
        return sections[sectionIndex].listPosition;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position >= getCount()) {
            position = getCount() - 1;
        }
        return getItem(position).sectionPosition;
    }

    public void startImageCycle(ImageCycleView mImageCycle, final List<CarouselResponse> webBeanModelList) {
        if (webBeanModelList == null) {
            return;
        }
        ArrayList<String> imageDescList = new ArrayList<>();
        /**装在数据的集合  图片地址*/
        ArrayList<String> urlList = new ArrayList<>();
        int size = webBeanModelList.size();
        for (int i = 0; i < size; i++) {
            imageDescList.add("北京");
            urlList.add(webBeanModelList.get(i).getFileUrl());
        }
        mImageCycle.setImageResources(imageDescList, urlList, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                ImageLoaderUtil.getInstance().loadNomalImage(imageURL, imageView, R.drawable.default_avatar);
            }

            @Override
            public void onImageClick(int position, View imageView) {
            }
        });
        mImageCycle.startImageCycle();
    }

    // 存储一行中的控件（缓存作用）---避免多次强转每行的控件
    class ViewHolder {
        TextView tvName;
    }

    class ViewContentHolder {
        private TextView textName, textUser, textSelect;
        private ImageView image;
        private TagFlowLayout tagFlowLayout;
    }

    class ViewVideoHolder {
        private TextView textUser, playNum, textVideoName;
        private TextView collectNum,commentNum,shareNum;
        private ImageView image;
        private JCVideoPlayerStandard videoplayer;
    }

    class ViewShopHolder {
        private TextView textName, textMoney, textBuy;
        private TagFlowLayout tagFlowLayout;
        private ImageView image;
    }

    class ViewHolderImageCycleView {
        ImageCycleView imageCycleView;
        RelativeLayout activities;
        RelativeLayout online;
        RelativeLayout tizhi;
        RelativeLayout recuperate;
        TextView nlday, year, nlyear, day;

        private TextView bad, good,goodBadDesc;
    }

}
