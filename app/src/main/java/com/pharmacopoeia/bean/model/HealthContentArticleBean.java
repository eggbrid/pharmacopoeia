package com.pharmacopoeia.bean.model;

import java.util.List;

/**
 * Created by Luhao on 2016/6/3.
 */
public class HealthContentArticleBean {

    //    private String id;
//    private String name;
    private String url;
//    private String userName;
//    private String selects;
//    private List<String> lists;

    private String articleId;// 10000,
    private String articleTitle;//中医养生长寿三大法宝：养精，养气，养神111
    private String authorId;// 4,
    private String authorName;//同仁堂
    private String tags;//中医养生,养生
    private String picUrl;
    private String articleContent;// null,
    private String articleView;// 0,
    private String createTime;//2017-07-13 23:30
    private String lastTime;//2017-08-29 01:46:17 private String
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    //    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getSelects() {
//        return selects;
//    }
//
//    public void setSelects(String selects) {
//        this.selects = selects;
//    }
//
//    public List<String> getLists() {
//        return lists;
//    }
//
//    public void setLists(List<String> lists) {
//        this.lists = lists;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
}
