package com.pharmacopoeia.activity.recuperate;

import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.recuperate.adapter.SysmptomResultAdapter;
import com.pharmacopoeia.activity.recuperate.adapter.SysmptomResultReaseAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.reponse.SystemResultResponse;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CustomGridView;
import com.pharmacopoeia.view.CustomListView;

import java.util.Map;

/**
 * Created by 52243 on 2017/8/29.
 */

public class SymptomResultActivity extends CommentActivity {

    private CustomGridView customGridView;
    private SysmptomResultAdapter sysmptomResultAdapter;
    private SysmptomResultReaseAdapter sysmptomResultReaseAdapter;


    private boolean isHistoty = false;
    private String resultId;
    private String oid;
    private String answerJson;

    private ScrollView scrollView;
    private TextView text_name, text_symptom;
    private String    userCode;
    private CustomListView list;

    @Override
    public int setContentView() {
        return R.layout.recuperate_sysmptomresult_acitivity;
    }

    @Override
    public void initView() throws Exception {

        if (getIntent().hasExtra("resultId")) {
            isHistoty = true;
            resultId = getIntent().getStringExtra("resultId");
        } else {
            isHistoty = false;
            userCode=getIntent().getStringExtra("userCode");
            oid = getIntent().getStringExtra("oid");
            answerJson = getIntent().getStringExtra("answerJson");

        }
        setCommentTitleView("您的症状为");
        customGridView = (CustomGridView) findViewById(R.id.grid_view);
        text_name = (TextView) findViewById(R.id.text_name);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        list = (CustomListView) findViewById(R.id.list);


        sysmptomResultAdapter = new SysmptomResultAdapter(this, null);

        sysmptomResultReaseAdapter = new SysmptomResultReaseAdapter(this, null);
        list.setAdapter(sysmptomResultReaseAdapter);

        customGridView.setAdapter(sysmptomResultAdapter);
        if (isHistoty) {
            geHisData();
        } else {
            getData();
        }
    }


    public void getData() {
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("oid", oid);
        map.put("answerJson", answerJson);
        map.put("userCode", userCode);
        OkHttpUtil.doPost(this, UrlUtil.ANSWER, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                if (o != null) {
                    scrollView.setVisibility(View.VISIBLE);
                    setData(o);
                } else {
                    scrollView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String s) {
            }
        }, SystemResultResponse.class);
    }

    public void geHisData() {
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("id", resultId);
        OkHttpUtil.doPost(this, UrlUtil.GETRESULTITEMBYID, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                if (o != null) {
                    scrollView.setVisibility(View.VISIBLE);
                    setData(o);
                } else {
                    scrollView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String s) {
            }
        }, SystemResultResponse.class);
    }

    public void setData(Object o) {
        SystemResultResponse systemResultResponse = (SystemResultResponse) o;
        if (systemResultResponse.getResultInfo() != null) {
            scrollView.setVisibility(View.VISIBLE);
            text_name.setText(systemResultResponse.getResultInfo().getName());
            setCommentTitleView("您的症状为" + systemResultResponse.getResultInfo().getName());
        } else {
            scrollView.setVisibility(View.GONE);
        }
        sysmptomResultAdapter.setList(systemResultResponse.getItems());
        sysmptomResultAdapter.notifyDataSetChanged();

        sysmptomResultReaseAdapter.setList(systemResultResponse.getTabs());
        sysmptomResultReaseAdapter.notifyDataSetChanged();
    }
}
