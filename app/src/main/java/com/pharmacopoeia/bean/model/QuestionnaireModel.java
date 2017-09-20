package com.pharmacopoeia.bean.model;

import com.pharmacopoeia.interfaces.enums.QuestType;

/**
 * Created by xus on 2017/8/30.
 */

public class QuestionnaireModel {
    public QuestionnaireModel() {

    }

    public QuestionnaireModel(Question question, String value) {
        this.isMainQuest = true;
        this.type = QuestType.getEnum(question.getQtype(), question.getSelList().size()).getType();
        this.title = question.getContent();
        this.value = value;
        if (question.getSelList() != null && question.getSelList().size() > 0) {
            this.q1 = question.getSelList().get(0).getContent();
            this.v1 = question.getSelList().get(0).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 1) {
            this.q2 = question.getSelList().get(1).getContent();
            this.v2 = question.getSelList().get(1).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 2) {
            this.q3 = question.getSelList().get(2).getContent();
            this.v3 = question.getSelList().get(2).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 3) {
            this.q4 = question.getSelList().get(3).getContent();
            this.v4 = question.getSelList().get(3).getSelseq();
        }
    }

    public QuestionnaireModel(AssistQuestion question) {
        this.isMainQuest = false;
        this.type = QuestType.getEnum(question.getQtype(), question.getSelList().size()).getType();
        this.title = question.getContent();
        if (question.getSelList() != null && question.getSelList().size() > 0) {
            this.q1 = question.getSelList().get(0).getContent();
            this.v1 = question.getSelList().get(0).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 1) {
            this.q2 = question.getSelList().get(1).getContent();
            this.v2 = question.getSelList().get(1).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 2) {
            this.q3 = question.getSelList().get(2).getContent();
            this.v3 = question.getSelList().get(2).getSelseq();
        }
        if (question.getSelList() != null && question.getSelList().size() > 3) {
            this.q4 = question.getSelList().get(3).getContent();
            this.v4 = question.getSelList().get(3).getSelseq();
        }
        this.pValue=question.getMain_selseq();
        this.seq=question.getSeq();
    }

    private String pValue;
    private String seq;

    private String type;
    private String value;
    private String title;
    private String q1;
    private String v1;
    private String q2;
    private String v2;
    private String q3;
    private String v3;
    private String q4;
    private String v4;

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    private boolean isMainQuest = false;

    public boolean isMainQuest() {
        return isMainQuest;
    }

    public void setMainQuest(boolean mainQuest) {
        isMainQuest = mainQuest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
