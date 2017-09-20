package com.pharmacopoeia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.model.WebBeanModel;

/**
 * Created by 52243 on 2017/4/11.
 */

public class WebViewActivity extends CommentActivity {

    private WebView webView;
    private ProgressBar pb_progress;
    private WebBeanModel webBeanModel;

    @Override
    public int setContentView() {
        return R.layout.webview_activity;
    }

    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        webBeanModel = (WebBeanModel) bundle.getSerializable("webBeanModel");
        setCommentTitleView(webBeanModel.getTitle());
        init();

    }

    public void initView() throws Exception {
        webView = (WebView) findViewById(R.id.webView);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);

        initData();
    }

    public void init() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    pb_progress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    if (pb_progress.getVisibility()==View.GONE){
                        pb_progress.setVisibility(View.VISIBLE);

                    }
                    pb_progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.loadUrl(webBeanModel.getUrl());
    }
}
