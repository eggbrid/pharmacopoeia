package com.pharmacopoeia.activity.collection;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.collection.adapter.SearchListAdapter;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.CollectionSearch;
import com.pharmacopoeia.util.IntentUtils;
import com.pharmacopoeia.util.T;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/7/24.
 */

public class SearchCollectionActivity extends CommentActivity {
    protected ImageView search;
    protected TagFlowLayout flowLayout;
    protected ListView searchList;
    protected EditText edit;
    private TagAdapter tagAdapter;
    private SearchListAdapter adapter;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(edit.getText().toString())) {
                //查库
                List<CollectionSearch> lists = LiteOrmDBUtil.getInstance(SearchCollectionActivity.this).getQueryByWhereLike(CollectionSearch.class, "name", new Object[]{"%" + edit.getText().toString() + "%"});
                if (lists != null && lists.size() > 0) {
                    adapter = new SearchListAdapter(SearchCollectionActivity.this, lists);
                    adapter.setKey(edit.getText().toString());
                    searchList.setVisibility(View.VISIBLE);
                    searchList.setAdapter(adapter);
                    flowLayout.setVisibility(View.GONE);
                } else {
                    searchList.setVisibility(View.GONE);
                    flowLayout.setVisibility(View.VISIBLE);
                }

            } else {
                searchList.setVisibility(View.GONE);
                flowLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public int setContentView() {
        return R.layout.collection_search_activity;
    }

    @Override
    public void initView() throws Exception {
        ImageButton left = (ImageButton) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchCollectionActivity.this.finish();
            }
        });
        search = (ImageView) findViewById(R.id.search);
        edit = (EditText) findViewById(R.id.title_text);
        edit.addTextChangedListener(textWatcher);
        TextView right = (TextView) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商品列表
                if (TextUtils.isEmpty(edit.getText())) {
                    T.show(SearchCollectionActivity.this, "请输入你要搜索的内容");
                    return;
                }
                IntentUtils.openActivity(SearchCollectionActivity.this, ShopListActivity.class);
            }
        });
        flowLayout = (TagFlowLayout) findViewById(R.id.flow_layout);
        searchList = (ListView) findViewById(R.id.search_list);
        initData();
    }

    public void initData() {
        List<CollectionSearch> cs = new ArrayList<>();
        CollectionSearch collectionSearch = new CollectionSearch();
        collectionSearch.setId("1");
        collectionSearch.setName("感冒片");
        cs.add(collectionSearch);
        CollectionSearch collectionSearch2 = new CollectionSearch();
        collectionSearch2.setId("2");
        collectionSearch2.setName("去疼片");
        cs.add(collectionSearch2);
        try {
            LiteOrmDBUtil.getInstance(this).insertAll(cs);
        } catch (Exception e) {
        }
        List<String> list = new ArrayList<>();
        list.add("珍珠粉");
        list.add("三七粉");
        list.add("隔离霜");
        list.add("红豆薏米粉");
        list.add("三七粉");
        list.add("蜂胶软胶囊");
        tagAdapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchCollectionActivity.this).inflate(R.layout.view_flowlayout_big_item, flowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        flowLayout.setAdapter(tagAdapter);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                IntentUtils.openActivity(SearchCollectionActivity.this, ShopListActivity.class);
                return false;
            }
        });
    }
}
