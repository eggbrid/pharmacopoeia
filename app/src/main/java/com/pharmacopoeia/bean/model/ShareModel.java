package com.pharmacopoeia.bean.model;

import android.text.TextUtils;

/**
 * Created by xus on 2016/8/15.
 */
public class ShareModel {
    private String titleUrl;
    private String title;
    private String text;
    private String imageUrl;
    private String site;
    private String siteUrl;
    private String ewmTitle;
    private String ewmName;

    public String getEwmTitle() {
        return ewmTitle;
    }

    public void setEwmTitle(String ewmTitle) {
        this.ewmTitle = ewmTitle;
    }

    public String getEwmName() {
        return ewmName;
    }

    public void setEwmName(String ewmName) {
        this.ewmName = ewmName;
    }

    public ShareModel(){

    }

    /**
     *
     * @param titleUrl title的url
     * @param title 标题
     * @param text 内容
     * @param imageUrl icon
     * @param site 公司名称
     * @param siteUrl 公司网址
     */
    public ShareModel(String titleUrl, String title, String text, String imageUrl, String site, String siteUrl) {
        this.titleUrl = titleUrl;
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
        this.site = site;
        this.siteUrl = siteUrl;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getTitle() {
        return TextUtils.isEmpty(title)?"众筑网":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return  imageUrl;
//        return TextUtils.isEmpty(imageUrl)?"http://www.lagou.com/i/image/M00/08/85/Cgp3O1bQDcuATWVUAABLUkrC4l4758.png":imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
