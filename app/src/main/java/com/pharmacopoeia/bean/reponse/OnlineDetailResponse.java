package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.model.HealthContentShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 52243 on 2017/9/8.
 */

public class OnlineDetailResponse {
    private long aliveId;
    private String aliveTitle;
    private String aliveUrl;
    private String alivePic; //主图
    private String collectNum;
    private String commentNum;
    private String shareNum;
    private String playNum;
    private String followNum;
    private String authorId;
    private String authorName;
    private String itemId;
    private String itemName;
    private String collection;
    private String concerned;
    private String createTime;
    private String lastTime;

    private ArrayList<HealthContentArticleBean> healthContentArticleBeen;
    private ArrayList<HealthContentShopBean> healthContentShopBeen;
    private List<Commentbean> comments;

    public ArrayList<HealthContentArticleBean> getHealthContentArticleBeen() {
        return healthContentArticleBeen;
    }

    public void setHealthContentArticleBeen(ArrayList<HealthContentArticleBean> healthContentArticleBeen) {
        this.healthContentArticleBeen = healthContentArticleBeen;
    }

    public ArrayList<HealthContentShopBean> getHealthContentShopBeen() {
        return healthContentShopBeen;
    }

    public void setHealthContentShopBeen(ArrayList<HealthContentShopBean> healthContentShopBeen) {
        this.healthContentShopBeen = healthContentShopBeen;
    }

    public List<Commentbean> getComments() {
        return comments;
    }

    public void setComments(List<Commentbean> comments) {
        this.comments = comments;
    }

    public long getAliveId() {
        return aliveId;
    }

    public void setAliveId(long aliveId) {
        this.aliveId = aliveId;
    }

    public String getAliveTitle() {
        return aliveTitle;
    }

    public void setAliveTitle(String aliveTitle) {
        this.aliveTitle = aliveTitle;
    }

    public String getAliveUrl() {
        return aliveUrl;
    }

    public void setAliveUrl(String aliveUrl) {
        this.aliveUrl = aliveUrl;
    }

    public String getAlivePic() {
        return alivePic;
    }

    public void setAlivePic(String alivePic) {
        this.alivePic = alivePic;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
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

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getConcerned() {
        return concerned;
    }

    public void setConcerned(String concerned) {
        this.concerned = concerned;
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
}
