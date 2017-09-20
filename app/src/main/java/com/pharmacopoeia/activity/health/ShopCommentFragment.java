package com.pharmacopoeia.activity.health;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pharmacopoeia.APP;
import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseLazyFragment;
import com.pharmacopoeia.bean.model.Commentbean;
import com.pharmacopoeia.bean.reponse.NUllValueResponse;
import com.pharmacopoeia.interfaces.adapter.ShopDetailCommentAdapter;
import com.pharmacopoeia.interfaces.views.RefreshListener;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.http.Url.UrlUtil;
import com.pharmacopoeia.util.http.okhttp.OkHttpUtil;
import com.pharmacopoeia.util.http.okhttp.interfaces.CallBack;
import com.pharmacopoeia.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 52243 on 2017/7/21.
 */

public class ShopCommentFragment extends BaseLazyFragment implements RefreshListener, View.OnClickListener {

    private ShopDetailCommentAdapter shopDetailCommentAdapter;
    private ListView list_item;
    private RefreshLayout refresh;
    private TextView no_data;
    private String cursor = "0";
    private String itemId;

    private EditText ed_comment;
    private Button send_button;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.health_shop_comment_fragment, container, false);
        list_item = (ListView) mView.findViewById(R.id.list_item);
        refresh = (RefreshLayout) mView.findViewById(R.id.refresh);
        no_data = (TextView) mView.findViewById(R.id.no_data);
        send_button = (Button) mView.findViewById(R.id.send_button);
        ed_comment = (EditText) mView.findViewById(R.id.ed_comment);
        return mView;
    }

    @Override
    protected void lazyLoad() {
        send_button.setOnClickListener(this);
        setRefresh(refresh, this.list_item, true, true, this);
        shopDetailCommentAdapter = new ShopDetailCommentAdapter(mContext, null);
        list_item.setAdapter(shopDetailCommentAdapter);
        itemId = ((ShopActivity) getActivity()).itemId;
        getData(itemId, cursor);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.send_button:
                if (TextUtils.isEmpty(ed_comment.getText())) {
                    T.show(mContext, "必须输入评论内容");
                    return;
                }
                addComent(ed_comment.getText().toString());
                break;
        }
    }

    public void addComent(final String commentContent) {

        if (APP.isLogin(mContext)) {
            Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
            map.put("itemId", itemId);
            map.put("commentContent", commentContent);
            map.put("userId",APP.getUser(mContext).getUserId());
            Log.e("APP.getUser().()",APP.getUser(mContext).getUserId()+"{}{}{}}{");
            OkHttpUtil.doPost(getActivity(), UrlUtil.SHOPDETAILCOMMENTINSERT, map, new CallBack() {
                @Override
                public void onSuccess(Object o) {
                    Commentbean commentbean = new Commentbean();
                    commentbean.setNickName(APP.getUser(mContext).getName());
                    commentbean.setCommentContent(commentContent);
                    List<Commentbean> list = shopDetailCommentAdapter.getList();
                    if (list == null) {
                        list = new ArrayList<Commentbean>();
                    }
                    list.add(commentbean);
                    shopDetailCommentAdapter.setList(list);
                    shopDetailCommentAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String s) {

                }
            }, NUllValueResponse.class);
        }
    }

    @Override
    public void onLoadMore() {
        getData(itemId, cursor);
    }

    @Override
    public void onRefresh() {
        getData(itemId, "0");
    }

    public void getData(String id, final String c) {
        Map<String, String> map = OkHttpUtil.getFromMap(getActivity());
        map.put("itemId", id);
        map.put("cursor", c + "");
        map.put("pageSize", "20");

        OkHttpUtil.doPost(getActivity(), UrlUtil.SHOPDETAILCOMMENT, map, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                List<Commentbean> list = (List<Commentbean>) o;
                shopDetailCommentAdapter.setList(list);
                shopDetailCommentAdapter.notifyDataSetChanged();
                if (list != null && list.size() != 0) {
                    Commentbean commentbean = list.get(list.size() - 1);
                    cursor = commentbean.getCommentId();
                }
                if ("0".equals(cursor) && list.size() <= 0) {
                    cursor="0";
                    refresh.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    return;
                }
                if (list.size() < 20) {
                    stopRefresh(refresh, true);
                } else {
                    stopRefresh(refresh, false);
                }

            }

            @Override
            public void onError(String s) {
                stopRefresh(refresh, true);
                refresh.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
            }
        }, Commentbean.class);
    }
}
