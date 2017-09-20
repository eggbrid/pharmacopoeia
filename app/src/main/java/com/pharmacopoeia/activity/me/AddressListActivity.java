package com.pharmacopoeia.activity.me;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.me.adapter.AddressListAdapter;
import com.pharmacopoeia.base.BaseActivity;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.bean.cache.TKAddress;
import com.pharmacopoeia.util.db.LiteOrmDBUtil;
import com.pharmacopoeia.view.PImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xus on 2017/9/14.
 */

public class AddressListActivity extends CommentActivity {
    protected ImageView search;
    protected TextView emp;
    protected ListView list;
    protected EditText titleTexts;
    private AddressListAdapter addressListAdapter;
    List<TKAddress> lists = new ArrayList<>();

    @Override
    public int setContentView() {
        return R.layout.me_address_activity;
    }

    @Override
    public void initView() throws Exception {
        search = (ImageView) findViewById(R.id.search);
        titleTexts = (EditText) findViewById(R.id.title_text);
        left = (PImageButton) findViewById(R.id.left);
        right = (PImageButton) findViewById(R.id.right);
        title = (LinearLayout) findViewById(R.id.title);
        list = (ListView) findViewById(R.id.list);
        emp = (TextView) findViewById(R.id.emp);
        left.setImageResource(R.drawable.back_black);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressListActivity.this.finish();
            }
        });
        right.setText("搜索");
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = titleTexts.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    try {
                        getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    getSearchList(s);

                }
            }
        });
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        addressListAdapter = new AddressListAdapter(this, lists);
        list.setAdapter(addressListAdapter);
        getData();
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             dissPross();
            switch (msg.what) {
                case 1:
                    addressListAdapter.setList(lists);
                    addressListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    break;
            }
        }
    };

    public void getData() throws IOException {
        showPross("正在查询");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lists = LiteOrmDBUtil.getInstance(AddressListActivity.this).getQueryAll(TKAddress.class);
                    if (lists == null || lists.size() <= 0) {
                        String cityString = null;
                        cityString = inputStream2String(AddressListActivity.this.getResources().getAssets().open("tk.json"));
                        Gson gson = new Gson();
                        lists = gson.fromJson(cityString,
                                new TypeToken<List<TKAddress>>() {
                                }.getType());
                        LiteOrmDBUtil.getInstance(AddressListActivity.this).insertAll(lists);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(1);
                            }
                        });

                    }else{
                        handler.sendEmptyMessage(1);

                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(2);

                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void getSearchList(final String s) {
        showPross("正在查询");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lists = LiteOrmDBUtil.getInstance(AddressListActivity.this).getQueryByWhereLikeBySql(TKAddress.class, "name like ? or address like ?", new Object[]{"%" + s + "%", "%" + s + "%"});
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(1);
                            }
                        });
                } catch (Exception e) {
                    handler.sendEmptyMessage(2);

                    e.printStackTrace();
                }
            }
        }).start();


    }
}
