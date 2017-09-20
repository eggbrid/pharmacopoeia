package com.pharmacopoeia.bean.model;

/**
 * Created by xus on 2017/9/11.
 */

public class SelList {
    private String content;//问题
    private String selseq;//选择项标识

    public String getSelseq() {
        return selseq;
    }

    public void setSelseq(String selseq) {
        this.selseq = selseq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
