package com.pharmacopoeia.bean.model;

/**
 * Created by 雪元 on 2016/7/21.
 */
public class ShareItem {

    private int id;
    private String name;

    public ShareItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
