package com.pharmacopoeia.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.ShopType;

import java.util.List;


/**
 * Created by xus on 2016/11/16.
 */

public class GridDialog extends Dialog implements View.OnClickListener {
    protected GridView items;
    protected ImageView close;
    protected OnTabItemClickListener onTabItemClickListener;
    protected List<ShopType> list;

    public GridDialog.OnTabItemClickListener getOnPhotoClickListener() {
        return onTabItemClickListener;
    }

    public void setOnPhotoClickListener(GridDialog.OnTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    private Context context;
    private int select;
    public GridDialog(Context context, List<ShopType> list,int select) {
        super(context, R.style.Dialog_Fullscreen);
        this.context = context;
        this.list = list;
        this.select=select;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_grid);
        initView();
        setCanceledOnTouchOutside(true);
    }

    public void show(Activity context, View v) {
        super.show();
        WindowManager m = context.getWindowManager();

        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight());
        p.width = (int) (d.getWidth());    //宽度设置为全屏
        p.y = (int) v.getY();
        getWindow().setAttributes(p);
        getWindow().setWindowAnimations(R.style.main_menu_animstyle_down);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.close) {
            dismiss();
        }


    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        items = (GridView) findViewById(R.id.items);
        close.setOnClickListener(this);
        Dadapter dadapter = new Dadapter();
        items.setAdapter(dadapter);
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onTabItemClickListener.onTabItemClick(position);
                dismiss();
            }
        });

    }

    public interface OnTabItemClickListener {
        void onTabItemClick(int pos);

    }

    public class Dadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_tab, null);
            TextView item = (TextView) convertView.findViewById(R.id.item);
            item.setText(list.get(position).getCateName());
            if (select==position){
                item.setTextColor(context.getResources().getColor(R.color.c_3db584));
            }else{
                item.setTextColor(context.getResources().getColor(R.color.c_3c3c3c));
            }
            return convertView;
        }
    }

}
