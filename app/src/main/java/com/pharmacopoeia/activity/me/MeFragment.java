package com.pharmacopoeia.activity.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.health.RecuperateActivity;
import com.pharmacopoeia.activity.main.LoginActivity;
import com.pharmacopoeia.activity.main.WebActivity;
import com.pharmacopoeia.activity.recuperate.SymptomResultActivity;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.cache.User;
import com.pharmacopoeia.bean.reponse.MeRecuperateResponse;
import com.pharmacopoeia.bean.reponse.UserInfoResponse;
import com.pharmacopoeia.util.ImageLoaderUtil;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.db.DBUtil;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.CircleImageView;

import java.util.Map;

/**
 * Created by xus on 2017/7/12.
 */

public class MeFragment extends BaseLazyFragment implements View.OnClickListener {
    protected ImageView ivIcon1;
    protected RelativeLayout myCollection;
    protected ImageView ivIcon2;
    protected TextView myRecuperateVal;
    protected TextView my_info_parting_val;

    protected TextView my_recuperate_time;
    protected TextView my_info_parting_time;


    protected RelativeLayout myRecuperate;
    protected ImageView ivIcon3;
    protected RelativeLayout myManage;
    protected ImageView ivIcon4;
    protected RelativeLayout myPrivacy;
    protected ImageView ivIcon5;
    protected RelativeLayout myService;
    protected ImageView ivIcon6;
    protected RelativeLayout myOpinion;
    protected ImageView ivIcon7;
    protected RelativeLayout mySetting;
    protected ImageView ivIcon8;
    protected RelativeLayout myAbout;
    protected RelativeLayout my_mdlb;
    protected CircleImageView avatar;
    protected RelativeLayout update;
    private TextView name;
    private User user;
    private TextView login;
    private String myManageID;
    private String myRecuperateID;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.me_fragment, container, false);
        initView(mView);
        return mView;
    }

    @Override
    protected void lazyLoad() {
        user = APP.getInstance().getUser(getActivity());
        if (user != null) {
            getMeInfo();
        }

    }

    private void initView(View rootView) {

        login = (TextView) rootView.findViewById(R.id.login);
        name = (TextView) rootView.findViewById(R.id.name);
        avatar = (CircleImageView) rootView.findViewById(R.id.avatar);
        myCollection = (RelativeLayout) rootView.findViewById(R.id.my_collection);
        myRecuperateVal = (TextView) rootView.findViewById(R.id.my_recuperate_val);

        my_info_parting_val = (TextView) rootView.findViewById(R.id.my_info_parting_val);
        my_recuperate_time = (TextView) rootView.findViewById(R.id.my_recuperate_time);
        my_info_parting_time = (TextView) rootView.findViewById(R.id.my_info_parting_time);

        myRecuperate = (RelativeLayout) rootView.findViewById(R.id.my_recuperate);
        myManage = (RelativeLayout) rootView.findViewById(R.id.my_manage);
        myPrivacy = (RelativeLayout) rootView.findViewById(R.id.my_privacy);
        myService = (RelativeLayout) rootView.findViewById(R.id.my_service);
        myOpinion = (RelativeLayout) rootView.findViewById(R.id.my_opinion);
        mySetting = (RelativeLayout) rootView.findViewById(R.id.my_setting);
        myAbout = (RelativeLayout) rootView.findViewById(R.id.my_about);
        my_mdlb = (RelativeLayout) rootView.findViewById(R.id.my_mdlb);
        myCollection.setOnClickListener(MeFragment.this);
        myPrivacy.setOnClickListener(MeFragment.this);
        myService.setOnClickListener(MeFragment.this);
        myOpinion.setOnClickListener(MeFragment.this);
        myAbout.setOnClickListener(MeFragment.this);
        mySetting.setOnClickListener(MeFragment.this);
        login.setOnClickListener(MeFragment.this);
        my_mdlb.setOnClickListener(MeFragment.this);
        myManage.setOnClickListener(MeFragment.this);
        myRecuperate.setOnClickListener(MeFragment.this);
        update = findViewById(R.id.update);
        update.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                IntentUtils.openActivity(getActivity(), LoginActivity.class);
                break;

            case R.id.my_collection:
                if (APP.isLogin(getActivity())) {
                    IntentUtils.openActivity(getActivity(), MyCollection.class);

                }
                break;
            case R.id.my_service:
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", "服务条款");
                bundle1.putString("url", "file:///android_asset/fwtk.html");

                IntentUtils.openActivity(getActivity(), WebActivity.class, bundle1);
                break;


            case R.id.my_privacy:
                Bundle bundle = new Bundle();
                bundle.putString("title", "隐私条款");
                bundle.putString("url", "file:///android_asset/ystk.html");
                IntentUtils.openActivity(getActivity(), WebActivity.class, bundle);
                break;
            case R.id.my_setting:
                IntentUtils.openActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.my_opinion:
                IntentUtils.openActivity(getActivity(), SuggestionsActivity.class);
                break;
            case R.id.my_recuperate:

                if (APP.isLogin(getActivity())){
                    if (TextUtils.isEmpty(myRecuperateID)) {
                    IntentUtils.openActivity(getActivity(),RecuperateActivity.class);
                    }else{
                        Bundle bundle2=new Bundle();
                        bundle2.putString("resultId",myRecuperateID);
                        IntentUtils.openActivity(getActivity(),SymptomResultActivity.class,bundle2);

                    }
                }

                break;
            case R.id.my_manage:
                if (APP.isLogin(getActivity())){
                    if (TextUtils.isEmpty(myManageID)) {
                        IntentUtils.openActivity(getActivity(),RecuperateActivity.class);
                    }else{
                        Bundle bundle2=new Bundle();
                        bundle2.putString("resultId",myManageID);
                        IntentUtils.openActivity(getActivity(),SymptomResultActivity.class,bundle2);
                    }
                }
                break;
            case R.id.update:
                if (APP.isLogin(getActivity())) {
                    IntentUtils.openActivity(getActivity(), UpdateInfoActivity.class);
                }
                break;
            case R.id.my_mdlb:
                IntentUtils.openActivity(getActivity(), AddressListActivity.class);
                break;
            case R.id.my_about:
                IntentUtils.openActivity(getActivity(), AboutActivity.class);
                break;
        }

    }

    public void getMeRecuperate() {
        if (user == null) {
            return;
        }
        Map<String, String> map = OkHttpUtil.getLoginFromMap(getActivity());

        OkHttpUtil.doPost(getActivity(), UrlUtil.GETLASTANSWER, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                MeRecuperateResponse response = (MeRecuperateResponse) o;

                if (response != null) {

                    if (response.getType1() != null) {
                        myRecuperateVal.setText(response.getType1().getResultName());
                        my_recuperate_time.setText(response.getType1().getAnswerTime());
                        myRecuperateID = response.getType1().getResultId();
                        my_recuperate_time.setVisibility(View.VISIBLE);

                    } else {
                        myRecuperateVal.setText("您还未测试");
                        my_recuperate_time.setVisibility(View.GONE);

                    }
                    if (response.getType2() != null) {
                        my_info_parting_val.setText(response.getType2().getResultName());
                        my_info_parting_time.setText(response.getType2().getAnswerTime());
                        myManageID = response.getType2().getResultId();
                        my_info_parting_time.setVisibility(View.VISIBLE);

                    } else {
                        my_info_parting_val.setText("您还未测试");
                        my_info_parting_time.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onError(String s) {
            }
        }, MeRecuperateResponse.class);
    }


    public void getMeInfo() {
        Map<String, String> map = OkHttpUtil.getLoginFromMap(getActivity());

        OkHttpUtil.doPost(getActivity(), UrlUtil.USERINFO, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                UserInfoResponse response = (UserInfoResponse) o;
                User user = APP.getUser(getActivity());
                user.setRegionCode(response.getRegionCode());
                user.setName(response.getNickName());
                DBUtil.getInstance(getActivity()).setUser(user);
                name.setText(response.getNickName());
                login.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                name.setText(user.getName());
//                ImageLoaderUtil.getInstance().loadNomalImage(user.getAvatar(), avatar, R.drawable.me_defult_avatar);
                avatar.setImageResource(R.drawable.me_defult_avatar);

            }

            @Override
            public void onError(String s) {
            }
        }, UserInfoResponse.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        user = APP.getInstance().getUser(getActivity());
        getMeRecuperate();

        if (user == null) {
            login.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            name.setVisibility(View.GONE);

        } else {
            if (TextUtils.isEmpty(user.getName())) {
                getMeInfo();
            } else {

                login.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                name.setText(user.getName());
                avatar.setImageResource(R.drawable.me_defult_avatar);
//                ImageLoaderUtil.getInstance().loadNomalImage(user.getAvatar(), avatar, R.drawable.me_defult_avatar);
            }


        }
    }
}
