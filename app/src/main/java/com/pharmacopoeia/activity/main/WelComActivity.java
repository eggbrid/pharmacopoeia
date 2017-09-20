package com.pharmacopoeia.activity.main;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.WebViewActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.Open;
import com.pharmacopoeia.bean.cache.Setting;
import com.pharmacopoeia.bean.reponse.CarouselResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/13.
 */

public class WelComActivity extends CommentActivity {
    public static final int AUDIO = 1;

    protected ImageView view;

    @Override
    public void beforeSet() {
        setTheme(R.style.AppThemeWelcome);
    }

    @Override
    public int setContentView() {
        return R.layout.main_welcom_activity;
    }

    private AlphaAnimation start_anima;

    @Override
    public void initView() throws Exception {
        view = (ImageView) findViewById(R.id.view);
        Open open = LiteOrmDBUtil.getInstance(WelComActivity.this).getQueryFirst(Open.class);
        if (open != null && !TextUtils.isEmpty(open.getFileUrl())) {
            ImageLoaderUtil.getInstance().loadNomalImage(open.getFileUrl(), view, R.drawable.welcome);
        }
        getData();
        start_anima = new AlphaAnimation(1.0f, 1.0f);
        start_anima.setDuration(3000);//
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!MPermissions.shouldShowRequestPermissionRationale(WelcomeActivity.this, Manifest.permission.RECORD_AUDIO, AUDIO)) {
//                        MPermissions.requestPermissions(WelcomeActivity.this, AUDIO, Manifest.permission.RECORD_AUDIO);
//                    }
                    redirectTo();
                } else {
                    redirectTo();
                }
            }
        });
    }

    private void redirectTo() {
        Setting setting = DBUtil.getInstance(this).getSetting();
        if (setting != null && setting.getIsfrist() == 1) {
            //非第一次
//            if (setting.getIsLogin()==1) {
            //已登录
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
//            } else {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
        } else {
            IntentUtils.openActivity(this, NewFirstActivity.class);
            finish();
        }
    }

    @PermissionGrant(AUDIO)
    public void AUDIOsuc() {

    }

    @PermissionDenied(AUDIO)
    public void AUDIOfal() {
    }

    public void getData() {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        OkHttpUtil.doGet(this, UrlUtil.OPEN, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
               List<CarouselResponse>  carouselResponse = (List<CarouselResponse>) o;
                if (carouselResponse!=null&&carouselResponse.size()>0&&carouselResponse.get(0)!=null){
                    Open open = new Open();
                    open.setFileUrl(carouselResponse.get(0).getFileUrl());
                    LiteOrmDBUtil.getInstance(WelComActivity.this).insert(open);
                }

            }

            @Override
            public void onError(String s) {

            }
        }, CarouselResponse.class);
    }
}
