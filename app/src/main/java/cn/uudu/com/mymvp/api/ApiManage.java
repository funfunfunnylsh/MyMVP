//package cn.uudu.com.mymvp.api;
//
//
//import java.io.File;
//import java.io.IOException;
//
//import cn.uudu.com.mymvp.MyApplication;
//import cn.uudu.com.mymvp.utils.NetWorkUtil;
//import okhttp3.Cache;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Response;
//import retrofit.GsonConverterFactory;
//import retrofit.Retrofit;
//import retrofit.RxJavaCallAdapterFactory;
//
///**
// * Created by xinghongfei on 16/8/12.
// */
//public class ApiManage {
//
//    public static ApiManage apiManage;
//
//    public static ApiManage getInstence() {
//        if (apiManage == null) {
//            synchronized (ApiManage.class) {
//                if (apiManage == null) {
//                    apiManage = new ApiManage();
//                }
//            }
//        }
//        return apiManage;
//    }
//
//    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response originalResponse = chain.proceed(chain.request());
//            if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
//                int maxAge = 60; // 在线缓存在1分钟内可读取
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//        }
//    };
//
//    private static File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "myCache");
//    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
//    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
//
//    private static OkHttpClient client = new OkHttpClient.Builder()
//            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//            .cache(cache)
//            .build();
//
//
//
//    public GankApi ganK;
//    public GankApi getGankService(String url){
//        if (ganK==null){
//                if (ganK==null){
//                    ganK=new Retrofit.Builder()
//                            .baseUrl(url)
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                            .client(client)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build().create(GankApi.class);
//
//                }
//
//        }
//        return ganK;
//    }
//
//}
