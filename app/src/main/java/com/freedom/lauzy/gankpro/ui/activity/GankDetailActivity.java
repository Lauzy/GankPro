package com.freedom.lauzy.gankpro.ui.activity;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.common.base.BaseToolbarActivity;
import com.freedom.lauzy.gankpro.common.utils.IntentUtils;
import com.freedom.lauzy.gankpro.common.widget.LyWebView;
import com.freedom.lauzy.gankpro.function.constants.ValueConstants;
import com.freedom.lauzy.gankpro.function.entity.CollectionEntity;
import com.freedom.lauzy.gankpro.function.greendao.CollectionEntityDao;
import com.freedom.lauzy.gankpro.function.utils.SnackBarUtils;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.lauzy.freedom.lbehaviorlib.behavior.CommonBehavior;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.ACCELERATE_DECELERATE_ENTER_TYPE;
import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.BOUNCE_ENTER_TYPE;
import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.ENTER_TYPE;

public class GankDetailActivity extends BaseToolbarActivity {

    private static final String LYTAG = GankDetailActivity.class.getSimpleName();
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
    @BindView(R.id.tool_web_layout)
    LinearLayout mToolWebLayout;
    @BindView(R.id.activity_gank_detail)
    CoordinatorLayout mDetailView;
    private CommonBehavior mGankBehavior;
    private String mDetailUrl;
    private String mPublishTime;
    private String mGankDesc;
    private String mGankType;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initViews() {
//        setupWindowAnimations();
        getIntentData();
        initMainView();
    }

    private void initMainView() {
        mWbDetail.loadUrl(mDetailUrl);
//        mToolbarSubtitle.setText(detailUrl);
        mToolbarCommon.setTitle("");
        mToolbarCommon.setNavigationIcon(R.mipmap.icon_close);

        //使用此方法可少嵌套一层，顶部颜色为NestedScrollView的背景色，可自行选择
        /*int webTopPadding = DensityUtils.dp2px(GankDetailActivity.this, 40) + ScreenUtils.getStatusHeight(GankDetailActivity.this);
//        WebView.LayoutParams lp = new WebView.LayoutParams(WebView.LayoutParams.MATCH_PARENT,webTopPadding);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) mWbDetail.getLayoutParams();
        mlp.topMargin = webTopPadding;
        mWbDetail.setLayoutParams(mlp);*/

        mGankBehavior = CommonBehavior.from(mToolWebLayout);
        mGankBehavior.setCanScroll(false);
    }

    private void getIntentData() {
        mDetailUrl = getIntent().getStringExtra(ValueConstants.GANK_DETAIL);
        Log.i(LYTAG, "WebView Url is: " + mDetailUrl);
        mPublishTime = getIntent().getStringExtra(ValueConstants.GANK_PUBLISH_TIME);
        mGankDesc = getIntent().getStringExtra(ValueConstants.GANK_DESC);
        mGankType = getIntent().getStringExtra(ValueConstants.GANK_TYPE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setupWindowAnimations() {

        mDetailView.setTransitionGroup(true);//必须添加,指定父容器为过渡的整体单元
        int enterType = getIntent().getIntExtra(ENTER_TYPE, -1);
        if (enterType == BOUNCE_ENTER_TYPE) {
            getWindow().setEnterTransition(TransitionUtils.buildSlideExitTrans(new AccelerateDecelerateInterpolator()));
            getWindow().setExitTransition(TransitionUtils.buildSlideEnterTrans(new LinearOutSlowInInterpolator()));
            getWindow().setReturnTransition(TransitionUtils.buildSlideEnterTrans(new LinearOutSlowInInterpolator()));
//            getWindow().setEnterTransition(TransitionUtils.buildExplodeEnterAnim(new FastOutLinearInInterpolator()));
        } else if (enterType == ACCELERATE_DECELERATE_ENTER_TYPE) {
            getWindow().setEnterTransition(TransitionUtils.buildSlideEnterTrans(new FastOutSlowInInterpolator()));
            getWindow().setExitTransition(TransitionUtils.buildSlideExitTrans(new FastOutSlowInInterpolator()));
            getWindow().setReturnTransition(TransitionUtils.buildSlideExitTrans(new FastOutSlowInInterpolator()));
//            getWindow().setEnterTransition(TransitionUtils.buildExplodeEnterAnim(new FastOutLinearInInterpolator()));
        }

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

        mWbDetail.setWebViewClient(new WebViewClient() {
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
            case R.id.menu_collection:
                collectionTheGank();
                break;
            case R.id.menu_share:
                startActivity(IntentUtils.shareIntent(GankDetailActivity.this, "分享", mWbDetail.getUrl(), "分享链接"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String COLLECTION_REPEAT = "Already collection this article.";

    private void collectionTheGank() {
        Subscription collectionSub = Observable
                .create(new Observable.OnSubscribe<CollectionEntity>() {
                    @Override
                    public void call(Subscriber<? super CollectionEntity> subscriber) {
                        CollectionEntityDao entityDao = GankApp.getInstance().getDaoSession()
                                .getCollectionEntityDao();
                        CollectionEntity gankEntity;
                        try {
                            List<CollectionEntity> collectionEntities = entityDao.queryBuilder().build().list();
                            gankEntity = entityDao.queryBuilder().where(CollectionEntityDao
                                    .Properties.DetailUrl.eq(mDetailUrl)).unique();

                            for (int i = 0; i < collectionEntities.size(); i++) {
                                if (collectionEntities.get(i).getDetailUrl().equals(mDetailUrl)) {
                                    subscriber.onError(new Throwable(COLLECTION_REPEAT));
                                    break;
                                }
                            }
                            //如果重复收藏，则抛出指定信息的异常以提示正确的语句
//                            subscriber.onError(new Throwable(COLLECTION_REPEAT));

                        } catch (Exception e) {
                            gankEntity = null;
                        }
                        if (gankEntity == null) {
                            gankEntity = new CollectionEntity();
                            gankEntity.setDetailUrl(mDetailUrl);
                            gankEntity.setDate(mPublishTime);
                            gankEntity.setDesc(mGankDesc);
                            gankEntity.setType(mGankType);
                        }

                        entityDao.insertOrReplace(gankEntity);
                        subscriber.onNext(gankEntity);
                        subscriber.onCompleted();
                        Log.i(LYTAG, "call: " + mGankType + "--" + mGankDesc);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CollectionEntity>() {
                    @Override
                    public void call(CollectionEntity collectionEntity) {
                        SnackBarUtils.simpleSnackBar(findViewById(android.R.id.content), "收藏成功").show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (COLLECTION_REPEAT.equals(throwable.getMessage())) {
                            SnackBarUtils.simpleSnackBar(findViewById(android.R.id.content), "已经收藏过该文章了哦").show();
                        } else {
                            SnackBarUtils.simpleSnackBar(findViewById(android.R.id.content), "收藏失败，再试试").show();
                        }
                    }
                });
        addSubscription(collectionSub);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWbDetail.canGoBack()) {
            mWbDetail.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWbDetail != null)
            mWbDetail.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWbDetail != null)
            mWbDetail.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebProgressbar != null) {
            mWebProgressbar.setVisibility(View.GONE);
        }
        if (mWbDetail != null)
            mWbDetail.destroy();
    }
}
