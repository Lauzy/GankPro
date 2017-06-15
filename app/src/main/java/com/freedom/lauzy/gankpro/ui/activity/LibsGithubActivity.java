package com.freedom.lauzy.gankpro.ui.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.widget.LyWebView;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

import butterknife.BindView;

public class LibsGithubActivity extends BaseToolbarActivity {

    public static final String LIB_LINK = "lib_link";
    @BindView(R.id.wb_libs)
    LyWebView mWbLibs;
    @BindView(R.id.nsv_web)
    NestedScrollView mNsvWeb;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @BindView(R.id.web_progressbar)
    ProgressBar mWebProgressbar;
    @BindView(R.id.tool_web_layout)
    LinearLayout mToolWebLayout;
    @BindView(R.id.activity_libs_detail)
    CoordinatorLayout mDetailView;
    private CommonBehavior mGankBehavior;

    public static Intent newInstance(Context context, String libLink) {
        Intent intent = new Intent(context, LibsGithubActivity.class);
        intent.putExtra(LIB_LINK, libLink);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_libs_github;
    }

    @Override
    protected void initViews() {
//        setupWindowAnimations();
        mToolbarCommon.setTitle("");
        mToolbarCommon.setNavigationIcon(R.mipmap.icon_close);
        mGankBehavior = CommonBehavior.from(mToolWebLayout);
        mGankBehavior.setCanScroll(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindowAnimations() {
        mDetailView.setTransitionGroup(true);//必须添加,指定父容器为过渡的整体单元
        getWindow().setEnterTransition(TransitionUtils.buildSlideEnterTrans(new AccelerateDecelerateInterpolator()));
        getWindow().setExitTransition(TransitionUtils.buildSlideEnterTrans(new LinearOutSlowInInterpolator()));
        getWindow().setReturnTransition(TransitionUtils.buildSlideEnterTrans(new LinearOutSlowInInterpolator()));
    }

    @Override
    protected void loadData() {
        String libLink = getIntent().getStringExtra(LIB_LINK);
        mWbLibs.loadUrl(libLink);
        mWbLibs.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mWebProgressbar.setProgress(newProgress);
                if (newProgress >= 100)
                    mWebProgressbar.setVisibility(View.GONE);
                else
                    mWebProgressbar.setVisibility(View.VISIBLE);

                if (newProgress >= 90) {//加载到90%设置可滑动
                    mGankBehavior.setCanScroll(true);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbarTitle.setText(title);
                mToolbarTitle.setMaxLines(1);
                mToolbarTitle.setMaxEms(10);
                mToolbarTitle.setSingleLine(true);
                mToolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
//                mToolbarSubtitle.setText(mWbDetail.getUrl());
            }
        });

        mWbLibs.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mToolbarSubtitle.setText(url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lib_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_refresh:
                mWbLibs.reload();
                break;
            case R.id.menu_copy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Url", mWbLibs.getUrl());
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(findViewById(android.R.id.content), "链接已复制", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_open_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWbLibs.getUrl()));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
