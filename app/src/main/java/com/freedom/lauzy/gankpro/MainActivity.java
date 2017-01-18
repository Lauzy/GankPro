package com.freedom.lauzy.gankpro;

import android.util.Log;
import android.widget.Toast;

import com.freedom.lauzy.gankpro.common.base.BaseActivity;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.net.ApiFactory;
import com.freedom.lauzy.gankpro.function.net.RetrofitUtil;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;

import rx.Subscriber;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {
        RetrofitUtil.loadGankData("Android", 1, new OnResponse<GankData>() {
            @Override
            public void onSuccess(GankData gankData) {
                int size = gankData.getResults().size();
                Log.e(TAG, "onSuccess: " + size);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void initViews() {

    }
}
