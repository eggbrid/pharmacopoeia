package com.pharmacopoeia.base;

import android.os.Bundle;
import android.util.Log;


/**
 * Created by xus on 2016/11/15.
 */

public abstract class CommentActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        beforeSet();
        super.onCreate(savedInstanceState);
        setContentView(setContentView());
        try {
            initView();
        } catch (Exception e) {
            Log.e("wangxu",e.toString());
            e.printStackTrace();
        }
    }
    public void beforeSet(){

    }

}
