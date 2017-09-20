package com.pharmacopoeia.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;


import com.pharmacopoeia.R;
import com.pharmacopoeia.view.wheel.widget.wheel.LocaWheelAdapter;
import com.pharmacopoeia.view.wheel.widget.wheel.OnWheelChangedListener;
import com.pharmacopoeia.view.wheel.widget.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2016/8/9.
 */
public class NomalWheel extends Dialog {
    private WheelView wv_view;
    private Button chance;
    private Button btn_ok;
    private List list;
    private ArrayList<String> values;
    private LinearLayout all_layout;
    private LocaWheelAdapter locaWheelAdapter;
    private Context context;
    private NomalWheelAdapter adapter;
    private OnItemSelectListener listener;
    private int id;
    private LinearLayout date_layout;
    public OnItemSelectListener getListener() {
        return listener;
    }

    public void setListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    public NomalWheel(Context context) {
        super(context, R.style.myDialog);
        this.context = context;
    }

    public NomalWheel(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    protected NomalWheel(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_nomal_wheel_view);
        wv_view = (WheelView) findViewById(R.id.wv_view);
        wv_view.setCyclic(false);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        chance = (Button) findViewById(R.id.chance);
        all_layout = (LinearLayout) findViewById(R.id.all_layout);
        values = new ArrayList<>();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    if(adapter!=null){
                        listener.onItemSelect(adapter.getItem(id));

                    }else{
                        listener.onItemSelect(locaWheelAdapter.getValues());

                    }

                }
                dismiss();
            }
        });
        wv_view.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                NomalWheel.this.id=newValue;
            }
        });

        chance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomalWheel.this.dismiss();
            }
        });
        date_layout = (LinearLayout) findViewById(R.id.date_selelct_layout);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        LinearLayout.LayoutParams lparams_hours = new LinearLayout.LayoutParams(width, (int) (height / 3.5));
        date_layout.setLayoutParams(lparams_hours);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
//        window.setWindowAnimations(R.style.dialogstyle); // 添加动画
        setCancelable(true);
    }

    public void setAdapter(NomalWheelAdapter adapter) {
        this.adapter = adapter;
        for (int i = 0; i < adapter.getSize(); i++) {
            values.add(adapter.getItemName(i));
        }
        locaWheelAdapter=new LocaWheelAdapter(values);
        wv_view.setAdapter(locaWheelAdapter);
        wv_view.setVisibleItems(3);

        setDateNotif();


    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
        setDateNotif();
    }

    public void setDateNotif() {
        if (values != null) {
            wv_view.setCurrentItem(0);
        }
    }

    public interface OnItemSelectListener<T> {
        void onItemSelect(T o);
    }
    public interface OnItemSelectListener2{
        void onItemSelect(int id, String name);
    }
}
