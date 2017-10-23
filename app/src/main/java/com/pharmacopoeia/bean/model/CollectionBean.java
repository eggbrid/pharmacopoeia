package com.pharmacopoeia.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xus on 2017/7/20.
 */

public class CollectionBean implements Serializable {
    //    private String title;//标题
//    private List<String> tags;//标签
//    private String useNum;//已用数目
//    private String image;//图片
    private String itemId;// 10000,
    private String itemName;//六味地黄丸
    private String cateId;// 6,
    private String cateName;//美颜
    private String authorId;// 2,
    private String authorName;//同仁堂测试2
    private String itemPic;//http://123.56.216.53/images/goodsPic/20170828/1503852432974.jpg
    private String detailPics;// null,
    private String tags;//活血化瘀,中医,针灸
    private String clickNum;// 0,
    private String application;// null,
    private String presSourcePic;// null,
    private String edibleMethodPic;// null,
    private String constituentPic;// null,
    private String forPeoplePic;// null,
    private String videoId;// 0,
//    private String properties;// null,
    private String createTime;//2017-08-28 02:15:42
    private String lastTime;//2017-08-29 00:10:11"
    private String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public String getDetailPics() {
        return detailPics;
    }

    public void setDetailPics(String detailPics) {
        this.detailPics = detailPics;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getClickNum() {
        return clickNum;
    }

    public void setClickNum(String clickNum) {
        this.clickNum = clickNum;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPresSourcePic() {
        return presSourcePic;
    }

    public void setPresSourcePic(String presSourcePic) {
        this.presSourcePic = presSourcePic;
    }

    public String getEdibleMethodPic() {
        return edibleMethodPic;
    }

    public void setEdibleMethodPic(String edibleMethodPic) {
        this.edibleMethodPic = edibleMethodPic;
    }

    public String getConstituentPic() {
        return constituentPic;
    }

    public void setConstituentPic(String constituentPic) {
        this.constituentPic = constituentPic;
    }

    public String getForPeoplePic() {
        return forPeoplePic;
    }

    public void setForPeoplePic(String forPeoplePic) {
        this.forPeoplePic = forPeoplePic;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

//    public String getProperties() {
//        return properties;
//    }
//
//    public void setProperties(String properties) {
//        this.properties = properties;
//    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
