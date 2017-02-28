package com.freedom.lauzy.gankpro.common.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;

import butterknife.ButterKnife;

/**
 * base
 * Created by Lauzy on 2017/1/17.
 */
/**
 * activity基类
 * Created by Lauzy on 2016/11/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }*/
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //防止软键盘遮挡输入框
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initViews();
        loadData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    protected abstract void loadData();

    /**
     * 若存在toolBar，可使用
     */
   /* protected void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        if (toolbar != null) {
            toolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(RrlApp.getInstance());
            toolbar.setPadding(0, ScreenUtils.getStatusHeight(RrlApp.getInstance()), 0, 0);
            setSupportActionBar(toolbar);
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
//        Log.e("TAG", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("TAG", "onResume: ");
        /*MobclickAgent.onPageStart("BaseActivity");
        MobclickAgent.onResume(this);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("TAG", "onPause: ");
        /*MobclickAgent.onPageEnd("BaseActivity");
        MobclickAgent.onPause(this);*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.e("TAG", "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.e("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.e("TAG", "onDestroy: ");
    }
}