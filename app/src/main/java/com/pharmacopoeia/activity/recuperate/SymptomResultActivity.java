package com.pharmacopoeia.activity.recuperate;

import android.view.View;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.TestData;
import com.pharmacopoeia.activity.recuperate.adapter.RecuperateAdapter;
import com.pharmacopoeia.activity.recuperate.adapter.SysmptomResultAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.bean.reponse.RecuperateResponse;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CustomGridView;

import java.util.Map;

/**
 * Created by 52243 on 2017/8/29.
 */

public class SymptomResultActivity extends CommentActivity implements View.OnClickListener {

    private CustomGridView customGridView;
    private SysmptomResultAdapter sysmptomResultAdapter;

    private View xingchengyuanyi, shenghuojianyi;
    private ImageView image_live, image_res;

    private boolean isHistoty=false;
    private String resultId;
    private String oid;
    private String answerJson;

    @Override
    public int setContentView() {
        return R.layout.recuperate_sysmptomresult_acitivity;
    }

    @Override
    public void initView() throws Exception {

        if (getIntent().hasExtra("resultId")){
            isHistoty=true;
            resultId=getIntent().getStringExtra("resultId");
        }else{
            isHistoty=false;
            oid=getIntent().getStringExtra("oid");
            answerJson=getIntent().getStringExtra("answerJson");

        }
        setCommentTitleView("您的症状为肝火上炎症高血压");
        customGridView = (CustomGridView) findViewById(R.id.grid_view);
        xingchengyuanyi = findViewById(R.id.xingchengyuanyi);
        shenghuojianyi = findViewById(R.id.shenghuojianyi);
        xingchengyuanyi.setOnClickListener(this);
        shenghuojianyi.setOnClickListener(this);

        sysmptomResultAdapter = new SysmptomResultAdapter(this, TestData.getMoreListShop());

        customGridView.setAdapter(sysmptomResultAdapter);
        if (isHistoty){
            geHisData();
        }else{
            getData();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        switch (id) {

            case R.id.xingchengyuanyi:

                break;
            case R.id.shenghuojianyi:

                break;
        }
    }

    public void getData(){
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("oid",oid);
        map.put("answerJson",answerJson);
        OkHttpUtil.doPost(this, UrlUtil.ANSWER, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(String s) {
            }
        }, NUllValueResponse.class);
    }

    public void geHisData(){
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("id",resultId);
        OkHttpUtil.doPost(this, UrlUtil.GETRESULTITEMBYID, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(String s) {
            }
        }, NUllValueResponse.class);
    }
}
