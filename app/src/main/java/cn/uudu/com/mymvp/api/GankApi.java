package cn.uudu.com.mymvp.api;


import cn.uudu.com.mymvp.bean.MeiziData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface GankApi {

    @GET("api/data/福利/10/{page}")
    Observable<MeiziData> getMeizhiData(@Path("page") int page);

}
