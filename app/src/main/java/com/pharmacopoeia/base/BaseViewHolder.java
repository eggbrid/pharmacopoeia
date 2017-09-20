package com.pharmacopoeia.base;

import android.view.View;

/**
 * Created by xus on 2016/12/2.
 */

public abstract class BaseViewHolder {
    public BaseViewHolder(){

    }
    public BaseViewHolder(View root) {
        initView(root);
    }

    public abstract void initView(View root);
}
