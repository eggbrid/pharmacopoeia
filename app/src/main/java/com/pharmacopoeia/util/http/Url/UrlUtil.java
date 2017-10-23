package com.pharmacopoeia.util.http.Url;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.pharmacopoeia.APP;


/**
 * Created by xus on 2017/3/25.
 */

public class UrlUtil {
    private static String baseUrl = "";

    public static String getUrl() {
        if (TextUtils.isEmpty(baseUrl)) {
            ApplicationInfo info = null;
            try {
                info = APP.getInstance().getPackageManager().getApplicationInfo(APP.getInstance().getPackageName(), PackageManager.GET_META_DATA);
                baseUrl = info.metaData.getString("hostIp");
            } catch (PackageManager.NameNotFoundException e) {

            }
            if (TextUtils.isEmpty(baseUrl)) {
                baseUrl = "http://123.56.216.53:8080/";
            }
        }
        return baseUrl;
    }

    public static String TEST = getUrl() + "trt_service/article/articleQuery"; //测试
    public static String GETSMSCODE = getUrl() + "trt_service/user/getsmscode";//发送验证码
    public static String REGISTER = getUrl() + "trt_service/user/register";//注册
    public static String LOGIN = getUrl() + "trt_service/user/login";//登录
    public static String SOLAR = getUrl() + "trt_service/getFileListByType/2";//节气
    public static String ACTIVITY = getUrl() + "trt_service/getFileListByType/3";//活动
    public static String CAROUSEL = getUrl() + "trt_service/getFileListByType/1";//轮播图
    public static String OPEN = getUrl() + "trt_service/getFileListByType/9";//开平
    public static String OPEN2 = getUrl() + "trt_service/getFileListByType/10";//开平


    public static String USERINFO = getUrl() + "trt_service/user/userinfo/get";//获取用户信息
    public static String UPDATEUSERINFO = getUrl() + "trt_service/user/userinfo/perfect";//修改用户信息
    public static String RESETPWD = getUrl() + "trt_service/user/mod/pwd";//修改密码
    public static String CHECKMOBILE = getUrl() + "trt_service/user/check/code";//检查验证码

    public static String TY2TYPE = getUrl() + "trt_service/ty2type/list";//调养
    public static String VIDEOLIST = getUrl() + "trt_service/video/videoQuery";//视频列表
    public static String VIDEODETAIL = getUrl() + "trt_service/video/videoDetail";//视频列表
    public static String CATELIST = getUrl() + "/trt_service/item/cateQuery";//商品分類
    public static String SHOPLIST = getUrl() + "/trt_service/item/itemQuery";//商品列表
    public static String articleList = getUrl() + "/trt_service/article/articleQuery";//文字列表
    public static String ARTICLEDETAIL = getUrl() + "/trt_service/article/articleDetail";//文章详情
    public static String GETQUESTIONLISTBYID = getUrl() + "//trt_service/wj/getQuestionListById";//文章详情
    public static String SHOPDETAIL = getUrl() + "/trt_service/item/itemDetail";//商品詳情
    public static String SHOPDETAILCOMMENT = getUrl() + "/trt_service/item/commentQuery";//商品詳情评论
    public static String SHOPDETAILCOMMENTINSERT = getUrl() + "/trt_service/item/commentInsert";//商品詳情评论添加
    public static String HOME = getUrl() + "/trt_service/home/homeQuery";//首页
    public static String ARTICLECOMMENTINSERT = getUrl() + "/trt_service/article/commentInsert";//文章评论添加

    public static String ARTICLECOMMENTQUERY = getUrl() + "/trt_service/article/commentQuery";//文章评论添加

    public static String GETLASTANSWER = getUrl() + "/trt_service/wj/getLastAnswer";//获取我的调养
    public static String ADDUSER = getUrl() + "/trt_service/wj/adduser";//添加子用户
    public static String ANSWER = getUrl() + "/trt_service/wj/answer";//添加提交问卷
    public static String GETRESULTITEMBYID = getUrl() + "/trt_service/wj/getResultItemById";//添加提交问卷
    public static String VIDEODETAILCOMMENTINSERT = getUrl() + "/trt_service/video/commentInsert";//視頻詳情评论添加

    public static String COLLECTION = getUrl() + "/trt_service/collection/collectionAdd";//收藏

    public static String ALIVEQUERY= getUrl() + "/trt_service/alive/aliveQuery";//視頻直播查詢
    public static String COLLECTIONCANCEL= getUrl() + "/trt_service/collection/collectionCancel";//取消收藏
    public static String FOLLOWPUBLISHER= getUrl() + "/trt_service/user/follow/publisher";//关注发布者
    public static String ALIVEDETAIL = getUrl() + "/trt_service/alive/aliveDetail";//直播详情
    public static String ALIVECOMMENTINSERT = getUrl() + "/trt_service/alive/commentInsert";//直播详情
    public static String VIDEOUPDATE = getUrl() + "/trt_service/video/videoUpdate";//直播详情


}
