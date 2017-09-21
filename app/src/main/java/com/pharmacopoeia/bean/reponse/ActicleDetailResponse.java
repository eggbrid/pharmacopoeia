package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.Commentbean;

import java.util.List;

/**
 * Created by xus on 2017/9/15.
 */

public class ActicleDetailResponse {
    private String articleId;//10000,
    private String articleTitle;//中医养生长寿三大法宝：养精，养气，养神111
    private String authorId;//4,
    private String authorName;//同仁堂
    private String tags;//中医养生,养生
    private String articleContent;//<p>中医学吸收了各家养生学说的精华....111</p>
    private String articleView;//0,
    private String picUrl;//http://123.56.216.53/images/20170814/1502689353146.jpg
    private String itemId;//10000,
    private String createTime;//2017-07-13 23:30
    private String lastTime;//2017-08-29 01:46:17private String
    private List<Commentbean> commentList;
    private String concerned;
    private String collection;

    public String getConcerned() {
        return concerned;
    }

    public void setConcerned(String concerned) {
        this.concerned = concerned;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleView() {
        return articleView;
    }

    public void setArticleView(String articleView) {
        this.articleView = articleView;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public List<Commentbean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Commentbean> commentList) {
        this.commentList = commentList;
    }
}
