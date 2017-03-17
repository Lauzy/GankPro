package com.freedom.lauzy.gankpro.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.DensityUtils;
import com.freedom.lauzy.gankpro.common.utils.ScreenUtils;
import com.freedom.lauzy.gankpro.common.widget.LyWebView;
import com.freedom.lauzy.gankpro.function.constants.ValueConstants;

import butterknife.BindView;

public class GankDetailActivity extends BaseToolbarActivity {

    @BindView(R.id.wb_detail)
    LyWebView mWbDetail;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.web_progressbar)
    ProgressBar mWebProgressbar;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @BindView(R.id.nsv_web)
    NestedScrollView mNsvWeb;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initViews() {
        String detailUrl = getIntent().getStringExtra(ValueConstants.GANK_DETAIL);
        mWbDetail.loadUrl(detailUrl);
//        mToolbarSubtitle.setText(detailUrl);
        mToolbarCommon.setTitle("");
        mToolbarCommon.setNavigationIcon(R.mipmap.icon_close);

        //使用此方法可少嵌套一层，顶部颜色为NestedScrollView的背景色，可自行选择
        /*int webTopPadding = DensityUtils.dp2px(GankDetailActivity.this, 40) + ScreenUtils.getStatusHeight(GankDetailActivity.this);
//        WebView.LayoutParams lp = new WebView.LayoutParams(WebView.LayoutParams.MATCH_PARENT,webTopPadding);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mWbDetail.getLayoutParams();
        mlp.topMargin = webTopPadding;
        mWbDetail.setLayoutParams(mlp);*/
    }

    @Override
    protected void loadData() {
        mWbDetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mWebProgressbar.setProgress(newProgress);
                if (newProgress >= 100)
                    mWebProgressbar.setVisibility(View.GONE);
                else
                    mWebProgressbar.setVisibility(View.VISIBLE);
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

        mWbDetail.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(view.getUrl());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mToolbarSubtitle.setText(url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_refresh:
                mWbDetail.reload();
                break;
            case R.id.menu_copy:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Url", mWbDetail.getUrl());
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(findViewById(android.R.id.content), "链接已复制", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.menu_open_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWbDetail.getUrl()));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWbDetail.canGoBack()) {
            mWbDetail.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
