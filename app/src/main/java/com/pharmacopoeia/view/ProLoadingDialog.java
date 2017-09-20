package com.pharmacopoeia.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pharmacopoeia.R;


/**
 * Created by xus on 2016/11/17.
 */

public class ProLoadingDialog extends Dialog {
    private ProgressBar taiji;
    private TextView text;
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
        taiji=(ProgressBar)findViewById(R.id.taiji);
        text=(TextView)findViewById(R.id.text);
    }

    public void show(String s) {
        super.show();
        text.setText(s);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
