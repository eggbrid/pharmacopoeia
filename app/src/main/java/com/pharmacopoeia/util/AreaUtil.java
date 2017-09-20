package com.pharmacopoeia.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pharmacopoeia.bean.model.AreaModel;
import com.pharmacopoeia.bean.reponse.AreaResponse;
import com.pharmacopoeia.util.db.DBUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/8/8.
 */

public class AreaUtil {
    private static AreaUtil areaUtil = null;
    private List<AreaModel> list = new ArrayList<>();
    private Map<String, List<AreaModel>> p = new HashMap<>();//省
    private Map<String, List<AreaModel>> ct = new HashMap<>();//市
    private Map<String, List<AreaModel>> t = new HashMap<>();//县

    private AreaUtil(Context c) {
        init(c);
    }

    public synchronized static AreaUtil getInstance(Context c) {

        if (areaUtil == null) {
            synchronized (DBUtil.class) {
                if (areaUtil == null) {
                    areaUtil = new AreaUtil(c);
                }
            }
        }
        return areaUtil;
    }

    private void init(Context c) {
        String cityString = null;
        try {
            InputStream is = c.getResources().getAssets().open("city.txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            cityString = new String(buffer, "utf-8");
//            cityString = inputStream2String(c.getResources().getAssets().open("city.json"));
            Gson g = new Gson();
            AreaResponse a = g.fromJson(cityString, AreaResponse.class);
            list = a.getData();
            for (AreaModel area : list) {
                if (area.getFather_id().equals("1")) {
                    List<AreaModel> l;
                    if (p.containsKey("1")) {
                        l = p.get("1");
                    } else {
                        l = new ArrayList<>();
                    }
                    l.add(area);
                    p.put("1", l);
                }

                if (!TextUtils.isEmpty(area.getCity_name())) {
                    if (area.getCity_name().equals(area.getProvince_name())) {
                        List<AreaModel> l;
                        if (ct.containsKey(area.getRegion_id())) {
                            l = ct.get(area.getRegion_id());
                        } else {
                            l = new ArrayList<>();
                        }
                        l.add(area);
                        ct.put(area.getRegion_id(), l);
                    } else {
                        List<AreaModel> l;
                        if (ct.containsKey(area.getFather_id())) {
                            l = ct.get(area.getFather_id());
                        } else {
                            l = new ArrayList<>();
                        }
                        l.add(area);
                        ct.put(area.getFather_id(), l);
                    }


                }

                if (!TextUtils.isEmpty(area.getCounty_name())) {
                    List<AreaModel> l;
                    if (t.containsKey(area.getFather_id())) {
                        l = t.get(area.getFather_id());
                    } else {
                        l = new ArrayList<>();
                    }
                    l.add(area);
                    t.put(area.getFather_id(), l);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AreaModel> getPList() {
        return p.get("1");
    }

    public List<AreaModel> getCList(String pid) {
        return ct.get(pid);
    }

    public List<AreaModel> getTList(String pid) {
        return t.get(pid);
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


}
