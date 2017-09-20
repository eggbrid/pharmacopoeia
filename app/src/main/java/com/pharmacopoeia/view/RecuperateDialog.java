package com.pharmacopoeia.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.health.RecuperateActivity;
import com.pharmacopoeia.activity.recuperate.InfoEditActivity;
import com.pharmacopoeia.util.IntentUtils;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by xus on 2016/11/17.
 */

public class RecuperateDialog extends Dialog {
    private Context context;
    private TextView text;
    private TextView yes;
    private TextView no;
    private String content;
    private String id;

    public RecuperateDialog(Context context, String content,String id) {
        super(context, R.style.Dialog_FullscreenC);
        this.context = context;
        this.content = content;
        this.id=id;
    }

    public RecuperateDialog(Context context, int themeResId) {

        super(context, R.style.Dialog_FullscreenC);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recuperate);
        text = (TextView) findViewById(R.id.text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml(content,Html.FROM_HTML_MODE_COMPACT));
        }else{
            text.setText(Html.fromHtml(content));
        }
        yes = (TextView) findViewById(R.id.yes);
        no = (TextView) findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperateDialog.this.dismiss();
                //跳轉
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                IntentUtils.openActivity(context, InfoEditActivity.class,bundle);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperateDialog.this.dismiss();
            }
        });
//        text.setText(matcherSearchText(text.getText().toString(), "≥140mmHg", "≥90mmHg"));

    }

    public SpannableString matcherSearchText(String text, String... keyword) {
        SpannableString ss = new SpannableString(text);

        for (String s : keyword) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(ss);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.app_green)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }

    public void show(String s) {
        super.show();

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
