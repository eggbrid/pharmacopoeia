package com.pharmacopoeia.activity.me;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.CommentActivity;
import com.pharmacopoeia.view.PImageButton;

/**
 * Created by xus on 2017/8/4.
 */

public class SuggestionsActivity extends CommentActivity implements View.OnClickListener {
    protected EditText edit;
    protected Button next;

    @Override
    public int setContentView() {
        return R.layout.me_suggestions_activity;
    }

    @Override
    public void initView() throws Exception {
        setCommentTitleView("意见反馈");
        edit = (EditText) findViewById(R.id.edit);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(SuggestionsActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
