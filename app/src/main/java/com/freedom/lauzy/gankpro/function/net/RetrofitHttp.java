package com.freedom.lauzy.gankpro.function.net;

import android.content.Context;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.freedom.lauzy.gankpro.function.utils.CacheUtils;
import com.freedom.lauzy.gankpro.function.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.freedom.lauzy.gankpro.function.net.NetConstants.BASE_URL;

/**
 * RTHttp
 * Created by Lauzy on 2017/1/18.
 */
@SuppressWarnings("unused")
public class RetrofitHttp {

    ApiService apiService;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    private Context mContext;

    //cache
    private boolean isUseCache;
    private int maxCacheTime = 60;

    public void setMaxCacheTime(int maxCacheTime) {
        this.maxCacheTime = maxCacheTime;
    }

    public void setUseCache(boolean useCache) {
        isUseCache = useCache;
    }

    public ApiService getService() {
        if (apiService == null && retrofit != null) {
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public void init(Context context) {
        this.mContext = context;
        initOkHttp();
        initRetrofit();
        if (apiService == null) {
            apiService = retrofit.create(ApiService.class);
        }
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //print log
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        // cache reference http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(CacheUtils.getCacheDir(mContext), "httpCache");
//        Log.d("OkHttp", "Cache File---" + cacheFile.getAbsolutePath());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //network is useless
                if (!NetUtils.isNetworkConnected(mContext) || isUseCache) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
//                    Log.d("OkHttp", "net work is useless, request intercept");
                } else if (NetUtils.isNetworkConnected(mContext) && !isUseCache) {//网络可用
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
//                    Log.d("OkHttp", "net work is useful, request intercept");
                }
                Response response = chain.proceed(request);
                if (NetUtils.isNetworkConnected(mContext)) {//如果网络可用
//                    Log.d("OkHttp", "net work is useless, response intercept");
                    response = response.newBuilder()
                            //覆盖服务器响应头的Cache-Control,用我们自己的,因为服务器响应回来的可能不支持缓存
                            .header("Cache-Control", "public,max-age=" + maxCacheTime)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    Log.d("OkHttp", "网络不可用");
                    int maxStale = 60 * 60 * 24 * 5; // no network，five days
                    response= response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;

            }
        };
        builder.cache(cache);
        builder.interceptors().add(cacheInterceptor);//添加本地缓存拦截器，用来拦截本地缓存
        builder.networkInterceptors().add(cacheInterceptor);//添加网络拦截器，用来拦截网络数据
        //设置头部
//        Interceptor headerInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request originalRequest = chain.request();
//                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .header("myhead", "myhead")
//                        .header("Content-Type", "application/json")
//                        .header("Accept", "application/json")
//                        .method(originalRequest.method(), originalRequest.body());
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addInterceptor(headerInterceptor );
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> void toSubscribe(Observable<T> observable, LySubscriber<T> s) {
        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /*public class HttpResultFunc<T> implements Func1<BaseResult<T>, T> {

        @Override
        public T call(BaseResult<T> httpResult) {
            return httpResult.results;
        }
    }*/
}
