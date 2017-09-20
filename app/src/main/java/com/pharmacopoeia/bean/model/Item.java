package com.pharmacopoeia.bean.model;

/**
 * Created by Luhao on 2016/6/3.
 */
public class Item {
    public static final int ITEM = 0;//判断是否是普通item 文章
    public static final int SECTION = 1;//判断是否是需要置顶悬停的item
    public static final int CAROUSEL = 2;//判断是否是轮播图
    public static final int VIDEO = 3;//判断是否是视频
    public static final int SHOP = 4;//判断是否是商品
    public static final int COMMENTTITLE = 5;//判断是否是评论
    public static final int COMMENT = 6;//判断是否是评论


    public final int type;//外部传入的标记
    public final HealthTimeBean healthContentBean;//外部传入的数据，这里我们将它写成城市实体类，可以任意更换

    private Object object;

    //如果该item是头，则集合标记失效
    public int sectionPosition;//头标记
    public int listPosition;//集合标记


    public Item(int type, HealthTimeBean healthContentBean) {
        this.type = type;
        this.healthContentBean = healthContentBean;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public HealthTimeBean getHealthContentBean() {
        return healthContentBean;
    }
}
