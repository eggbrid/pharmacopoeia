package com.pharmacopoeia.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.other.EnumCollect;

import java.util.ArrayList;
import java.util.List;


public class IntentUtils {
    private  static List<Activity> activityList = new ArrayList<Activity>();
    public static void addtActivityList(Activity activity) {
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static List<Activity> getActivityList() {
        return activityList;
    }

    public static void openActivity(Context context, Intent intent) {
        mOpenActivity(context,intent, 0, EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Intent intent, int requestCode) {
        mOpenActivity(context,intent, requestCode,EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Intent intent, int requestCode, EnumCollect.OpenAnimType type) {
        mOpenActivity(context,intent,requestCode,type);
    }

    public static void openActivity(Context context, Class<?> pClass) {
        mOpenActivity(context,pClass, null, 0,EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Class<?> pClass, Bundle pBundle) {
        mOpenActivity(context, pClass, pBundle, 0,EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Class<?> pClass, int requestCode) {
        mOpenActivity(context, pClass, null, requestCode,EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Class<?> pClass, Bundle pBundle, int requestCode) {
        mOpenActivity(context,pClass,pBundle,requestCode,EnumCollect.OpenAnimType.PUSH);
    }

    public static void openActivity(Context context, Class<?> pClass, EnumCollect.OpenAnimType type) {
        mOpenActivity(context,pClass, null, 0,type);
    }

    public static void openActivity(Context context, Class<?> pClass, Bundle pBundle, EnumCollect.OpenAnimType type) {
        mOpenActivity(context, pClass, pBundle, 0,type);
    }

    public static void openActivityForResult(Context context, Class<?> pClass, int requestCode, EnumCollect.OpenAnimType type) {
        mOpenActivity(context, pClass, null, requestCode,type);
    }

    public static void openActivity(Context context, Class<?> pClass, Bundle pBundle, int requestCode, EnumCollect.OpenAnimType type) {
        mOpenActivity(context,pClass,pBundle,requestCode,type);
    }

    public static void mOpenActivity(Context context, Class<?> pClass, Bundle pBundle, int requestCode, EnumCollect.OpenAnimType intentFlag) {
        Intent intent = new Intent(context, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);

        }
        if(intentFlag.equals(EnumCollect.OpenAnimType.PUSH)){
            IntentUtils.startPreviewActivity(context, intent, requestCode, R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.PUSH2)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.PRESENT)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.FADE)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.activity_fade_in,R.anim.activity_fade_out);
        }else{
            IntentUtils.startPreviewActivity(context, intent, requestCode,0,0);
        }
    }

    private static void mOpenActivity(Context context, Intent intent, int requestCode, EnumCollect.OpenAnimType intentFlag) {
        if(intentFlag.equals(EnumCollect.OpenAnimType.PUSH)){
            //IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.activity_slide_in_left,R.anim.activity_fade_back);
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.PUSH2)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.PRESENT)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.activity_push_bottom_in,R.anim.activity_push_bottom_out);
        }else if(intentFlag.equals(EnumCollect.OpenAnimType.FADE)){
            IntentUtils.startPreviewActivity(context, intent, requestCode,R.anim.activity_fade_in,R.anim.activity_fade_out);
        }else{
            IntentUtils.startPreviewActivity(context, intent, requestCode,0,0);
        }
    }

    public static void finishActivity(Context context){
        finishPushActivity(context);
    }

    public static void finishPushActivity(Context context){
        ((Activity)context).finish();
        ((Activity)context).overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_right);
    }

    public static void finishPresentActivity(Context context){
        ((Activity)context).finish();
        ((Activity)context).overridePendingTransition(R.anim.activity_push_top_in,R.anim.activity_push_top_out);
    }


    private static long lastClickTime;
    private static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * start screen capture with no delay
     * 
     * @param context
     * @param intent
     */
    private static void startPreviewActivity(Context context, Intent intent, int requestCode, final int enterAnim, final int exitAnim) {
        if(isFastDoubleClick())return;
        startPreviewActivity(context, intent, 0, requestCode,enterAnim,exitAnim);
    }

    /**
     * start screen capture after "delay" milliseconds, so the previous
     * activity's state recover to normal state, such as button click, list item
     * click,wait them to normal state so we can make a good screen capture
     * 
     * @param context
     * @param intent
     * @param delay time in milliseconds
     */
    private static void startPreviewActivity(final Context context, final Intent intent, long delay, final int requestCode, final int enterAnim, final int exitAnim) {
        if(isFastDoubleClick())return;
        final Handler mainThread = new Handler(Looper.getMainLooper());
        final Runnable postAction = new Runnable() {
            @Override
            public void run() {
                try {
                    if (requestCode == 0) {
                        ((Activity)context).startActivity(intent);
                    } else {
                        ((BaseActivity) context).startActivityForResult(intent, requestCode);
                    }

                    if (enterAnim!=0&&exitAnim!=0){
                        ((Activity)context).overridePendingTransition(enterAnim,exitAnim);
                    }
                }catch (Exception e){
                }
            }
        };

        mainThread.post(postAction);
    }
}
