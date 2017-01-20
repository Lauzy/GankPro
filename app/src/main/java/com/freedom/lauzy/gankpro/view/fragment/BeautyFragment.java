package com.freedom.lauzy.gankpro.view.fragment;


import android.icu.util.Calendar;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.DailyData;
import com.freedom.lauzy.gankpro.function.net.RetrofitUtil;
import com.freedom.lauzy.gankpro.function.net.callback.OnResponse;

import butterknife.BindView;

public class BeautyFragment extends BaseFragment {


    private static final String TAG = BeautyFragment.class.getSimpleName();

    @BindView(R.id.beauty_recycler_view)
    RecyclerView mBeautyRecyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void loadData() {
        RetrofitUtil.loadDailyData(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, new OnResponse<DailyData>() {
            @Override
            public void onSuccess(DailyData dailyData) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
