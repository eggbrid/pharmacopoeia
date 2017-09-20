package com.pharmacopoeia.util;

/**
 * Created by xus on 2016/8/10.
 */
public abstract class NomalWheelAdapter<T>{
    public abstract String getItemName(int pos);
    public abstract T getItem(int pos);
    public abstract int getSize();

}