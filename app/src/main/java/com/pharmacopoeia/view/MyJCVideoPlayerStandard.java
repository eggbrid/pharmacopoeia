package com.pharmacopoeia.view;

import android.content.Context;
import android.util.AttributeSet;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by xus on 2017/9/21.
 */

public class MyJCVideoPlayerStandard extends JCVideoPlayerStandard {

    public OnStart onStart=new OnStart() {
        @Override
        public void onStartVideo() {

        }
    };

    public OnStart getOnStart() {
        return onStart;
    }

    public void setOnStart(OnStart onStart) {
        this.onStart = onStart;
    }

    public MyJCVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJCVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startVideo() {
        super.startVideo();
        onStart.onStartVideo();
    }

    public interface OnStart {
        void onStartVideo();
    }

}
