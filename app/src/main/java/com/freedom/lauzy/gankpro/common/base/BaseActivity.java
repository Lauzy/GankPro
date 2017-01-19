package com.freedom.lauzy.gankpro.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 基类
 * Created by Lauzy on 2017/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);

        initViews();
        loadData();
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

/*
*
* public abstract class BaseAppCompatActivity extends AppCompatActivity {
      private static final String TAG = BaseAppCompatActivity.class.getSimpleName();
      private TextView mToolbarTitle;
      private TextView mToolbarSubTitle;
      private Toolbar mToolbar;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(getLayoutId());

          mToolbar = (Toolbar) findViewById(R.id.toolbar);
         */
/*
          toolbar.setLogo(R.mipmap.ic_launcher);
          toolbar.setTitle("Title");
          toolbar.setSubtitle("Sub Title");
          *//*

mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        if (mToolbar != null) {
        //将Toolbar显示到界面
        setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
        //getTitle()的值是activity的android:lable属性值
        mToolbarTitle.setText(getTitle());
        //设置默认的标题不显示
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        }

@Override
protected void onStart() {
        super.onStart();
        */
/**
         * 判断是否有Toolbar,并默认显示返回按钮
         *//*

        if(null != getToolbar() && isShowBacking()){
        showBack();
        }
        }

*/
/**
 * 获取头部标题的TextView
 * @return
 *//*

public TextView getToolbarTitle(){
        return mToolbarTitle;
        }
*/
/**
 * 获取头部标题的TextView
 * @return
 *//*

public TextView getSubTitle(){
        return mToolbarSubTitle;
        }

*/
/**
 * 设置头部标题
 * @param title
 *//*

public void setToolBarTitle(CharSequence title) {
        if(mToolbarTitle != null){
        mToolbarTitle.setText(title);
        }else{
        getToolbar().setTitle(title);
        setSupportActionBar(getToolbar());
        }
        }

*/
/**
 * this Activity of tool bar.
 * 获取头部.
 * @return support.v7.widget.Toolbar.
 *//*

public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
        }

*/
/**
 * 版本号小于21的后退按钮图片
 *//*

private void showBack(){
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.mipmap.icon_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        onBackPressed();
        }
        });
        }

*/
/**
 * 是否显示后退按钮,默认显示,可在子类重写该方法.
 * @return
 *//*

protected boolean isShowBacking(){
        return true;
        }

*/
/**
 * this activity layout res
 * 设置layout布局,在子类重写该方法.
 * @return res layout xml id
 *//*

protected abstract int getLayoutId();

@Override
protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy...");

        }
        }
*
* */

