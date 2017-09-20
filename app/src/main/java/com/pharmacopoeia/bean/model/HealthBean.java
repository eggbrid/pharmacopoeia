package com.pharmacopoeia.bean.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luhao on 2016/6/3.
 */
public class HealthBean {

    private ArrayList<WebBeanModel> webBeanModelList;
    private ArrayList<HealthTimeBean> list;

    public ArrayList<WebBeanModel> getWebBeanModelList() {
        return webBeanModelList;
    }

    public void setWebBeanModelList(ArrayList<WebBeanModel> webBeanModelList) {
        this.webBeanModelList = webBeanModelList;
    }

    public ArrayList<HealthTimeBean> getList() {
        return list;
    }

    public void setList(ArrayList<HealthTimeBean> list) {
        this.list = list;
    }
}
