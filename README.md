
# Gank 干货集中营客户端

## 介绍

「Gank」一款独特风格的干货集中营[Gank.io](http://gank.io)Android客户端。首页为妹子，还具有Android及其他分类的技术干货，可以保存妹子图片，本地收藏干货数据，切换夜间模式等。

## 体验
[下载地址](https://github.com/Lauzy/GankPro/raw/master/apk/gank.apk)

## 特征及技术点

1. 采用BottomNavigationView作为导航，自定义Behavior高仿知乎首页滑动隐藏显示标题栏、底部导航栏及浮动按钮；
2. MVP + RxJava + Retrofit + ButterKnife 开发，代码结构清晰，Retrofit+OkHttp实现网络加载及数据缓存；
3. Android Transition实现Activity的转场及共享元素动画；
4. 半透明(沉浸)状态栏，切换夜间模式；
5. RxPermissions动态权限判断，GreenDao实现收藏功能；
6. 个人中心页面及跳转均采用Fragment实现；
7. 每日内容通过自定义RecyclerView.ItemDecoration实现分组及悬停。

## 预览

<img src="/imgs/ScreenshotS1.png" alt="screenshot" title="screenshot" width="270" height="486" /> <img src="/imgs/ScreenshotS2.png" alt="screenshot" title="screenshot" width="270" height="486" />
<img src="/imgs/ScreenshotS4.png" alt="screenshot" title="screenshot" width="270" height="486" /> <img src="/imgs/ScreenshotS5.png" alt="screenshot" title="screenshot" width="270" height="486" />

## TODO、TO SOLVE
1. 分享功能
2. 夜间模式7.0+动画(可采用换肤框架实现)
3. ……

欢迎提[issues](https://github.com/Lauzy/GankPro/issues/new)、建议或Bug。


## 致谢
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Gson](https://github.com/google/gson)
- [Retrofit](https://github.com/square/retrofit)
- [Okhttp](https://github.com/square/okhttp)
- [ButterKnife](https://github.com/JakeWharton/butterknife)
- [Picasso](https://github.com/square/picasso)
- [RxPermissions](https://github.com/tbruyelle/RxPermissions)
- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
- [GreenDAO](https://github.com/greenrobot/greenDAO)

## 特别鸣谢
 [代码家](https://github.com/daimajia)  [干货集中营](http://gank.io/) 提供 API
