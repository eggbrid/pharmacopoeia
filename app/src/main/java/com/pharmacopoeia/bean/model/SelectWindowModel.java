package com.pharmacopoeia.bean.model;

public class SelectWindowModel {
    private String value;
    private int type;
    private String index;

    public SelectWindowModel(String index, String value) {
        super();
        this.value = value;
        this.index = index;
    }

    public SelectWindowModel(String value, int type) {
        super();
        this.value = value;
        this.type = type;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SelectWindowModel [value=" + value + ", type=" + type + "]";
    }

}
