package com.pharmacopoeia.bean.model;

/**
 * Created by xus on 2017/8/24.
 */

public class RecuperateModel {
    private String id;
    private String oid;

    private String type2Name;
    private String type2LogoUrl;
    private String content;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType2Name() {
        return type2Name;
    }

    public void setType2Name(String type2Name) {
        this.type2Name = type2Name;
    }

    public String getType2LogoUrl() {
        return type2LogoUrl;
    }

    public void setType2LogoUrl(String type2LogoUrl) {
        this.type2LogoUrl = type2LogoUrl;
    }
}
