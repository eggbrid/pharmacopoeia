package com.pharmacopoeia.activity.health;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.adapter.CollectionAdapter;
import com.pharmacopoeia.activity.health.adapter.CommentAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.CollectionBean;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.reponse.ActicleDetailResponse;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.VideoListResponse;
import com.pharmacopoeia.util.DateUtil;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.SharedUtil;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.util.share.ShareUtils;
import com.pharmacopoeia.view.CustomListView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xus on 2017/7/18.
 */

public class ArticleActivity extends CommentActivity implements View.OnClickListener {
    protected WebView web;
    protected ImageButton right2;
    protected TextView title;
    protected TextView name;
    protected TextView num;
    protected Button send_button;

    protected TagFlowLayout flowLayout;
    protected CustomListView apply;
    protected CustomListView comment;
    protected ImageButton left1;
    protected ImageButton right1;
    protected CommentAdapter adapterComment;
    private List<Commentbean> list = new ArrayList<>();
    private CollectionAdapter adapter;
    private List<CollectionBean> lists = new ArrayList<>();
    private String id;
    private ActicleDetailResponse acticleDetailResponse;
    private CollectionBean collectionBean;
    private ScrollView scrollView;
    private ImageView no_data;
    private Boolean hasMore = true;
    private EditText ed_comment;

    @Override
    public int setContentView() {
        return R.layout.health_activle_activity;
    }

