package com.pharmacopoeia.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.InvokeParam;
import com.pharmacopoeia.R;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.view.PImageButton;
import com.pharmacopoeia.view.ProLoadingDialog;
import com.pharmacopoeia.view.RefreshLayout;
import com.pharmacopoeia.view.RefreshLayoutFooter;

public class BaseFragment extends Fragment {
    public View mView;
    public Context mContext;
    protected PImageButton left;
    protected PImageButton right;
    protected LinearLayout title;
    protected TextView titleText;
    protected InvokeParam invokeParam;
    protected TakePhoto takePhoto;
    //    protected PhotoDialog photoDialog;
    protected ProLoadingDialog proLoadingDialog;
    protected InputMethodManager inputManager;
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    protected <T> T findViewById(int id) {
        T view = (T) mView.findViewById(id);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setCommentTitleView(String titles) {
        initTitleView();
        left.setImageResource(R.drawable.back_black);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        left.setVisibility(View.VISIBLE);
        titleText.setText(titles);
    }

    public void initTitleView() {
        titleText = findViewById(R.id.title_text);
        left =  findViewById(R.id.left);
        right =  findViewById(R.id.right);
//        title =  findViewById(R.id.title);
//        title.setVisibility(View.VISIBLE);
    }
    public void setRefresh(final RefreshLayout mRefreshLayout, final ListView mListView, boolean isautoRefresh, boolean isopenLoad, final RefreshListener refreshListener){
        mRefreshLayout.setChildView(mListView, isopenLoad);
        mRefreshLayout.setColorSchemeResources(R.color.black,
                R.color.white);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });

        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                mRefreshLayout.getRefreshLayoutFooter().closefooter();
                if (refreshListener != null) {
                    refreshListener.onLoadMore();
                }
            }
        });

        if (isautoRefresh) {

            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    if (refreshListener != null) {
                        refreshListener.onRefresh();
                    }
                }
            });
        }
    }
    public void setRefresh(final SwipeRefreshLayout mRefreshLayout, final RecyclerView mListView, boolean isautoRefresh, final RefreshListener refreshListener){
        mRefreshLayout.setColorSchemeResources(R.color.black,
                R.color.white);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (refreshListener != null) {
                    refreshListener.onRefresh();
                }
            }
        });


        if (isautoRefresh) {

            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    if (refreshListener != null) {
                        refreshListener.onRefresh();
                    }
                }
            });
        }
    }
    public  void stopRefresh(final SwipeRefreshLayout mRefreshLayout){
        if (mRefreshLayout == null)
            return;
        mRefreshLayout.setRefreshing(false);

    }
    public  void stopRefresh(final RefreshLayout mRefreshLayout,boolean isNoData){
        if (mRefreshLayout == null)
            return;
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setLoading(false);
        RefreshLayoutFooter refreshLayoutFooter = mRefreshLayout.getRefreshLayoutFooter();
        if (refreshLayoutFooter != null) {
            refreshLayoutFooter.setViewVis();
            refreshLayoutFooter.openfooter();
        }
        mRefreshLayout.setNoData(isNoData);

    }

}
