package com.pharmacopoeia.util.db;

import android.app.Activity;
import android.content.Context;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.bean.cache.Setting;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.util.IntentUtils;

/**
 * Created by xus on 2017/7/13.
 */

public class DBUtil {
    private static DBUtil dbUtil;
    private static LiteOrmDBUtil liteOrmDBUtil;

    private DBUtil(Context c) {
        liteOrmDBUtil = LiteOrmDBUtil.getInstance(c);
    }

    public static synchronized DBUtil getInstance(Context c) {
        if (dbUtil == null || liteOrmDBUtil == null) {
            synchronized (DBUtil.class) {
                if (dbUtil == null || liteOrmDBUtil == null) {
                    dbUtil = new DBUtil(c);
                }
            }
        }
        return dbUtil;
    }

    public Setting getSetting() {
        Setting setting = liteOrmDBUtil.getQueryFirst(Setting.class);
        if (setting == null) {
            setting = new Setting();
            setting.setId(1);
            setting.setIsfrist(0);
            setting.setIsLogin(0);
            setting.setMobile(-1);
            liteOrmDBUtil.insert(setting);
        }
        return setting;
    }

    public void setSetting(Setting setting) {
        Setting mySetting = liteOrmDBUtil.getQueryFirst(Setting.class);
        if (mySetting == null) {
            liteOrmDBUtil.insert(setting);
        } else {
            liteOrmDBUtil.update(setting);
        }
        APP.setUser(null);
    }

    public User getUser() {
        Setting setting = getSetting();
        if (setting.getMobile() == -1) {
            return null;
        }
        User user = liteOrmDBUtil.getQueryFirstByWhere(User.class, "mobile", setting.getMobile() + "");
        return user;
    }

    public User setUser(User user) {
        liteOrmDBUtil.set(user);
        return user;
    }

    public void login(User user) {
        Setting setting = getSetting();
        setting.setIsLogin(1);
        setting.setMobile(user.getMobile());
        APP.setUser(user);
        liteOrmDBUtil.set(setting);

        liteOrmDBUtil.set(user);
    }

    public void loginOut() {
        Setting setting = getSetting();
        setting.setIsLogin(0);
        setting.setMobile(-1);
        setting.setMobile(-1);
        APP.setUser(null);
        for (Activity activity : IntentUtils.getActivityList()) {
            activity.finish();
        }
        liteOrmDBUtil.set(setting);
    }

}
