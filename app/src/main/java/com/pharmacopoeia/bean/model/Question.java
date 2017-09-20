package com.pharmacopoeia.bean.model;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class Question {
    private String content;//问题
    private String qtype;//0单选 1多选
    private List<MainSelList> selList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public List<MainSelList> getSelList() {
        return selList;
    }

    public void setSelList(List<MainSelList> selList) {
        this.selList = selList;
    }
}
