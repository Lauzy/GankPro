package com.freedom.lauzy.gankpro.function.net;

import android.content.Context;
import android.util.Log;

import com.freedom.lauzy.gankpro.common.utils.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lauzy on 2017/3/14.
 */

public class RetrofitInterceptor implements Interceptor {

    private boolean isUseCache;
    private Context mContext;
    private int mMaxCacheTime;

    public RetrofitInterceptor(Context context, boolean isUseCache, int maxCacheTime) {
        this.isUseCache = isUseCache;
        mContext = context;
        mMaxCacheTime = maxCacheTime;
    }

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
                    .header("Cache-Control", "public,max-age=" + mMaxCacheTime)
                    .removeHeader("Pragma")
                    .build();
        } else {
            Log.d("OkHttp", "网络不可用");
            int maxStale = 60 * 60 * 24 * 5; // no network，five days
            response = response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
