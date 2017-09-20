package com.pharmacopoeia.bean.model;

import java.util.List;

/**
 * Created by xus on 2017/9/11.
 */

public class AssistQuestion  {
    private String content;//问题
    private String qtype;//0单选 1多选
    private String main_selseq;//辅症对应主症标识
    private String seq;//扶正表示
    private List<SelList> selList;

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

    public List<SelList> getSelList() {
        return selList;
    }

    public void setSelList(List<SelList> selList) {
        this.selList = selList;
    }

    public String getMain_selseq() {
        return main_selseq;
    }

    public void setMain_selseq(String main_selseq) {
        this.main_selseq = main_selseq;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
