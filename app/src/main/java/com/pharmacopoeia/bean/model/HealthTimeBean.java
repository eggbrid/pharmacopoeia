package com.pharmacopoeia.bean.model;

import com.pharmacopoeia.bean.reponse.HealthResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 52243 on 2017/7/18.
 */

public class HealthTimeBean {

    private String time;

    private List<HealthResponse> healthResponses;

    public List<HealthResponse> getHealthResponses() {
        return healthResponses;
    }

    public void setHealthResponses(List<HealthResponse> healthResponses) {
        this.healthResponses = healthResponses;
    }

    public String getTime() {
        return time;

    }

    public void setTime(String time) {
        this.time = time;
    }
}

