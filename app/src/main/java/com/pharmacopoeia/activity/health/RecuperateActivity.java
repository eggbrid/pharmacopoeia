package com.pharmacopoeia.activity.health;

import com.pharmacopoeia.R;
import com.pharmacopoeia.activity.recuperate.RecuperateFragment;
import com.pharmacopoeia.base.CommentActivity;

/**
 * Created by xus on 2017/8/28.
 */

public class RecuperateActivity extends CommentActivity {
    private RecuperateFragment recuperateFragment;
    @Override
    public int setContentView() {
        return R.layout.health_recuperate_activity;
    }

    @Override
    public void initView() throws Exception {
        recuperateFragment=(RecuperateFragment)getSupportFragmentManager().findFragmentById(R.id.recuperateFragment);
        recuperateFragment.setCommentTitleView("症状查询");
        recuperateFragment.getTY2TYPE();
    }
}
