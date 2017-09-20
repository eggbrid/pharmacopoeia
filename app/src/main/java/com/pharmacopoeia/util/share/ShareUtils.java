package com.pharmacopoeia.util.share;

import android.app.Activity;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.bean.model.ShareItem;
import com.pharmacopoeia.bean.model.ShareModel;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.view.dialog.ShareDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by yanghaitao on 16/7/14.
 */
public class ShareUtils {

    public static List<ShareItem> items;
    public static void addItem() {
        if (items == null) {
            items = new ArrayList<>();
            items.add(new ShareItem(R.drawable.weiixn, "微信"));
            items.add(new ShareItem(R.drawable.penyouquan, "微信朋友圈"));
            items.add(new ShareItem(R.drawable.weixinshoucang, "微信收藏"));
            items.add(new ShareItem(R.drawable.weibo, "新浪微博"));
            items.add(new ShareItem(R.drawable.qq, "qq"));
            items.add(new ShareItem(R.drawable.kongjian, "qq空间"));
        }
    }

    public static void showShare(final BaseActivity BaseActivity, View view) {
        showShareNoERW(BaseActivity, view, new ShareModel("http://www.baidu", "测试", "", "", "测试", "http://www.baidu"));
    }



    public static void showShareNoERW(final BaseActivity BaseActivity, View view, final ShareModel model) {
        addItem();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) BaseActivity).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        ShareDialog shareDialog = new ShareDialog(BaseActivity, new ShareDialog.MapJobOnItemClick() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ShareUtils.shareWechat(BaseActivity, model);
                        break;
                    case 1:
                        ShareUtils.shareWechatWechatMoments(BaseActivity, model);
                        break;
                    case 2:
                        ShareUtils.shareWechatFavorite(BaseActivity, model);
                        break;
                    case 3:
                        ShareUtils.shareWeiBo(BaseActivity, model);
                        break;
                    case 4:
                        ShareUtils.shareQQ(BaseActivity, model);
                        break;
                    case 5:
                        ShareUtils.shareQZone(BaseActivity, model);
                        break;

                    default:
                        break;
                }
            }
        }, width, height, items);
        Window window = shareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialogstyle); // 添加动画
        shareDialog.setCancelable(true);
        shareDialog.show();

    }

    public static void shareWeiBo(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"

        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(model.getTitle());
        sp.setText(model.getTitle() + "→" + model.getText() + "点击链接查看吧~ " + model.getTitleUrl());
        sp.setImageUrl(model.getImageUrl());
        shareAction(mBaseActivity, sp, SinaWeibo.NAME);
    }

    public static void shareWeiBoImage(BaseActivity mBaseActivity, String text, String path) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setText(text);
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setImagePath(path);
        shareAction(mBaseActivity, sp, SinaWeibo.NAME);
    }

    public static void shareQQ(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"

        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(model.getTitle());
        sp.setTitleUrl(model.getTitleUrl()); // 标题的超链接
        sp.setText(model.getText());
        sp.setImageUrl(model.getImageUrl());
        sp.setSite(model.getSite());
        sp.setSiteUrl(model.getSiteUrl());

        shareAction(mBaseActivity, sp, QQ.NAME);
    }

    public static void shareQQImage(BaseActivity mBaseActivity, String text, String path) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setText(text);
        sp.setImagePath(path);
        sp.setShareType(Platform.SHARE_IMAGE);
        shareAction(mBaseActivity, sp, QQ.NAME);
    }

    public static void shareQZone(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(model.getTitle());
        sp.setTitleUrl(model.getTitleUrl()); // 标题的超链接
        sp.setText(model.getText());
        sp.setImageUrl(model.getImageUrl());
        sp.setSite(model.getSite());
        sp.setSiteUrl(model.getSiteUrl());
        sp.setUrl(model.getTitleUrl());
        shareAction(mBaseActivity, sp, QZone.NAME);
    }

    public static void shareWechat(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"

        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性

        sp.setTitle(model.getTitle());
        sp.setText(model.getText());
        sp.setImagePath(model.getImageUrl());
        sp.setUrl(model.getTitleUrl());

        shareAction(mBaseActivity, sp, Wechat.NAME);
    }

    public static void shareWechatImage(BaseActivity mBaseActivity, String text, String path) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setText(text);
        sp.setImagePath(path);
        shareAction(mBaseActivity, sp, Wechat.NAME);
    }

    public static void shareWechatWechatMomentsImage(BaseActivity mBaseActivity, String text, String path) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_IMAGE);
        sp.setText(text);
        sp.setImagePath(path);
        shareAction(mBaseActivity, sp, WechatMoments.NAME);
    }
    public static void shareWechatWechatMoments(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
        sp.setTitle(model.getTitle());
        sp.setText(model.getText());
        sp.setImagePath(model.getImageUrl());
        sp.setUrl(model.getTitleUrl());
        shareAction(mBaseActivity, sp, WechatMoments.NAME);
    }

    public static void shareWechatFavorite(BaseActivity mBaseActivity, ShareModel model) {
        ShareSDK.initSDK(mBaseActivity, "12345678");//,"你的应用在Sharesdk注册时返回的AppKey"

        WechatFavorite.ShareParams sp = new WechatFavorite.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性

        sp.setTitle(model.getTitle());
        sp.setText(model.getText());
        sp.setImagePath(model.getImageUrl());
        sp.setUrl(model.getTitleUrl());
        shareAction(mBaseActivity, sp, WechatFavorite.NAME);
    }

    //微信分享 留在微信界面后不会回调
    public static void shareAction(final BaseActivity mBaseActivity, Platform.ShareParams params, String platformName) {
        mBaseActivity.showPross("分享中...");
       final  Handler handler=new Handler();
        Platform platform = ShareSDK.getPlatform(platformName);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseActivity.dissPross();
                        T.show(mBaseActivity, "分享成功");
                    }
                });

            }

            @Override
            public void onError(Platform platform, int i, final Throwable throwable) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseActivity.dissPross();
                        String expName = throwable.getClass().getSimpleName();
                        Log.e("wangxu",expName);
                        if ("WechatClientNotExistException".equals(expName) || "WechatTimelineNotSupportedException".equals(expName)) {
                            T.show(mBaseActivity,"未安装微信或微信版本过低");

                        }else if ("QQClientNotExistException".equals(expName)) {
                            T.show(mBaseActivity,"未安装QQ或QQ版本过低");
                        } else {
                            T.show(mBaseActivity,"分享失败");


                        }
                    }
                });

            }

            @Override
            public void onCancel(Platform platform, int i) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseActivity.dissPross();
                        T.show(mBaseActivity, "取消分享");
                    }
                });

            }
        });
        // 执行图文分享
        platform.share(params);
    }
}
