package com.pharmacopoeia.activity.health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pharmacopoeia.R;
import com.pharmacopoeia.base.BaseLazyFragment;

/**
 * Created by 52243 on 2017/7/21.
 */

public class ShopApplyFragment extends BaseLazyFragment {

    private TextView text_app;
    public static String appContent;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.health_shop_apply_fragment, container, false);
        text_app = (TextView) mView.findViewById(R.id.text_app);
        return mView;
    }

    @Override
    protected void lazyLoad() {
        text_app.setText(appContent);
    }
}
