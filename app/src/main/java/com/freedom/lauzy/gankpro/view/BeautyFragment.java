package com.freedom.lauzy.gankpro.view;


import android.util.Log;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;

import butterknife.BindView;

public class BeautyFragment extends BaseFragment {


    private static final String TAG = BeautyFragment.class.getSimpleName();
    @BindView(R.id.txt_beauty)
    TextView mTxtBeauty;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void loadData() {
        mTxtBeauty.setText("hahahhahaha");
    }
}
