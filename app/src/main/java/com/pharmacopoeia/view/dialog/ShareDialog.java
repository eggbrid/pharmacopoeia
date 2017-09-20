package com.pharmacopoeia.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.ShareItem;

import java.util.List;


/**
 * 日期选择�?
 *
 * @author Administrator
 */
public class ShareDialog extends Dialog implements AdapterView.OnItemClickListener {
    /**
     * 自定义Dialog监听�?
     */
    public interface MapJobOnItemClick {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    private MapJobOnItemClick lis;
    private static int theme = R.style.myDialog;// 主题
    private LinearLayout date_layout;
    private int width, height;// 对话框宽高
    private List<ShareItem> list;
    private GridView grid_view;
    private LayoutInflater mInflater;

    public ShareDialog(final Context context,
                       final MapJobOnItemClick listener
            , int width,
                       int height, List<ShareItem> list) {
        super(context, theme);
        lis = listener;
        this.width = width;
        this.height = height;
        this.list = list;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_shareapp_dialog_view);
        date_layout = (LinearLayout) findViewById(R.id.date_selelct_layout);
        LayoutParams lparams_hours = new LayoutParams(width, (int) (height / 3.5));
        date_layout.setLayoutParams(lparams_hours);
        grid_view = (GridView) date_layout.findViewById(R.id.grid_view);
        GridViewAdapter gridViewAdapter = new GridViewAdapter();
        grid_view.setAdapter(gridViewAdapter);
        grid_view.setOnItemClickListener(this);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        Button bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    public ShareDialog(Context context, MapJobOnItemClick listener) {
        super(context, theme);
    }

    public ShareDialog(Context context, String birthDate) {
        super(context, theme);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (lis != null) {
            lis.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = mInflater.inflate(R.layout.me_shareapp_dialog_item_view, parent, false);
                holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                holder.textView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            ShareItem shareItem = list.get(position);
            holder.imageView.setBackgroundResource(shareItem.getId());
            holder.textView.setText(shareItem.getName());
            return convertView;
        }

        public class Holder {
            private ImageView imageView;
            private TextView textView;
        }
    }
}
