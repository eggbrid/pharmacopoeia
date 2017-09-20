package com.pharmacopoeia.activity.main;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;

/**
 * Created by xus on 2017/7/25.
 */

public class WebActivity extends CommentActivity implements View.OnClickListener {

    private WebView web;

    @Override
    public int setContentView() {
        return R.layout.main_web_activity;
    }

    @Override
    public void initView() throws Exception {
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        web = (WebView) findViewById(R.id.web);
        web.setOnClickListener(this);
        web.loadUrl(url);
        web.setBackgroundColor(0); // 设置背景色
        web.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
        setCommentTitleView(title);
    }


}
