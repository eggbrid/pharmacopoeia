package com.pharmacopoeia.bean.reponse;

import com.pharmacopoeia.bean.model.Question;

/**
 * Created by xus on 2017/9/11.
 */

public class QuestionResponse {
    private String oid;//问题id？
    private Question question;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
