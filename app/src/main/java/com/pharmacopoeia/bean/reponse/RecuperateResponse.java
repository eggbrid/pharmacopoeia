package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.RecuperateModel;

import java.util.List;

/**
 * Created by xus on 2017/8/24.
 */

public class RecuperateResponse {
    private List<RecuperateModel> type1;
    private List<RecuperateModel> type2;

    private List<RecuperateModel> type3;

    public List<RecuperateModel> getType1() {
        return type1;
    }

    public void setType1(List<RecuperateModel> type1) {
        this.type1 = type1;
    }

    public List<RecuperateModel> getType2() {
        return type2;
    }

    public void setType2(List<RecuperateModel> type2) {
        this.type2 = type2;
    }

    public List<RecuperateModel> getType3() {
        return type3;
    }

    public void setType3(List<RecuperateModel> type3) {
        this.type3 = type3;
    }
}
