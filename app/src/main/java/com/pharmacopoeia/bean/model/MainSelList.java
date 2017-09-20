package com.pharmacopoeia.bean.model;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class MainSelList  {
    private String content;//问题
    private String selseq;//选择项标识
    private List<AssistQuestion> assistQuestionList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSelseq() {
        return selseq;
    }

    public void setSelseq(String selseq) {
        this.selseq = selseq;
    }

    public List<AssistQuestion> getAssistQuestionList() {
        return assistQuestionList;
    }

    public void setAssistQuestionList(List<AssistQuestion> assistQuestionList) {
        this.assistQuestionList = assistQuestionList;
    }
}
