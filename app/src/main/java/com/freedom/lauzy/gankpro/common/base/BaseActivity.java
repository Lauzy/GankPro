package com.freedom.lauzy.gankpro.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);

        mTitleTextView = (TextView) findViewById(R.id.txt_toolbar_title);
        mBackImageView = (ImageView) findViewById(R.id.img_back);

        initViews();
        loadData();
    }

    protected void setActTitle(String title){
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    protected void setActTitle(int titleRes,int color){
        if (mTitleTextView != null) {
            mTitleTextView.setText(titleRes);
            mTitleTextView.setTextColor(color);
        }
    }

    protected void setBtnBackClickListener(View.OnClickListener listener) {
        if (mBackImageView != null) {
            mBackImageView.setVisibility(View.VISIBLE);
            mBackImageView.setOnClickListener(listener);
        }else {
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