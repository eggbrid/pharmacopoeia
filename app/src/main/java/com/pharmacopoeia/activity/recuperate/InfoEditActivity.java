package com.pharmacopoeia.activity.recuperate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.reponse.LoginResponse;
import com.pharmacopoeia.bean.reponse.QuestionResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.NomalWheel;
import com.pharmacopoeia.util.NomalWheelUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.Utils;
import com.pharmacopoeia.util.enums.SexEnum;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.PImageButton;
import com.pharmacopoeia.view.dialog.BirthDateDialog;
import com.pharmacopoeia.view.dialog.CityDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.darsh.multipleimageselect.helpers.Constants.limit;

/**
 * Created by xus on 2017/8/29.
 */

public class InfoEditActivity extends CommentActivity implements View.OnClickListener, TextWatcher {
    protected ImageView iconName;
    protected EditText name;
    protected ImageView iconSex;
    protected TextView sex;
    protected ImageView iconBirthday;
    protected TextView birthday;
    protected ImageView iconCity;
    protected TextView city;
    protected Button next;
    private BirthDateDialog birthDiolog;

    private String cityCode;
    private String sexCode;
    private String timeCode;

    private String id;


    @Override
    public int setContentView() {
        return R.layout.recuperate_info_edit_activity;
    }

    @Override
    public void initView() throws Exception {
        id = getIntent().getStringExtra("id");
        titleText = (TextView) findViewById(R.id.title_text);
        left = (PImageButton) findViewById(R.id.left);
        right = (PImageButton) findViewById(R.id.right);
        title = (LinearLayout) findViewById(R.id.title);
        iconName = (ImageView) findViewById(R.id.icon_name);
        name = (EditText) findViewById(R.id.name);
        iconSex = (ImageView) findViewById(R.id.icon_sex);
        sex = (TextView) findViewById(R.id.sex);
        iconBirthday = (ImageView) findViewById(R.id.icon_birthday);
        birthday = (TextView) findViewById(R.id.birthday);
        iconCity = (ImageView) findViewById(R.id.icon_city);
        city = (TextView) findViewById(R.id.city);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        setCommentTitleView("基本资料填写");
        birthday.setOnClickListener(this);
        city.setOnClickListener(this);
        sex.setOnClickListener(this);
        name.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkExist();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.next:
                geHisData();
                break;
            case R.id.birthday:
                showDialog(birthday);
                break;
            case R.id.city:
                showAddress(city);
                break;
            case R.id.sex:
                NomalWheelUtil.getInstance().showWheel(this, NomalWheelUtil.AdapterType.SEX, new NomalWheel.OnItemSelectListener<SexEnum>() {
                    @Override
                    public void onItemSelect(SexEnum o) {
                        sex.setText(o.getName());
                        InfoEditActivity.this.sexCode = o.getValue();
                        checkExist();
                    }
                });
                break;
        }
    }

    private void checkExist() {
        next.setEnabled(false);
        if (TextUtils.isEmpty(birthday.getText())) {
            return;
        }
        if (TextUtils.isEmpty(city.getText())) {
            return;
        }
        if (TextUtils.isEmpty(sex.getText())) {
            return;
        }
        if (TextUtils.isEmpty(name.getText())) {

            return;
        }
        next.setEnabled(true);
    }


    public void showDialog(final TextView textView) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String curDate;
        int[] date;
        try {
            curDate = textView.getText().toString();
            if (curDate.indexOf("-") != -1) {
                date = Utils.getYMDArray(curDate, "-");
            } else {
                date = Utils.getYMDArray(curDate, "\\.");
            }

        } catch (Exception e) {
            date = Utils.getYMDArray(com.pharmacopoeia.util.DateUtil.getInstance().simMPoiD_HMS(new Date().getTime() + ""), "\\.");
        }
        Calendar calendar = Calendar.getInstance();
        int endTime = calendar.get(Calendar.YEAR);
        birthDiolog = new BirthDateDialog(this,
                new BirthDateDialog.PriorityListener() {
                    @Override
                    public void refreshPriorityUI(String year, String month, String day) {
                        textView.setText(year + "-" + month + "-" + day);
                        InfoEditActivity.this.timeCode = textView.getText().toString();
                        checkExist();
                    }
                }, date[0], date[1], date[2], width,
                height, "", 0, limit, 1900, endTime);


        Window window = birthDiolog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialogstyle); // 添加动画
        birthDiolog.setCancelable(true);
        birthDiolog.show();
    }


    public void showAddress(final TextView textView) {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        CityDialog moneyDialog = new CityDialog(this, width, height, "");
        Window window = moneyDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialogstyle); // 添加动画
        moneyDialog.setCancelable(true);
        moneyDialog.show();
        moneyDialog.setOnCitySelectListener(new CityDialog.OnCitySelectListener() {
            @Override
            public void onSelect(String name, String code) {
                InfoEditActivity.this.cityCode = code;
                textView.setText(name.trim());
                checkExist();
            }
        });
    }

    public void geHisData() {
        showPross("正在提交..");
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("userName", name.getText().toString());
        map.put("sex", sexCode);
        map.put("birthday", timeCode);
        map.put("regionCode", cityCode);
        OkHttpUtil.doPost(this, UrlUtil.ADDUSER, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                IntentUtils.openActivity(InfoEditActivity.this, QuestionnaireActivity.class, bundle);
                InfoEditActivity.this.finish();
            }

            @Override
            public void onError(String s) {
                dissPross();
                T.show(InfoEditActivity.this, s);
            }
        }, LoginResponse.class);


    }

}
