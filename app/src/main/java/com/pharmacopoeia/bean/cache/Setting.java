package com.pharmacopoeia.bean.cache;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by xus on 2017/7/13.
 */
@Table("tb_setting")
public class Setting {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id=1;

    private int isfrist;//0为第一次 1为多次
    private int isLogin;// 为未登录 1为已登录
    private long verificationTime;//上次验证码时间
    private long mobile;//手机号

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsfrist() {
        return isfrist;
    }

    public void setIsfrist(int isfrist) {
        this.isfrist = isfrist;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public long getVerificationTime() {
        return verificationTime;
    }

    public void setVerificationTime(long verificationTime) {
        this.verificationTime = verificationTime;
    }
}
