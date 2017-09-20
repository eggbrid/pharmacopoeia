package com.pharmacopoeia.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 */
public class T {

    private T()
    {
        /** cannot be instantiated**/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static TipsToast toast;

    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = TipsToast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = TipsToast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void show(Context context, String message) {
        if (null == toast) {
            toast = TipsToast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void show(Context context, String message, int duration) {
        if (null == toast) {
            toast = TipsToast.makeText(context, message, duration);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public static void show(Context context, int message, int duration) {
        if (null == toast) {
            toast = TipsToast.makeText(context, message, duration);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    /** Hide the toast, if any. */
    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }


    /*
    class TipsToast extends Toast {
        public TipsToast(Context context) {
            super(context);
        }

        public TipsToast makeText(Context context, CharSequence text)
        {
            TipsToast result = new TipsToast(context);

            LayoutInflater inflate = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.view_tips, null);
            TextView tv = (TextView) v.findViewById(R.id.tips_msg);
            tv.setText(text);

            result.setView(v);
            // setGravity方法用于设置位置，此处为垂直居中
            result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            //result.setDuration(duration);
            return result;
        }

        public TipsToast makeText(Context context, int resId) throws Resources.NotFoundException
        {
            return makeText(context, context.getResources().getText(resId));
        }

        public void setIcon(int iconResId)
        {
            if (getView() == null) {
                throw new RuntimeException(
                        "This Toast was not created with Toast.makeText()");
            }
            ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
            if (iv == null) {
                throw new RuntimeException(
                        "This Toast was not created with Toast.makeText()");
            }
            iv.setImageResource(iconResId);
        }

        @Override
        public void setText(CharSequence s)
        {
            if (getView() == null) {
                throw new RuntimeException(
                        "This Toast was not created with Toast.makeText()");
            }
            TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
            if (tv == null) {
                throw new RuntimeException(
                        "This Toast was not created with Toast.makeText()");
            }
            tv.setText(s);
        }
    }*/
}
