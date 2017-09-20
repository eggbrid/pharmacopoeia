package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.ResultInfo;
import com.pharmacopoeia.bean.model.ResultTabBean;

import java.util.List;

/**
 * Created by 52243 on 2017/9/20.
 */

public class SystemResultResponse {
    private List<ShopDetailResponse> items;
    private List<ResultTabBean> tabs;
    private ResultInfo resultInfo;

    public List<ShopDetailResponse> getItems() {
        return items;
    }

    public void setItems(List<ShopDetailResponse> items) {
        this.items = items;
    }

    public List<ResultTabBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<ResultTabBean> tabs) {
        this.tabs = tabs;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
