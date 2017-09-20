package com.pharmacopoeia.bean.model;

/**
 * Created by xus on 2017/9/19.
 */

public class MeRecuperateModel {
    private String oid;//1,
    private String answerTime;//2017-09-16 20:19:06
    private String resultName;//测试病症结果1
    private String resultId;//4

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
}
