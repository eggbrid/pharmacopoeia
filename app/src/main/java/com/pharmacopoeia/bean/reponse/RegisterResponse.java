package com.pharmacopoeia.bean.reponse;

/**
 * Created by xus on 2017/8/7.
 */

public class RegisterResponse {
    private String userCode;
    private String authToken;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
