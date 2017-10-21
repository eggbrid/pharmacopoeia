package com.pharmacopoeia.activity.recuperate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.main.MainActivity;
import com.pharmacopoeia.activity.recuperate.adapter.RecuperateAdapter;
import com.pharmacopoeia.activity.recuperate.adapter.RecuperateAdapter2;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.RecuperateResponse;
import com.pharmacopoeia.bean.reponse.UserInfoResponse;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CustomGridView;
import com.pharmacopoeia.view.RecuperateDialog;

import java.util.Map;

/**
 * Created by xus on 2017/7/12.
 * 调养
 */

public class RecuperateFragment extends BaseLazyFragment {

    private CustomGridView normal_disease;
    private CustomGridView female_maintenance;
    private CustomGridView child_maintenance;


    private RecuperateAdapter normal;
    private RecuperateAdapter women;
    private RecuperateAdapter child;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.recuperate_fragment, container, false);
        initView();

        return mView;
    }

    @Override
    protected void lazyLoad() {
        getTY2TYPE();
    }

    private void initView() {
        normal_disease = findViewById(R.id.normal_disease);
        female_maintenance = findViewById(R.id.female_maintenance);
        child_maintenance = findViewById(R.id.child_maintenance);
        initTitleView();
        titleText.setText("调养");
        normal_disease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (APP.isLogin(getActivity())) {

                    if (TextUtils.isEmpty(normal.getItem(position).getContent())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", normal.getItem(position).getOid());
                        IntentUtils.openActivity(getActivity(), InfoEditActivity.class, bundle);
                    } else {
                        new RecuperateDialog(getActivity(), normal.getItem(position).getContent(), normal.getItem(position).getOid()).show();

                    }
                }
            }
        });
        female_maintenance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (APP.isLogin(getActivity())) {

                    if (TextUtils.isEmpty(women.getItem(position).getContent())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", women.getItem(position).getOid());
                        IntentUtils.openActivity(getActivity(), InfoEditActivity.class, bundle);
                    } else {
                        new RecuperateDialog(getActivity(), women.getItem(position).getContent(), women.getItem(position).getOid()).show();
                    }
                }
            }
        });
        child_maintenance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (APP.isLogin(getActivity())) {
                    if (TextUtils.isEmpty(child.getItem(position).getContent())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", child.getItem(position).getOid());
                        IntentUtils.openActivity(getActivity(), InfoEditActivity.class, bundle);
                    } else {
                        new RecuperateDialog(getActivity(), child.getItem(position).getContent(), child.getItem(position).getOid()).show();
                    }
                }
            }
        });
//        normal = new RecuperateAdapter(getActivity(),1);
//        women = new RecuperateAdapter(getActivity(), 2);
//        child = new RecuperateAdapter(getActivity(),3);
        normal_disease.setAdapter(normal);
        female_maintenance.setAdapter(women);
        child_maintenance.setAdapter(child);
    }

    public void getTY2TYPE() {
        Map<String, String> map = OkHttpUtil.getLoginFromMap(getActivity());

        OkHttpUtil.doGet(getActivity(), UrlUtil.TY2TYPE, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                RecuperateResponse response = (RecuperateResponse) o;
                normal = new RecuperateAdapter(getActivity(), response.getType1());
                women = new RecuperateAdapter(getActivity(), response.getType2());
                child = new RecuperateAdapter(getActivity(), response.getType3());
//                normal = new RecuperateAdapter2(getActivity(),1);
//                women = new RecuperateAdapter2(getActivity(), 2);
//                child = new RecuperateAdapter2(getActivity(),3);
                normal_disease.setAdapter(normal);
                female_maintenance.setAdapter(women);
                child_maintenance.setAdapter(child);
            }

            @Override
            public void onError(String s) {
            }
        }, RecuperateResponse.class);
    }


}
