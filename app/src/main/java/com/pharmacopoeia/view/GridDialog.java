package com.pharmacopoeia.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.ShopType;

import java.util.List;


/**
 * Created by xus on 2016/11/16.
 */

public class GridDialog extends Dialog implements View.OnClickListener {
    protected GridView items;
    protected ImageView close;
    protected onPhotoClickListener onPhotoClickListener;

    public GridDialog.onPhotoClickListener getOnPhotoClickListener() {
        return onPhotoClickListener;
    }

    public void setOnPhotoClickListener(GridDialog.onPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public GridDialog(Context context) {
        super(context, R.style.Dialog_Fullscreen);
    }

    public GridDialog(Context context, int themeResId) {
        super(context, themeResId);

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
        getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.photo) {
            onPhotoClickListener.onPhotoClick();

        } else if (view.getId() == R.id.camera) {
            onPhotoClickListener.onCameraClick();
        }
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        items = (GridView) findViewById(R.id.items);
    }

    public interface onPhotoClickListener {
        void onPhotoClick();

        void onCameraClick();

    }

    public class Dadapter extends BaseAdapter {
        private List<ShopType> list;

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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}
