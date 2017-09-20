package com.pharmacopoeia.bean.model;

/**
 * Created by 52243 on 2017/9/20.
 */

public class ResultTabBean {
    private String sort;
    private String tabName;
    private String tabContent;
    private String tabLogo;
    private boolean isSelect;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getTabContent() {
        return tabContent;
    }

    public void setTabContent(String tabContent) {
        this.tabContent = tabContent;
    }

    public String getTabLogo() {
        return tabLogo;
    }

    public void setTabLogo(String tabLogo) {
        this.tabLogo = tabLogo;
    }
}
