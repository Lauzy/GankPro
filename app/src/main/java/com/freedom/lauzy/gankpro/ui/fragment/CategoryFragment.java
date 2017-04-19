package com.freedom.lauzy.gankpro.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseFragment;
import com.freedom.lauzy.gankpro.common.utils.DensityUtils;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;
import com.freedom.lauzy.gankpro.function.view.ZoomOutPageTransformer;
import com.freedom.lauzy.gankpro.ui.adapter.CategoryPagerAdapter;

import butterknife.BindArray;
import butterknife.BindView;

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.tab_category)
    TabLayout mTabCategory;
    @BindView(R.id.vp_category)
    ViewPager mVpCategory;
    @BindArray(R.array.CategoryArr)
    String[] mCategoryArrTitle;
    @BindView(R.id.category_content)
    LinearLayout mCategoryContent;

  /*  private static final String BEHAVIOR = "category_behavior";
    private GankBottomBehavior mBottomBehavior;

    public static CategoryFragment newInstance(GankBottomBehavior bottomBehavior) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(BEHAVIOR, bottomBehavior);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBottomBehavior = (GankBottomBehavior) getArguments().getSerializable(BEHAVIOR);
        }
    }*/

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initViews() {
        //iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
        CategoryPagerAdapter pagerAdapter = new CategoryPagerAdapter(getChildFragmentManager(), mCategoryArrTitle);
        pagerAdapter.setTitle(mCategoryArrTitle);
        mVpCategory.setAdapter(pagerAdapter);
        mTabCategory.setupWithViewPager(mVpCategory);

        int statusTitle = DensityUtils.dp2px(mActivity, 40) + ScreenUtils.getStatusHeight(mActivity);
        mCategoryContent.setPadding(0, statusTitle, 0, 0);

        mVpCategory.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    protected void loadData() {
//        mNsCategory.setFillViewport(true);
        //设置Vp缓存
        mVpCategory.setOffscreenPageLimit(6);
    }
}
