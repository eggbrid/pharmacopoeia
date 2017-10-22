package com.pharmacopoeia.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pharmacopoeia.R;


/**
 * Created by xus on 2016/11/17.
 */

public class ProLoadingDialog extends Dialog {
    private ImageView taiji;
    private TextView text;
    AnimationDrawable ad;
    public ProLoadingDialog(Context context) {
        super(context, R.style.Dialog_Fullscreen);
    }

    public ProLoadingDialog(Context context, int themeResId) {
        super(context, R.style.Dialog_Fullscreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_proload);
        taiji=(ImageView)findViewById(R.id.taiji);
        //设置背景资源
        taiji.setBackgroundResource(R.drawable.pro_animation);
         ad = (AnimationDrawable) taiji.getBackground();
        //开始执行动画
        text=(TextView)findViewById(R.id.text);
    }

    public void show(String s) {
        super.show();
        text.setText(s);
        ad.start();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        ad.stop();

    }
}
