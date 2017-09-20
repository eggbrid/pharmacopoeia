package com.pharmacopoeia.activity.recuperate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.PBaseAdapter;

/**
 * Created by xus on 2017/7/19.
 */

public class RecuperateAdapter2 extends BaseAdapter {
    private Context context;
    int status = 1;//1,2,3;
    private int[] normalRes = {R.drawable.normal_a, R.drawable.normal_b, R.drawable.normal_c, R.drawable.normal_d, R.drawable.normal_e,
            R.drawable.normal_f, R.drawable.normal_g, R.drawable.normal_h, R.drawable.normal_i};
    private String[] normalString = {"痤疮", "痞满", "便秘", "腹泻", "消化性溃疡", "感冒", "咳嗽", "痹症", "虚症"};
    private int[] womenlRes = {R.drawable.women_a, R.drawable.women_b, R.drawable.women_c};
    private String[] womenString = {"高血压", "冠心病", "中风"};
    private int[] childRes = {R.drawable.child_a, R.drawable.child_b, R.drawable.child_c, R.drawable.child_d, R.drawable.child_e};
    private String[] childString = {"厌食", "积食", "咳嗽","感冒","腹泻"};
    public RecuperateAdapter2(Context context, int status) {
        this.context = context;
        this.status = status;
    }

    @Override
    public int getCount() {
        switch (status){
            case 1:
                return normalRes.length;

            case 2:
                return womenlRes.length;

            default:
                return childRes.length;
        }
    }

    @Override
    public Object getItem(int position) {

        switch (status){
            case 1:
                return  normalRes[position];

            case 2:
                return womenlRes[position];

            default:
                return childRes[position];
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recuperate_fragment_item, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView text = (TextView) convertView.findViewById(R.id.text);



        switch (status){
            case 1:
                imageView.setImageResource(normalRes[position]);
                text.setText(normalString[position]);
                break;
            case 2:
                imageView.setImageResource(womenlRes[position]);
                text.setText(womenString[position]);
                break;

            default:
                imageView.setImageResource(childRes[position]);
                text.setText(childString[position]);
                break;
        }

        return convertView;
    }
}
