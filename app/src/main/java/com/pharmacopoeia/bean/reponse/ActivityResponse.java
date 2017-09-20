package com.pharmacopoeia.bean.reponse;

/**
 * Created by xus on 2017/9/18.
 */

public class ActivityResponse {
    private String activityFileUrl;
    private String createDate;
    private String fileUrl;

    public String getActivityFileUrl() {
        return activityFileUrl;
    }

    public void setActivityFileUrl(String activityFileUrl) {
        this.activityFileUrl = activityFileUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
