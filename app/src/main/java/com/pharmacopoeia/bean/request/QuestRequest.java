package com.pharmacopoeia.bean.request;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class QuestRequest {
    private List<QuestMainSelListRequest> selList;

    public List<QuestMainSelListRequest>  getSelList() {
        return selList;
    }

    public void setSelList(List<QuestMainSelListRequest>  selList) {
        this.selList = selList;
    }
}
