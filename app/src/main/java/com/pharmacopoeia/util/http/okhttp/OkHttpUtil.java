package com.pharmacopoeia.util.http.okhttp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.bean.model.HomeBottomModel;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.ShopDetailResponse;
import com.pharmacopoeia.util.AndroidIDUtil;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by xus on 2017/3/24.
 */

public class OkHttpUtil {
    private static OkHttpClient client;
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();

    private static HomeBottomModel homeBottomModel = null;
    private static CollectionBean shopDetailResponse = null;

    public static CollectionBean getShopDetailResponse() {
        return shopDetailResponse;
    }

    public static void CollectionBean(CollectionBean shopDetailResponse) {
        OkHttpUtil.shopDetailResponse = shopDetailResponse;
    }

    public static HomeBottomModel getHomeBottomModel() {
        return homeBottomModel;
    }

    public static void initHttpUtil() {
        UrlUtil.getUrl();
        client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();
    }

    public static <P> void doGet(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {

        try {

            if (!map.containsKey("mac")) {
                map.put("mac", AndroidIDUtil.getLocalMacAddressFromIp(context));
                map.put("imei", AndroidIDUtil.getSzImei(context));
                map.put("model", AndroidIDUtil.getSystemModel());
                map.put("osVersion", AndroidIDUtil.getSystemVersion());
                map.put("os", "android");
            }
        } catch (Exception e) {
        }
        try {

            String addUrl = "?";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (TextUtils.isEmpty(entry.getValue())) {
                    continue;
                }
                addUrl = addUrl + entry.getKey() + "=" + entry.getValue() + "&";
            }
            addUrl = addUrl.substring(0, addUrl.length() - 1);
            url = url + addUrl;
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runUIError(context, callBack, e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);

                }


            });
        } catch (Exception e) {
            runUIError(context, callBack, e.toString());
        }
    }

    public static Map<String, String> getFromMap(Context context) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("mac", AndroidIDUtil.getLocalMacAddressFromIp(context));
            map.put("imei", AndroidIDUtil.getSzImei(context));
            map.put("model", AndroidIDUtil.getSystemModel());
            map.put("osVersion", AndroidIDUtil.getSystemVersion());
            map.put("os", "android");

        } catch (Exception e) {
        }
        return map;
    }


    public static Map<String, String> getLoginFromMap(Context context) {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("mac", AndroidIDUtil.getLocalMacAddressFromIp(context));
            map.put("imei", AndroidIDUtil.getSzImei(context));
            map.put("model", AndroidIDUtil.getSystemModel());
            map.put("osVersion", AndroidIDUtil.getSystemVersion());
            map.put("os", "android");

            if (APP.getInstance().getUser(context) != null) {
                User user = APP.getInstance().getUser(context);
                map.put("userCode", user.getUserCode());
                map.put("authToken", user.getAuthToken());
            }
//            if (!TextUtils.isEmpty(APP.getInstance().getUserCode(context))){
//                map.put("userCode", APP.getInstance().getUserCode(context));
//            }
        } catch (Exception e) {
        }
        return map;
    }

    public static <P> void doPost(final Activity context, String url, Map<String, String> map, final CallBack callBack, final Class<P> pClass) {
        try {
            if (!map.containsKey("mac")) {
                map.put("mac", AndroidIDUtil.getLocalMacAddressFromIp(context));
                map.put("imei", AndroidIDUtil.getSzImei(context));
                map.put("model", AndroidIDUtil.getSystemModel());
                map.put("osVersion", AndroidIDUtil.getSystemVersion());
                map.put("os", "android");
            }
        } catch (Exception e) {
        }
        try {

            String addUrl = "?";
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (TextUtils.isEmpty(entry.getValue())) {
                    continue;
                }
                formBodyBuilder = formBodyBuilder.add(entry.getKey(), entry.getValue());
                addUrl = addUrl + entry.getKey() + "=" + entry.getValue() + "&";

            }
            FormBody formBody = formBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            Log.e("wangxu", request.url().toString());
            Log.e("wangxu", addUrl);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runUIError(context, callBack, e.toString());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
            runUIError(context, callBack, e.toString());
        }
    }

    public static <P, V> void doPost(final Activity context, String url, V v, final CallBack callBack, final Class<P> pClass) {
        try {

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson(v));
//            FormBody formBody = formBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runUIError(context, callBack, e.toString());
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    setSuccess(context, response, callBack, pClass);
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
            runUIError(context, callBack, e.toString());

        }
    }

    public static <P> void setSuccess(final Activity context, final Response response, final CallBack callBack, final Class<P> pClass) {
        try {
            String json = response.body().string();


            Log.e("wangxu", json);
            ResponseCodeBean responseCodeBean = gson.fromJson(json, ResponseCodeBean.class);
            if (responseCodeBean.getState().equals("0") || responseCodeBean.getState().equals("success")) {
                JsonObject jsonObject = parser.parse(json).getAsJsonObject();

                if (jsonObject.has("bottomPic")) {
                    if (homeBottomModel == null) {
                        homeBottomModel = new HomeBottomModel();
                    }
                    homeBottomModel.setBottomPic(jsonObject.get("bottomPic").getAsString());
                }
                if (jsonObject.has("badevent")) {
                    if (homeBottomModel == null) {
                        homeBottomModel = new HomeBottomModel();
                    }
                    homeBottomModel.setBadevent(jsonObject.get("badevent").getAsString());
                }
                if (jsonObject.has("goodevent")) {
                    if (homeBottomModel == null) {
                        homeBottomModel = new HomeBottomModel();
                    }
                    homeBottomModel.setGoodevent(jsonObject.get("goodevent").getAsString());
                }
                if (jsonObject.has("goodBadDesc")) {
                    if (homeBottomModel == null) {
                        homeBottomModel = new HomeBottomModel();
                    }
                    homeBottomModel.setGoodBadDesc(jsonObject.get("goodBadDesc").getAsString());
                }
                if (jsonObject.has("item")) {
                        shopDetailResponse =new Gson().fromJson(jsonObject.get("item").toString(), CollectionBean.class);
                }


                if (jsonObject.has("data")) {
                    if (pClass != NUllValueResponse.class) {
                        if (jsonObject.get("data").isJsonArray()) {
                            JsonArray data = jsonObject.getAsJsonArray("data");
                            ArrayList<P> jsonObjects = new ArrayList<>();
                            if (data.isJsonNull()) {
                                runUISuccess(context, callBack, jsonObjects);
                            }
                            for (final JsonElement elem : data) {
                                jsonObjects.add(gson.fromJson(elem, pClass));
                            }
                            runUISuccess(context, callBack, jsonObjects);

                        } else {
                            JsonObject data = jsonObject.getAsJsonObject("data");
                            P p = gson.fromJson(data.toString(), pClass);
                            runUISuccess(context, callBack, p);
                        }

                    } else {
                        runUISuccess(context, callBack, null);
                    }

                } else {
                    runUISuccess(context, callBack, null);
                }
            } else {
                runUIError(context, callBack, responseCodeBean.getMsg());
            }
        } catch (Exception e) {
            runUIError(context, callBack, e.toString());
        }
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {// 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static <P> void runUISuccess(final Activity context, final CallBack callBack, final P p) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(p);

            }
        });

    }

    public static <P> void runUIError(final Activity context, final CallBack callBack, final String s) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callBack.onError(s);

            }
        });
    }

    public static <P> String toJson(P p) {
        return gson.toJson(p);
    }

    public static <P> P fromJson(String json, Class<P> p) {
        return gson.fromJson(json, p);
    }
}
