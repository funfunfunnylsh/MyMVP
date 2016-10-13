package cn.uudu.com.mymvp.api;


import cn.uudu.com.mymvp.bean.MeiziData;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface GankApi {

    @GET("/api/data/福利/10/{page}")
    Observable<MeiziData> getMeizhiData(@Path("page") int page);

}
