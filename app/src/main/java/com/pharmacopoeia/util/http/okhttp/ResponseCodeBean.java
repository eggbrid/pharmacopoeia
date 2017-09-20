package com.pharmacopoeia.util.http.okhttp;

/**
 * Created by xus on 2017/3/24.
 */

public class ResponseCodeBean {
    private String state;
    private String msg;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
