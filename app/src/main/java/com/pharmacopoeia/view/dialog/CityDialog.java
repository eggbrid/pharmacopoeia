package com.pharmacopoeia.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.AreaModel;
import com.pharmacopoeia.util.AreaUtil;
import com.pharmacopoeia.view.wheel.widget.wheel.LocaWheelAdapter;
import com.pharmacopoeia.view.wheel.widget.wheel.OnWheelChangedListener;
import com.pharmacopoeia.view.wheel.widget.wheel.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 滚动选择
 *
 * @author Administrator
 */
public class CityDialog extends Dialog implements
        View.OnClickListener, OnWheelChangedListener {
    private List<AreaModel> listp = new ArrayList<>();
    private List<AreaModel> listct = new ArrayList<>();
    private List<AreaModel> listt = new ArrayList<>();
    //     */
    protected String mCurrentDistrictName = "";
    //
//    /**
//     * 当前区的邮政编码
//     */
    protected String mCurrentZipCode = "";

    /**
     * 解析省市区的XML数据
     */

    private Button btn_sure = null;
    private Button btn_cancel = null;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private static int theme = R.style.myDialog;// 主题
    private LinearLayout date_layout;
    private int width, height;// 对话框宽高
    private TextView title_tv;

    private String title;

    private Context context;
    private OnCitySelectListener onCitySelectListener;

    public CityDialog(final Context context,
                      int width,
                      int height, String title) {
        super(context, theme);
        this.context = context;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public OnCitySelectListener getOnCitySelectListener() {
        return onCitySelectListener;
    }

    public void setOnCitySelectListener(OnCitySelectListener onCitySelectListener) {
        this.onCitySelectListener = onCitySelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_item_dialog);
        btn_sure = (Button) findViewById(R.id.confirm_btn);
        btn_sure.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.cancel_btn);
        btn_cancel.setOnClickListener(this);
        date_layout = (LinearLayout) findViewById(R.id.date_selelct_layout);
        LayoutParams lparams_hours = new LayoutParams(width, (int) (height / 3.5));
        date_layout.setLayoutParams(lparams_hours);
        title_tv = (TextView) findViewById(R.id.diaolog_title_tv);
        title_tv.setText(title);
        mViewProvince = (WheelView) findViewById(R.id.province);
        mViewProvince.setCyclic(false);


        mViewCity = (WheelView) findViewById(R.id.city);
        mViewCity.setCyclic(false);

        mViewDistrict = (WheelView) findViewById(R.id.district);
        mViewDistrict.setCyclic(false);

        setUpListener();
        setUpData();
    }


    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
    }

    private void setUpData() {
//        initProvinceDatas();
//        List<String> list = Arrays.asList(mProvinceDatas);
        listp = AreaUtil.getInstance(context).getPList();
        mViewProvince.setAdapter(new LocaWheelAdapter(listp));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(3);
        mViewCity.setVisibleItems(3);
        mViewDistrict.setVisibleItems(3);
        updateCities();
        updateAreas();

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
//            mCurrentDistrictName = listt.get(newValue).getCounty_name();
//            mCurrentZipCode = listt.get(newValue).getRegion_code();
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        listt = AreaUtil.getInstance(context).getTList(listct.get(pCurrent).getRegion_id());
//        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
//        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
//
//        if (areas == null) {
//            areas = new String[]{""};
//        }
//        List<String> list = Arrays.asList(areas);

        mViewDistrict.setAdapter(new LocaWheelAdapter(listt));
        mViewDistrict.setCurrentItem(0);


    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        listct = AreaUtil.getInstance(context).getCList(listp.get(pCurrent).getRegion_id());
//        mCurrentProviceName = mProvinceDatas[pCurrent];
//        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
//        if (cities == null) {
//            cities = new String[]{""};
//        }
//        List<String> list = Arrays.asList(cities);
        mViewCity.setAdapter(new LocaWheelAdapter(listct));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                showSelectedResult();
                this.dismiss();
                break;
            default:
                this.dismiss();
                break;
        }
    }

    private void showSelectedResult() {
        int c = ((LocaWheelAdapter) mViewDistrict.getAdapter()).getIndex();
        mCurrentDistrictName = listt.get(c).getCounty_name();
        mCurrentZipCode = listt.get(c).getRegion_code();
        onCitySelectListener.onSelect(mCurrentDistrictName,mCurrentZipCode);
    }


    public interface OnCitySelectListener {
        void onSelect(String name, String code);
    }
}
