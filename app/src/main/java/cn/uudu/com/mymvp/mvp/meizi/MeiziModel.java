package cn.uudu.com.mymvp.mvp.meizi;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.uudu.com.mymvp.api.GankApi;
import cn.uudu.com.mymvp.api.Urls;
import cn.uudu.com.mymvp.bean.MeiziData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;
import cn.uudu.com.mymvp.http.RetrofitUtils;
import retrofit.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MeiziModel implements MeiziContract.IMeiziModel{


    @Override
    public void getMeiziData(int page, final OnHttpCallBack<MeiziData> callBack) {
        RetrofitUtils.newInstence(Urls.MEIZHI_URL)
                .create(GankApi.class)
                .getMeizhiData(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<MeiziData>() {
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
                    public void onNext(MeiziData meiziData) {
                        callBack.onSuccessful(meiziData);
                    }
                });
    }
}
