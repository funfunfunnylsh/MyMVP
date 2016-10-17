package cn.uudu.com.mymvp.api;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/11.
 */
public interface ZhiHuService {
    @GET("api/4/news/latest")
    Call<ZhiHuData> getLastestNews();

    @GET("api/4/news/before/{date}")
    Observable<ZhiHuData> getTheDaily(@Path("date") String date);

}
