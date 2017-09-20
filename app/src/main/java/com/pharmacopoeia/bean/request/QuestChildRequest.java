package com.pharmacopoeia.bean.request;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class QuestChildRequest {
    private List<QuestSelListRequest> selList;
    private String seq;

    public List<QuestSelListRequest> getSelList() {
        return selList;
    }

    public void setSelList(List<QuestSelListRequest> selList) {
        this.selList = selList;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
