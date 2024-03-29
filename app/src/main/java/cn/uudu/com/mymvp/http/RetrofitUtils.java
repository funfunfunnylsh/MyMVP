package cn.uudu.com.mymvp.http;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.uudu.com.mymvp.MyApplication;
import cn.uudu.com.mymvp.utils.NetWorkUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retofit网络请求工具类
 */
public class RetrofitUtils {
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒

    private static Retrofit mRetrofit;

    private RetrofitUtils() {

    }

    public static Retrofit newInstence(String url) {
        mRetrofit = null;
        //构建Retrofit
        mRetrofit = new Retrofit.Builder()
                //设置OKHttpClient为网络客户端
                .client(client)//添加一个client,不然retrofit会自己默认添加一个
                .baseUrl(url) //配置服务器路径
                .addConverterFactory(GsonConverterFactory.create())//配置转化库，默认是Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//配置回调库，采用RxJava
                .build();
        return mRetrofit;
    }


    //设置缓存目录
    private static File cacheDirectory = new File(MyApplication.getContext().getCacheDir()
            .getAbsolutePath(), "HttpCache");
    private static Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);

    //设置拦截器
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    //初始化一个client,不然retrofit会自己默认添加一个
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

}
