package com.pharmacopoeia.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.pharmacopoeia.APP;

/**
 * Created by xus on 2017/9/20.
 */

public class SharedUtil {


    public synchronized static void saveString(String name, String value) {
        SharedPreferences sp = APP.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public synchronized static String getString(String name, String dvalue) {
        SharedPreferences preferences = APP.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
        String value = preferences.getString(name, dvalue);
        return value;
    }


}
