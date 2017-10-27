package com.pharmacopoeia.activity.me.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseViewHolder;
import com.pharmacopoeia.base.PBaseAdapter;
import com.pharmacopoeia.bean.cache.TKAddress;
import com.pharmacopoeia.util.T;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by xus on 2017/9/14.
 */

public class AddressListAdapter extends PBaseAdapter<TKAddress, AddressListAdapter.ViewHolder> {

    public AddressListAdapter(Context context, List<TKAddress> list) {
        super(context, list);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup, ViewHolder viewHolder) {
        final TKAddress tkAddress = list.get(i);
        viewHolder.address.setText("地址：" + tkAddress.getAddress());
        viewHolder.name.setText(tkAddress.getName());
        viewHolder.phone.setText("电话：" + tkAddress.getPhone());
        if (tkAddress.isVis()) {
            viewHolder.open.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(R.drawable.up_icon);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.c_20Af78));
        } else {
            viewHolder.open.setVisibility(View.GONE);
            viewHolder.icon.setImageResource(R.drawable.down_icon);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.c_333333));
        }
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setVis(!tkAddress.isVis());
                notifyDataSetChanged();
            }
        });
        viewHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = tkAddress.getPhone();
                number = number.replace("（传真）", "");
                number = number.split("/")[0];
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1001);
                    T.show(context,"请检查拨打电话权限是否开启");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (!((Activity)context).shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
//                            AskForPermission();
//                        }
//                    }

//                    T.show(context,"请检查拨打电话权限是否开启");

                    return;
                }
                context.startActivity(intent2);
            }
        });
        return view;
    }
    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("需要开启拨打电话的权限!");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                T.show(context,"请检查拨打电话权限是否开启");
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName())); // 根据包名打开对应的设置界面
                context.startActivity(intent);
            }
        });
        builder.create().show();
    }
    @Override
    public View getLayoutView(int i, LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.me_address_item_f, viewGroup, false);
    }

    @Override
    public ViewHolder setViewHolder(View view, int pos) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView name, phone, address;
        private LinearLayout open;
        private ImageView icon;
        private RelativeLayout title;

        public ViewHolder(View root) {
            super(root);
        }

        @Override
        public void initView(View root) {
            name = (TextView) root.findViewById(R.id.name);
            phone = (TextView) root.findViewById(R.id.phone);
            address = (TextView) root.findViewById(R.id.address);
            open = (LinearLayout) root.findViewById(R.id.open);
            icon = (ImageView) root.findViewById(R.id.icon);
            title = (RelativeLayout) root.findViewById(R.id.title);
        }
    }
}
