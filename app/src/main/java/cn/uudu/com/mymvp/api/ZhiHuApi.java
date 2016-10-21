package cn.uudu.com.mymvp.api;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.bean.ZhiHuStory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */
public interface ZhiHuApi {
    @GET("api/4/news/latest")
    Observable<ZhiHuData> getLastestNews();

    @GET("api/4/news/before/{date}")
    Observable<ZhiHuData> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhiHuStory> getZhihuStory(@Path("id") String id);

}
