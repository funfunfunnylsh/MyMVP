package cn.uudu.com.mymvp.mvp.girls;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.uudu.com.mymvp.api.GirlApi;
import cn.uudu.com.mymvp.http.GirlsUtils;
import cn.uudu.com.mymvp.http.OnHttpCallBack;
import cn.uudu.com.mymvp.http.Urls;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GirlsModel implements GirlsContract.IGirlModel{


    @Override
    public void getGirlData(String cid, int page, final OnHttpCallBack<String> callBack) {
        GirlsUtils.newInstence(Urls.URL_GET_GIRL)
                .create(GirlApi.class)
                .getGirlItemData(cid,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //失败的时候回调-----一下可以忽略 直接 callBack.onFaild("请求失败");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        callBack.onSuccessful(s);
                    }
                });
    }
}
