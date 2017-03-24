package com.freedom.lauzy.gankpro.ui.fragment;


import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;

public class MineFragment extends BaseFragment {

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

    }
}
