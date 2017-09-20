package com.pharmacopoeia.view;

/**
 * Created by 52243 on 2017/7/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.pharmacopoeia.R;


public class VerticalTextView extends TextView {

    /**
     *    * 绘制整个VerticalTextView区域大小的画笔
     *    
     */
    private Paint mPaint;
    /**
     *    * 绘制每个竖排字符间的间隔横线的画笔
     *    
     */
    private Paint linePaint;
    /**
     *    * 绘制单个字符的画笔
     *    
     */
    private Paint charPaint;

    private char[] indexs;

    private int textCount;
    private float childHeight;

    private String textString;
    private float density;

    public VerticalTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        density = dm.density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextViewColor, defStyle, 0);
        int mBorderColor = a.getColor(R.styleable.VerticalTextViewColor_textColor, getResources().getColor(R.color.c_5b6567));

        mPaint = new Paint();

        linePaint = new Paint();

        linePaint.setAntiAlias(true);

        charPaint = new Paint();
        charPaint.setColor(mBorderColor);
        charPaint.setAntiAlias(true);

        textString = getText().toString();
        indexs = getKeyChar(textString);
        textCount = textString.toCharArray().length;
        a.recycle();
    }


    public int DiptoPx(int dip) {
        return (int) Math.ceil(dip * density + 0.5f);
    }


    boolean is = true;

    @Override
    protected void onDraw(Canvas canvas) {
        childHeight = getHeight() / textCount;
        if (is) {
            is = false;
            childHeight = childHeight - DiptoPx(3);
        }
        if (TextUtils.isEmpty(textString))
            return;
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        canvas.drawColor(Color.WHITE);
        linePaint.setColor(Color.WHITE);

        charPaint.setTextSize((float) (getWidth() * 0.70));
        charPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = charPaint.getFontMetrics();
        for (int i = 0; i < textCount; i++) {
            canvas.drawLine(0, i * childHeight, getWidth(), i * childHeight,
                    linePaint);
            canvas.drawText(
                    String.valueOf(indexs[i]),
                    getWidth() / 2, (float) (((i + 0.5) * childHeight) - (fm.ascent + fm.descent) / 2),
                    charPaint);
        }
    }

    protected char[] getKeyChar(String str) {
        char[] keys = new char[str.length()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = str.charAt(i);
        }
        return keys;
    }
}
