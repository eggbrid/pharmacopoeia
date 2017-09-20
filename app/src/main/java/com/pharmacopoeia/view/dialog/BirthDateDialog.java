package com.pharmacopoeia.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.pharmacopoeia.R;
import com.pharmacopoeia.view.wheel.widget.wheel.NumericWheelAdapter;
import com.pharmacopoeia.view.wheel.widget.wheel.OnWheelChangedListener;
import com.pharmacopoeia.view.wheel.widget.wheel.OnWheelScrollListener;
import com.pharmacopoeia.view.wheel.widget.wheel.WheelView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 日期选择�?
 *
 * @author Administrator
 */
public class BirthDateDialog extends Dialog implements
        View.OnClickListener {
    /**
     * 自定义Dialog监听�?
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(String year, String month, String day);
    }

    private PriorityListener lis;
    private boolean scrolling = false;
    private Context context = null;
    public Button softInfo = null;
    public Button softInfoButton = null;
    private NumericWheelAdapter year_adapter;
    private NumericWheelAdapter month_adapter = null;
    private Button btn_sure = null;
    private Button btn_cancel = null;
    private int curYear = 0;
    private int curMonth = 0;
    private int curDay = 0;
    private WheelView dayview = null;
    private NumericWheelAdapter day_adapter = null;

    private WheelView monthview = null;
    private WheelView yearview = null;
    private static int theme = R.style.myDialog;// 主题
    private LinearLayout date_layout;
    private int width, height;// 对话框宽高
    private TextView title_tv;

    private int gongDay = 0;//0表示年月日，1表示年月，2 表示年
    private String title;
    private int limit = 0;//0表示不限制 1表示限制
    private int startTime = 1900;
    private int endTime=2100;
    private int index;

    public BirthDateDialog(final Context context,
                           final PriorityListener listener, int currentyear, int currentmonth, int curDay,
                           int width,
                           int height, String title, int gongDay, int limit, int startTime,int endTime) {//0表示年月日，1表示年月，2 表示年
        super(context, theme);
        this.context = context;
        lis = listener;
        this.curYear = currentyear;
        this.curMonth = currentmonth;
        this.width = width;
        this.title = title;
        this.curDay = curDay;
        this.height = height;
        this.gongDay = gongDay;
        this.limit = limit;
        this.endTime=endTime;
        this.startTime = startTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_select_wheel);
        btn_sure = (Button) findViewById(R.id.confirm_btn);
        btn_sure.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.cancel_btn);
        btn_cancel.setOnClickListener(this);
        date_layout = (LinearLayout) findViewById(R.id.date_selelct_layout);
        LayoutParams lparams_hours = new LayoutParams(width, (int) (height / 3.5));
        date_layout.setLayoutParams(lparams_hours);
        title_tv = (TextView) findViewById(R.id.diaolog_title_tv);
        title_tv.setText(title);
        yearview = (WheelView) findViewById(R.id.year);
        monthview = (WheelView) findViewById(R.id.month);
        dayview = (WheelView) findViewById(R.id.day);
        if (gongDay == 1) {//日隐藏
            dayview.setVisibility(View.GONE);
        } else if (gongDay == 2) {//月和日隐藏
            dayview.setVisibility(View.GONE);
            monthview.setVisibility(View.GONE);
        }

        final Calendar cal = Calendar.getInstance();

        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    updateDays(yearview, monthview, dayview);
                }
            }
        };


        OnWheelChangedListener listener1 = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {

                    if (index == newValue) {
                        int index_month = 1;
                        if (limit == 1) {
                            index_month = (cal.get(Calendar.MONTH) + 1);
                        }
                        month_adapter = new NumericWheelAdapter(1, index_month, "%02d");
                        monthview.setAdapter(month_adapter);
                        monthview.setCurrentItem(index_month - 1);
                    } else {
                        month_adapter = new NumericWheelAdapter(1, 12, "%02d");
                        monthview.setAdapter(month_adapter);
                        monthview.setCurrentItem(curMonth - 1);
                    }


                    updateDays(yearview, monthview, dayview);
                }
            }
        };

        monthview.addChangingListener(listener);
        yearview.addChangingListener(listener1);
        yearview.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                Log.e("AAA", ((NumericWheelAdapter) wheel.getAdapter()).getValues());
            }
        });
        yearview.setCyclic(false);
        Calendar calendar = Calendar.getInstance();
        if (this.curYear == 0) {
            curYear = calendar.get(Calendar.YEAR);
        }
        if (this.curMonth == 0) {
            curMonth = calendar.get(Calendar.MONTH) + 1;
            curDay = calendar.get(calendar.DAY_OF_MONTH);
        }

        int index_year = endTime;
        int index_month = 12;
        if (limit == 1) {
            index_year = cal.get(Calendar.YEAR);
            index_month = (cal.get(Calendar.MONTH) + 1);
        }
        index = index_year - startTime;
        year_adapter = new NumericWheelAdapter(startTime, index_year);
        int cc = curYear - startTime;// 按下标来�?
        yearview.setAdapter(year_adapter);
        yearview.setCurrentItem(cc);// 传�?过去的是下标
        dayview.setVisibleItems(3);
        yearview.setVisibleItems(3);

        if ((cal.get(Calendar.YEAR)) != curYear) {
            index_month = 12;
        }
        month_adapter = new NumericWheelAdapter(1, index_month, "%02d");
        monthview.setAdapter(month_adapter);
        monthview.setCurrentItem(curMonth - 1);
        monthview.setVisibleItems(3);
        updateDays(yearview, monthview, dayview);

    }

    public int getDay(int year, int month, int index) {
        if (limit == 1) {
            Calendar calendar = Calendar.getInstance();
            int curr_day = calendar.get(Calendar.DATE);
            int curr_year = calendar.get(Calendar.YEAR);
            int curr_month = calendar.get(Calendar.MONTH) + 1;
            if (year == curr_year && curr_month == month) {
                return curr_day;
            } else {
                return index;
            }
        } else {
            return index;
        }
    }

    /**
     */
    private void updateDays(WheelView year, WheelView month, WheelView day) {
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);
        int year_num = year.getCurrentItem() + startTime;
        if (list_big.contains(String.valueOf(month.getCurrentItem() + 1))) {
            day_adapter = new NumericWheelAdapter(1, getDay(year_num, month.getCurrentItem() + 1, 31), "%02d");
        } else if (list_little
                .contains(String.valueOf(month.getCurrentItem() + 1))) {
            day_adapter = new NumericWheelAdapter(1, getDay(year_num, month.getCurrentItem() + 1, 30), "%02d");
        } else {
            if ((year_num % 4 == 0 && year_num % 100 != 0)
                    || year_num % 400 == 0)
                day_adapter = new NumericWheelAdapter(1, getDay(year_num, month.getCurrentItem() + 1, 29), "%02d");
            else
                day_adapter = new NumericWheelAdapter(1, getDay(year_num, month.getCurrentItem() + 1, 28), "%02d");
        }
        dayview.setAdapter(day_adapter);
        dayview.setCurrentItem(curDay - 1);
    }


    public BirthDateDialog(Context context, PriorityListener listener) {
        super(context, theme);
        this.context = context;
    }

    public BirthDateDialog(Context context, String birthDate) {
        super(context, theme);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                lis.refreshPriorityUI(year_adapter.getValues(),
                        month_adapter.getValues(), day_adapter.getValues());
                this.dismiss();
                break;
            case R.id.cancel_btn:
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public int getIntFromString(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        return Integer.parseInt(year_adapter.getValues());
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
