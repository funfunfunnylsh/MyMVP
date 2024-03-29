package cn.uudu.com.mymvp.mvp.news;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.uudu.com.mymvp.api.ZhiHuApi;
import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;
import cn.uudu.com.mymvp.http.RetrofitUtils;
import cn.uudu.com.mymvp.http.Urls;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ZhihuModel implements ZhihuContract.IZhihuModel{


    @Override
    public void getZhihuData(final OnHttpCallBack<ZhiHuData> callBack) {
        RetrofitUtils.newInstence(Urls.ZHIHU_URL)
                .create(ZhiHuApi.class)
                .getLastestNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ZhiHuData>() {
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
                    public void onNext(ZhiHuData zhiHuData) {
                        callBack.onSuccessful(zhiHuData);
                    }
                });

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Urls.ZHIHU_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Call<ZhiHuData> call = retrofit.create(ZhiHuService.class).getLastestNews();
//        call.enqueue(new Callback<ZhiHuData>() {
//            @Override
//            public void onResponse(Call<ZhiHuData> call, Response<ZhiHuData> response) {
//                ZhiHuData zhiHuData = response.body();
//                callBack.onSuccessful(zhiHuData);
//            }
//
//            @Override
//            public void onFailure(Call<ZhiHuData> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void getMoreZhihuData(String data, final OnHttpCallBack<ZhiHuData> callBack) {
        RetrofitUtils.newInstence(Urls.ZHIHU_URL)
                .create(ZhiHuApi.class)
                .getTheDaily(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ZhiHuData>() {
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
                    public void onNext(ZhiHuData zhiHuData) {
                        callBack.onSuccessful(zhiHuData);
                    }
                });
    }


}
