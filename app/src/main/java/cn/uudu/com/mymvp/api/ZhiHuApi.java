package cn.uudu.com.mymvp.api;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */
public interface ZhiHuApi {
    @GET("/api/4/news/latest")
    Observable<ZhiHuData> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhiHuData> getTheDaily(@Path("date") String date);

}
