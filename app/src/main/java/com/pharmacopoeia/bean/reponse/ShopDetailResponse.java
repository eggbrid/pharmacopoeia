package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.model.ShopPropertiesModel;

import java.util.List;

/**
 * Created by 52243 on 2017/9/11.
 */

public class ShopDetailResponse {
    private String itemId;
    private String itemName;
    private String cateId;
    private String cateName;
    private String authorId;
    private String authorName;
    private String itemPic;
    private String detailPics;
    private String tags;
    private String clickNum;
    private String application;
    private String presSourcePic;
    private String edibleMethodPic;
    private String constituentPic;
    private String forPeoplePic;
    private String videoId;
    private String createTime;
    private String lastTime;
    private List<ShopPropertiesModel> properties;
    private List<Commentbean> commentList;
    private String videoUrl;
    private String videoPic;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public List<Commentbean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Commentbean> commentList) {
        this.commentList = commentList;
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

    public List<ShopPropertiesModel> getProperties() {
        return properties;
    }

    public void setProperties(List<ShopPropertiesModel> properties) {
        this.properties = properties;
    }
}
