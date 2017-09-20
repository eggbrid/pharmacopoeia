/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.pharmacopoeia.view.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.util.ImageLoaderUtil;

public class XListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int NO_DATA = 3;

    private ImageView image1;
    private Context mContext;

    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;

    public XListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void setState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mHintView.setVisibility(View.INVISIBLE);
        if (state == STATE_READY) {
            image1.setVisibility(GONE);
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.xlistview_footer_hint_ready);
//            mHintView.setBackgroundColor(getResources().getColor(R.color.tr));
            mHintView.setBackgroundResource(R.color.tr);

        } else if (state == STATE_LOADING) {
            image1.setVisibility(GONE);
            mProgressBar.setVisibility(View.VISIBLE);
//            mHintView.setBackgroundColor(getResources().getColor(R.color.tr));
            mHintView.setBackgroundResource(R.color.tr);

        }else if (state == STATE_NORMAL) {
            image1.setVisibility(GONE);
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.xlistview_footer_hint_normal);
            mHintView.setBackgroundResource(R.color.tr);

        }  else {
            mHintView.setVisibility(View.GONE);
            image1.setVisibility(VISIBLE);
        }
    }

    public void setBottom(String url){
        ImageLoaderUtil.getInstance().loadNomalImage(url,image1,R.drawable.no_more);
    }

    public void setBottomMargin(int height) {
        if (height < 0) return ;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
        return lp.bottomMargin;
    }


    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mContentView.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        image1=(ImageView) moreView.findViewById(R.id.image1);
        mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView)moreView.findViewById(R.id.xlistview_footer_hint_textview);
    }


}