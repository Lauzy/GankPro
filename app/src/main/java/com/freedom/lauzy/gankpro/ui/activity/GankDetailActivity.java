package com.freedom.lauzy.gankpro.ui.activity;

import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.widget.ProgressWebView;

import butterknife.BindView;

public class GankDetailActivity extends BaseToolbarActivity {

    @BindView(R.id.txt_toolbar_title)
    TextView mTxtTitle;
    @BindView(R.id.wb_detail)
    ProgressWebView mWbDetail;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }
}
