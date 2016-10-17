package cn.uudu.com.mymvp.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/14.
 */
public interface GirlApi {

    @GET("dbgroup/show.htm")
    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);
}
