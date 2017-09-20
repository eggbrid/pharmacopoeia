package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.MeRecuperateModel;

/**
 * Created by xus on 2017/9/19.
 */

public class MeRecuperateResponse {
    private MeRecuperateModel type2;
    private MeRecuperateModel type1;
    public MeRecuperateModel getType2() {
        return type2;
    }

    public void setType2(MeRecuperateModel type2) {
        this.type2 = type2;
    }

    public MeRecuperateModel getType1() {
        return type1;
    }

    public void setType1(MeRecuperateModel type1) {
        this.type1 = type1;
    }
}
