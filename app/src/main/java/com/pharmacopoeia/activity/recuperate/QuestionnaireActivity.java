package com.pharmacopoeia.activity.recuperate;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.recuperate.adapter.QuestionnaireAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.model.HealthContentArticleBean;
import com.pharmacopoeia.bean.model.MainSelList;
import com.pharmacopoeia.bean.model.QuestionnaireModel;
import com.pharmacopoeia.bean.reponse.QuestionResponse;
import com.pharmacopoeia.bean.request.QuestChildRequest;
import com.pharmacopoeia.bean.request.QuestMainSelListRequest;
import com.pharmacopoeia.bean.request.QuestRequest;
import com.pharmacopoeia.bean.request.QuestSelListRequest;
import com.pharmacopoeia.interfaces.enums.QuestType;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CustomListView;
import com.pharmacopoeia.view.PImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by xus on 2017/8/30.
 */

public class QuestionnaireActivity extends CommentActivity {
    protected CustomListView list;
    private QuestionnaireAdapter adapter;
    private LinearLayout view;
    private ScrollView scroll;
    private QuestionResponse questionResponse;
    private String id;
    private TextView next;
private String userCode;
    @Override
    public int setContentView() {
        return R.layout.recuperate_questionnaire_activity;
    }

    @Override
    public void initView() throws Exception {
        id = getIntent().getStringExtra("id");
        userCode= getIntent().getStringExtra("userCode");
        list = (CustomListView) findViewById(R.id.list);
        view = (LinearLayout) findViewById(R.id.view);
        scroll = (ScrollView) findViewById(R.id.scroll);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestRequest questRequest=     getQR();
                String answerJson=new Gson().toJson(questRequest);
                Bundle bundle=new Bundle();
                bundle.putString("oid",id);
                bundle.putString("answerJson",answerJson);
                bundle.putString("userCode",userCode);



                IntentUtils.openActivity(QuestionnaireActivity.this, SymptomResultActivity.class,bundle);
            }
        });
        adapter = new QuestionnaireAdapter(this, new ArrayList<QuestionnaireModel>());
        list.setAdapter(adapter);
        setCommentTitleView("症状查询测试");
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        setAdapterListener();
        getData();
    }

    public QuestRequest getQR() {
        QuestRequest questRequest = new QuestRequest();
        List<QuestMainSelListRequest> selList = new ArrayList<>();
        List<QuestionnaireModel> list = adapter.getList();
        QuestionnaireModel main = list.get(0);
        String[] valuse = main.getValue().split(",");
        for (String id : valuse) {
            if (TextUtils.isEmpty(id))
                continue;
            QuestMainSelListRequest questMainSelListRequest = new QuestMainSelListRequest();
            List<QuestChildRequest> assistQuestionList = new ArrayList<>();
            questMainSelListRequest.setSelseq(id);
            for (int i = 1; i < list.size(); i++) {
                QuestChildRequest questChildRequest = new QuestChildRequest();
                List<QuestSelListRequest> cselList = new ArrayList<>();
                QuestionnaireModel model = list.get(i);
                questChildRequest.setSeq(model.getSeq());
                String[] cvaluses = model.getValue().split(",");
                for (String cid : cvaluses) {
                    if (TextUtils.isEmpty(cid))
                        continue;
                    QuestSelListRequest listRequest = new QuestSelListRequest();
                    listRequest.setSeleseq(cid);
                    cselList.add(listRequest);
                }
                questChildRequest.setSelList(cselList);
                assistQuestionList.add(questChildRequest);
            }
            questMainSelListRequest.setAssistQuestionList(assistQuestionList);
            selList.add(questMainSelListRequest);
        }
        questRequest.setSelList(selList);
        return questRequest;

    }

    public void setAdapterListener() {
        adapter.setOnFinish(new QuestionnaireAdapter.OnFinish() {
            @Override
            public void onFinish(boolean isFinish) {
                next.setEnabled(isFinish);
            }
        });
        adapter.setOnMainQuestSelect(new QuestionnaireAdapter.OnMainQuestSelect() {
            @Override
            public void onMainQuestSelect(String values) {
                adapter.setList(getData(questionResponse, values));
                adapter.notifyDataSetChanged();
            }
        });
    }

    public List<QuestionnaireModel> getData(QuestionResponse questionResponse, String MainValues) {
        List<QuestionnaireModel> list = new ArrayList<>();
        if(questionResponse.getQuestion()==null){
            T.show(this, "该问卷下暂无问题");
            return list ;
        }

        list.add(new QuestionnaireModel(questionResponse.getQuestion(), MainValues));
        if (!TextUtils.isEmpty(MainValues)) {
            String[] valuse = MainValues.split(",");
            for (String id : valuse) {
                for (MainSelList msl : questionResponse.getQuestion().getSelList()) {
                    if (msl.getSelseq().equals(id)) {
                        for (int i = 0; i < msl.getAssistQuestionList().size(); i++) {
                            list.add(new QuestionnaireModel(msl.getAssistQuestionList().get(i)));
                        }
                    }
                }
            }
        }
        return list;

    }


    public void getData() {
        showPross("正在获取题目信息...");
        Map<String, String> map = OkHttpUtil.getLoginFromMap(this);
        map.put("id", id);
//        map.put("id", "1");
        OkHttpUtil.doPost(this, UrlUtil.GETQUESTIONLISTBYID, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dissPross();
                questionResponse = (QuestionResponse) o;
                adapter.setList(getData(questionResponse, ""));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
                dissPross();
                Log.e("wangxu", s);
                T.show(QuestionnaireActivity.this, s);
            }
        }, QuestionResponse.class);
    }
}
