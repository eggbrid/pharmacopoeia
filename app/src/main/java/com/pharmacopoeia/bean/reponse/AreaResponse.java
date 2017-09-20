package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.AreaModel;

import java.util.List;

/**
 * Created by xus on 2017/8/8.
 */

public class AreaResponse {
    private List<AreaModel> data;

    public List<AreaModel> getData() {
        return data;
    }

    public void setData(List<AreaModel> data) {
        this.data = data;
    }
}
