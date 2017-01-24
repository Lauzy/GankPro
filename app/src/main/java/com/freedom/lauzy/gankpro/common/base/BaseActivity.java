package com.freedom.lauzy.gankpro.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;

import butterknife.ButterKnife;

/**
 * base
 * Created by Lauzy on 2017/1/17.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private TextView mTitleTextView;
    private ImageView mBackImageView;
    private View mStatusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        /*if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            mStatusView = new View(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getStatusHeight(this));
//            Log.e(TAG, "onCreate: 状态栏高度: " + ScreenUtils.getStatusHeight(this));
            params.gravity = Gravity.TOP;

            mStatusView.setLayoutParams(params);
            mStatusView.setVisibility(View.VISIBLE);
            decorViewGroup.addView(mStatusView);
        }*/

        mTitleTextView = (TextView) findViewById(R.id.txt_toolbar_title);
        mBackImageView = (ImageView) findViewById(R.id.img_back);

        initViews();
        loadData();
    }

    /*protected void setStatusColor(int color) {
        mStatusView.setBackgroundColor(color);
    }*/

    /*protected void setStatusColor(int colorRes){
        mStatusView.setBackgroundResource(colorRes);
    }*/

    protected void setActTitle(String title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    protected void setActTitle(int titleRes, int color) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(titleRes);
            mTitleTextView.setTextColor(color);
        }
    }

    protected void setBtnBackClickListener(View.OnClickListener listener) {
        if (mBackImageView != null) {
            mBackImageView.setVisibility(View.VISIBLE);
            mBackImageView.setOnClickListener(listener);
        } else {
            Log.e(TAG, "setBtnBackClickListener: backImg is null, WTF");
        }
    }

    protected abstract int setLayoutId();

    protected abstract void loadData();

    protected abstract void initViews();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}