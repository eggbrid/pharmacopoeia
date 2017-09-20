package com.pharmacopoeia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;

/**
 * Created by xus on 2017/7/20.
 */

public class PImageButton extends LinearLayout {

    public void setText(String text) {
        textView.setText(text);
    }

    public PImageButton(Context context) {
        super(context);
        init(context);
    }

    public PImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public PImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    public TextView textView;
    public ImageView imageView;
    public void setImageResource(int res){
        imageView.setImageResource(res);
    }
    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_image_button, null);
        textView = (TextView) view.findViewById(R.id.text);
        imageView = (ImageView) view.findViewById(R.id.image);
        addView(view);

    }
}
