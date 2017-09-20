package com.pharmacopoeia.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.bean.model.SelectWindowModel;
import com.pharmacopoeia.view.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 下面弹框
 *
 * @author Administrator
 */
public class SelectDialog extends Dialog {
    private View mMenuView;
    private List<SelectWindowModel> models;
    private CustomListView nlv;
    private Context context;
    private setOnSelectItemCliclk selectItemCliclk;
    private boolean isInit = false;
    //private View view;
    private TextView tv_title;
    private Button bt_cancel;

    public SelectDialog setSelectItemCliclk(
            setOnSelectItemCliclk selectItemCliclk) {
        this.selectItemCliclk = selectItemCliclk;
        return this;
    }

    public SelectDialog(Context context, View view) {
        super(context, R.style.transparentFrameWindowStyle);
        this.context = context;
        //this.view = view;
        mMenuView = LayoutInflater.from(context).inflate(
                R.layout.me_select_dialog_view, null);
        tv_title = (TextView) mMenuView.findViewById(R.id.tv_title);
        bt_cancel = (Button) mMenuView.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDismiss();
            }
        });

        setContentView(mMenuView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画


        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;

        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = height2;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        mDismiss();
                    }
                }
                return true;
            }
        });

        // // 设置SelectPicPopupWindow的View
        // this.setContentView(mMenuView);
        // // 设置SelectPicPopupWindow弹出窗体的宽
        // this.setWidth(LayoutParams.MATCH_PARENT);
        // // 设置SelectPicPopupWindow弹出窗体的高
        // this.setHeight(LayoutParams.WRAP_CONTENT);
        // // 设置SelectPicPopupWindow弹出窗体可点击
        // this.setFocusable(true);
        // // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        // // 实例化一个ColorDrawable颜色为透明
        // ColorDrawable dw = new ColorDrawable(0x80000000);
        // // 设置SelectPicPopupWindow弹出窗体的背景
        // this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        // mMenuView.setOnTouchListener(new OnTouchListener() {
        //
        // public boolean onTouch(View v, MotionEvent event) {
        //
        // int height = mMenuView.findViewById(R.id.pop_layout).getTop();
        // int y = (int) event.getY();
        // if (event.getAction() == MotionEvent.ACTION_UP) {
        // if (y < height) {
        // dismiss();
        // }
        // }
        // return true;
        // }
        // });
    }

    /**
     * 添加行布局
     *
     * @param type
     * @param value
     */
    public SelectDialog addItem(int type, String value) {
        if (models == null) {
            models = new ArrayList<SelectWindowModel>();
        }
        models.add(new SelectWindowModel(value, type));
        return this;
    }

    public SelectDialog setTitle(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
            mMenuView.findViewById(R.id.v_title).setVisibility(View.VISIBLE);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 初始化
     */
    public void init() {
        isInit = true;
        nlv = (CustomListView) mMenuView.findViewById(R.id.nlv);
        nlv.setAdapter(new SelectWindowAdpter(context, models));
        nlv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (selectItemCliclk != null) {
                    selectItemCliclk.onSelectItemClick(models.get(position)
                            .getType(), models.get(position).getValue());
                }
            }
        });

        // setOnDismissListener(new OnDismissListener() {
        // @Override
        // public void onDismiss() {
        // // 设置背景颜色变暗
        // Activity activity;
        // if (context instanceof Activity) {
        // activity = (Activity) context;
        // WindowManager.LayoutParams lp = activity.getWindow()
        // .getAttributes();
        // lp.alpha = 1.0f;
        // activity.getWindow().setAttributes(lp);
        // }
        //
        // }
        // });
    }

    // public void show() {

    // }

    public void mDismiss(){
        selectItemCliclk.onSelectItemClick(-1,"");
        super.dismiss();
    }

    @Override
    public void show() {
        if (!isInit) {
            init();
        }
        super.show();
    }

    /**
     * @author Administrator
     */
    public interface setOnSelectItemCliclk {
        public void onSelectItemClick(int type, String value);
    }
}