    @Override
    public void initView() throws Exception {
        id = getIntent().getStringExtra("id");
        web = (WebView) findViewById(R.id.web);
        left1 = (ImageButton) findViewById(R.id.left);
        left1.setOnClickListener(ArticleActivity.this);
        right1 = (ImageButton) findViewById(R.id.right);
        right1.setOnClickListener(ArticleActivity.this);
        right2 = (ImageButton) findViewById(R.id.right2);
        right2.setOnClickListener(ArticleActivity.this);
        flowLayout = (TagFlowLayout) findViewById(R.id.flow_layout);
        apply = (CustomListView) findViewById(R.id.apply);
        comment = (CustomListView) findViewById(R.id.comment);
        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        num = (TextView) findViewById(R.id.num);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        no_data = (ImageView) findViewById(R.id.no_data);
        String url = SharedUtil.getString("app_bottom_pic", "");
        ImageLoaderUtil.getInstance().loadNomalImage(url, no_data, R.drawable.no_more);
        send_button = (Button) findViewById(R.id.send_button);
        ed_comment = (EditText) findViewById(R.id.ed_comment);
        send_button.setOnClickListener(this);
        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //当文本的measureheight 等于scroll滚动的长度+scroll的height
                        if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
                            if (hasMore) {
                                LoadMore();
                            } else {
                                no_data.setVisibility(View.VISIBLE);
                            }
                        } else {

                        }
                        break;
                    }
                }
                return false;
            }
        });


        getData();
    }

    private void LoadMore() {
        getData(id, adapterComment.getList().get(adapterComment.getList().size() - 1).getCommentId());
    }

    private void initData() {
        ArrayList<String> Strings = new ArrayList<String>();
        Collections.addAll(Strings, acticleDetailResponse.getTags().split(","));
        flowLayout.setAdapter(new TagAdapter<String>(Strings) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(ArticleActivity.this).inflate(R.layout.view_flowlayout_item, null);
                tv.setText(s);
                return tv;
            }
        });
        initWebviewString(acticleDetailResponse.getArticleContent());
        if(collectionBean!=null){
            lists.add(collectionBean);
        }
        adapter = new CollectionAdapter(this, lists);
        apply.setAdapter(adapter);
        adapterComment = new CommentAdapter(ArticleActivity.this, list);
        comment.setAdapter(adapterComment);
        title.setText(acticleDetailResponse.getArticleTitle());
        name.setText(acticleDetailResponse.getAuthorName());
        num.setText(acticleDetailResponse.getArticleView());

        right1.setImageResource("true".equals(acticleDetailResponse.getCollection()) ? R.drawable.shoucang_nomal : R.drawable.shoucang);

        getData(id, "0");
    }

    private void initWebviewString(String html) {
        web.addJavascriptInterface(new JavascriptHandler(), "android");

        WebSettings webSettings = web.getSettings();

        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(false);
        // 设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultFontSize(16);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
//
//        String css = "<style type=\"text/css\"> img {" +
//                "width:90%;" +
//                "height:auto;" +
//                "}" +   "</style>";
//         html = "<html><header>" + css + "</header><body>" + html + "</body></html>";
        html = html.replace("<img", "<img style='max-width:90%;height:auto;'");
        web.loadData(html, "text/html; charset=UTF-8", null);
        web.setWebViewClient(new WebViewClient() {
            // url拦截
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                view.loadUrl(url);
                // 相应完成返回true
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }

            // 页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showPross("正在加载");
                super.onPageStarted(view, url, favicon);
            }

            // 页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                dissPross();
                super.onPageFinished(view, url);
            }

            // WebView加载的所有资源url
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				view.loadData(errorHtml, "text/html; charset=UTF-8", null);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });

        // 设置WebChromeClient
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            // 处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            ;

            @Override
            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            ;

            @Override
            // 处理javascript中的prompt
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            ;

            // 设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            // 设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) { // 表示按返回键

                        web.goBack(); // 后退

                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.left) {
            this.finish();
        } else if (view.getId() == R.id.right) {
            if ("true".equals(acticleDetailResponse.getCollection())) {
                quxiaoshoucang(id, "article");
            } else {
                shoucang(id, "article");
            }
        } else if (view.getId() == R.id.right2) {
            ShareUtils.showShare(this, view);
        } else if (view.getId() == R.id.send_button) {
            if (TextUtils.isEmpty(ed_comment.getText())) {
                T.show(this, "必须输入评论内容");
                return;
            }
            addComent(ed_comment.getText().toString());
        }
    }

    class JavascriptHandler {
        @JavascriptInterface
        public void getContent(String htmlContent) {
            L.d("html content:" + htmlContent);
        }

        public boolean canBack() {
            if (web.canGoBack()) {
                web.goBack();
                return false;
            }
            return true;
        }
    }

    public void getData() {
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("articleId", id);
        OkHttpUtil.doPost(this, UrlUtil.ARTICLEDETAIL, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                collectionBean = OkHttpUtil.getShopDetailResponse();
                acticleDetailResponse = (ActicleDetailResponse) o;
                initData();
            }

            @Override
            public void onError(String s) {
                T.show(ArticleActivity.this, s);
            }
        }, ActicleDetailResponse.class);
    }

    public void addComent(final String commentContent) {
        showPross("正在评论");
        if (APP.isLogin(this)) {
            Map<String, String> map = OkHttpUtil.getFromMap(this);
            map.put("articleId", id);
            map.put("commentContent", commentContent);
            map.put("userId", APP.getUser(this).getUserId());
            OkHttpUtil.doPost(this, UrlUtil.ARTICLECOMMENTINSERT, map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    dissPross();
                    Commentbean commentbean = new Commentbean();
                    commentbean.setNickName(APP.getUser(ArticleActivity.this).getName());
                    commentbean.setCommentContent(commentContent);
                    commentbean.setCreateTime(DateUtil.getInstance().simY_M_D_H_M(new Date().getTime() + ""));
                    List<Commentbean> list = adapterComment.getList();
                    if (list == null) {
                        list = new ArrayList<Commentbean>();
                    }
                    list.add(commentbean);
                    adapterComment.setList(list);
                    adapterComment.notifyDataSetChanged();
                }

                @Override
                public void onError(String s) {
                    dissPross();

                }
            }, NUllValueResponse.class);
        }
    }

    public void getData(String id, final String c) {
        Map<String, String> map = OkHttpUtil.getFromMap(this);
        map.put("articleId", id);
        map.put("cursor", c);
        map.put("pageSize", "20");
        OkHttpUtil.doPost(this, UrlUtil.ARTICLECOMMENTQUERY, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<Commentbean> clist = (List<Commentbean>) o;
                if (clist.size() < 20) {
                    hasMore = false;
                    no_data.setVisibility(View.VISIBLE);

                } else {
                    hasMore = true;
                }
                adapterComment.getList().addAll(clist);
                adapterComment.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
            }
        }, Commentbean.class);
    }

    public void shoucang(String id, String type) {
        if (APP.isLogin(this)) {
            showPross("正在收藏");
            Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
            map.put("contentId", id);
            map.put("contentType", type);
            OkHttpUtil.doPost(this, UrlUtil.COLLECTION, map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    dissPross();
                    T.show(ArticleActivity.this, "收藏成功");
                    right1.setImageResource(R.drawable.shoucang_nomal);
                    acticleDetailResponse.setCollection("true");

                }

                @Override
                public void onError(String s) {
                    dissPross();
                    T.show(ArticleActivity.this, s);
                }
            }, Commentbean.class);
        }
    }

    public void quxiaoshoucang(String ids, String type) {
        if (APP.isLogin(this)) {
            showPross("正在收藏");
            Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
            map.put("contentIds", id);
            map.put("contentType", type);
            OkHttpUtil.doPost(this, UrlUtil.COLLECTIONCANCEL, map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    dissPross();
                    acticleDetailResponse.setCollection("false");
                    T.show(ArticleActivity.this, "取消收藏成功");
                    right1.setImageResource(R.drawable.shoucang);
                }

                @Override
                public void onError(String s) {
                    dissPross();

                    T.show(ArticleActivity.this, s);
                }
            }, Commentbean.class);
        }
    }

}
