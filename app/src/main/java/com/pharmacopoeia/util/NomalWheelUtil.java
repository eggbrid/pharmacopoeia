package com.pharmacopoeia.util;

import android.content.Context;

import com.pharmacopoeia.util.enums.SexEnum;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by xus on 2016/8/9.
 */
public class NomalWheelUtil {
    public static NomalWheelUtil nwu;
    public ArrayList<String> contractModes = new ArrayList<>();

    public synchronized static NomalWheelUtil getInstance() {
        if (nwu == null) {
            nwu = new NomalWheelUtil();
        }
        return nwu;
    }

    public void showWheel(Context cxt, AdapterType type, NomalWheel.OnItemSelectListener c) {
        NomalWheel nomalWheel = new NomalWheel(cxt);
        nomalWheel.show();
        nomalWheel.setAdapter(getAdapter(type));
        nomalWheel.setListener(c);
    }
    public void setAdapterAndShow(Context cxt, ArrayList<String> list, NomalWheel.OnItemSelectListener c) {
        NomalWheel nomalWheel = new NomalWheel(cxt);
        nomalWheel.show();
        nomalWheel.setValues(list);
        nomalWheel.setListener(c);
    }
    public void setAdapterAndShow(Context cxt, NomalWheelAdapter adapter, NomalWheel.OnItemSelectListener c) {
        NomalWheel nomalWheel = new NomalWheel(cxt);
        nomalWheel.show();
        nomalWheel.setAdapter(adapter);
        nomalWheel.setListener(c);
    }

    public NomalWheelAdapter getAdapter(AdapterType type) {
        NomalWheelAdapter a = null;
        switch (type) {


            case SEX:
                a = new NomalWheelAdapter<SexEnum>() {
                    @Override
                    public String getItemName(int pos) {

                        return ((Map<String, String>) SexEnum.toList().get(pos)).get("desc");
                    }

                    @Override
                    public SexEnum getItem(int pos) {
                        return SexEnum.getEnum(((Map<String, String>) SexEnum.toList().get(pos)).get("value"));
                    }

                    @Override
                    public int getSize() {
                        return SexEnum.toList().size();
                    }
                };
                break;

        }
        return a;
    }

    public enum AdapterType {
        SEX
    }
}
