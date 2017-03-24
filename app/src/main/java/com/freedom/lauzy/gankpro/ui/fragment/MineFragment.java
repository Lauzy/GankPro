package com.freedom.lauzy.gankpro.ui.fragment;


import android.content.Context;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.MineTitleListener;

public class MineFragment extends BaseFragment {

    private MineTitleListener mMineTitleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MineTitleListener){
            mMineTitleListener = (MineTitleListener) context;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        getFragmentManager().beginTransaction().replace(R.id.layout_mine_content, MinePageFragment.newInstance("", "")).commit();
    }

    @Override
    protected void loadData() {
        if (mMineTitleListener != null){
            mMineTitleListener.setTitle("关于");
        }
    }
}
