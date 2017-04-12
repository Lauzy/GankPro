package com.freedom.lauzy.gankpro.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.function.entity.AuthorEntity;
import com.freedom.lauzy.gankpro.function.entity.LibEntity;
import com.freedom.lauzy.gankpro.ui.activity.LibsGithubActivity;
import com.freedom.lauzy.gankpro.ui.adapter.AuthorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;


public class AuthorFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_author)
    RecyclerView mRvAuthor;
    @BindArray(R.array.AuthorTipArr)
    String[] mAuthorTips;
    @BindArray(R.array.AuthorUrlArr)
    String[] mAuthorUrls;

    public AuthorFragment() {
    }

    public static AuthorFragment newInstance(String param1, String param2) {
        AuthorFragment fragment = new AuthorFragment();
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
        return R.layout.fragment_author;
    }

    @Override
    protected void initViews() {
        mRvAuthor.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvAuthor.setNestedScrollingEnabled(false);
    }

    @Override
    protected void loadData() {

        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (int i = 0; i < mAuthorTips.length; i++) {
            AuthorEntity libEntity = new AuthorEntity(mAuthorTips[i], mAuthorUrls[i]);
            authorEntities.add(libEntity);
        }
        mRvAuthor.setAdapter(new AuthorAdapter(R.layout.layout_author_item, authorEntities));
        mRvAuthor.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AuthorEntity authorEntity = (AuthorEntity) adapter.getData().get(position);
                mActivity.startActivity(LibsGithubActivity.newInstance(mActivity, authorEntity.getUrl()));
            }
        });
    }

}
