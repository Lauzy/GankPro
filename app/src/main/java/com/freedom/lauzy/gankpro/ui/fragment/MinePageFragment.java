package com.freedom.lauzy.gankpro.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.common.widget.RvItemTouchListener;
import com.freedom.lauzy.gankpro.function.MineTitleListener;
import com.freedom.lauzy.gankpro.function.view.AndroidItemDecoration;
import com.freedom.lauzy.gankpro.ui.adapter.MineAdapter;

import butterknife.BindView;

public class MinePageFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    @BindView(R.id.rv_mine)
    RecyclerView mRvMine;

    private MineTitleListener mMineTitleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MineTitleListener) {
            mMineTitleListener = (MineTitleListener) context;
        }
    }

    public MinePageFragment() {
    }

    public static MinePageFragment newInstance(String param1, String param2) {
        MinePageFragment fragment = new MinePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine_page;
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(mActivity, 1);
        mRvMine.setLayoutManager(manager);
        mRvMine.addItemDecoration(new AndroidItemDecoration(mActivity));
        MineAdapter adapter = new MineAdapter(mActivity);
        mRvMine.setAdapter(adapter);
        mRvMine.setNestedScrollingEnabled(false);
    }


    @Override
    protected void loadData() {
        mRvMine.addOnItemTouchListener(new RvItemTouchListener(mActivity, mRvMine, new RvItemTouchListener.RvItemClickListener() {

            @Override
            public void rvItemClick(int position) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        transaction.replace(R.id.layout_mine_content, CollectionFragment.newInstance("", ""))
                                .addToBackStack(null);
                        break;
                    case 3:
                        transaction.replace(R.id.layout_mine_content, OpenLibsFragment.newInstance("", ""))
                                .addToBackStack(null);
                        break;
                    case 4:
                        transaction.replace(R.id.layout_mine_content, AuthorFragment.newInstance("", ""))
                                .addToBackStack(null);
                        break;
                }
                transaction.commit();
            }

            @Override
            public void rvItemLongClick(int position) {

            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMineTitleListener != null) {
            mMineTitleListener.setTitle("关于");
        }
    }
}
