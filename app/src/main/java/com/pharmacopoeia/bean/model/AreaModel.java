package com.pharmacopoeia.bean.model;

import android.text.TextUtils;

/**
 * Created by xus on 2017/8/8.
 */

public class AreaModel {
    private String city_name;
    private String county_name;
    private String father_id;
    private String province_name;
    private String region_code;
    private String region_id;
    private String region_name;

    public String getCity_name() {
        return city_name;
    }

    public String getCounty_name() {
        return county_name;
    }

    public String getFather_id() {
        return father_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public String getRegion_id() {
        return region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    @Override
    public String toString() {
        if (!TextUtils.isEmpty(province_name)){
            return province_name;
        }
        if (!TextUtils.isEmpty(city_name)){
            return city_name;
        }
        if (!TextUtils.isEmpty(county_name)){
            return county_name;
        }
        if (!TextUtils.isEmpty(region_name)){
            return region_name;
        }
        return super.toString();
    }
}
