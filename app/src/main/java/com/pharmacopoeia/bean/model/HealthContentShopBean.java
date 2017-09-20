package com.pharmacopoeia.bean.model;

import java.util.List;

/**
 * Created by Luhao on 2016/6/3.
 */
public class HealthContentShopBean {

    private String id;
    private String shopUrl;
    private String shopName;
    private String shopMoney;
    private String shopBuys;
    private List<String> list;

    public String getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(String shopMoney) {
        this.shopMoney = shopMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopBuys() {
        return shopBuys;
    }

    public void setShopBuys(String shopBuys) {
        this.shopBuys = shopBuys;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
