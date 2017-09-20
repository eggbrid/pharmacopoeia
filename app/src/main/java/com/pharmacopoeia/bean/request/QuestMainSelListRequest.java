package com.pharmacopoeia.bean.request;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class QuestMainSelListRequest {
    private List<QuestChildRequest> assistQuestionList;
    private String selseq;

    public List<QuestChildRequest> getAssistQuestionList() {
        return assistQuestionList;
    }

    public void setAssistQuestionList(List<QuestChildRequest> assistQuestionList) {
        this.assistQuestionList = assistQuestionList;
    }

    public String getSelseq() {
        return selseq;
    }

    public void setSelseq(String selseq) {
        this.selseq = selseq;
    }
}
