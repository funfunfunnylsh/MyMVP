package cn.uudu.com.mymvp.http;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import cn.uudu.com.mymvp.MyApplication;
import rx.Subscriber;

/**
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private boolean mIsShowLoading;//是否显示加载loading

    public RxSubscriber(boolean isShowLoading) {
        mContext = MyApplication.getContext();
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        //统一处理请求异常的情况
        if (e instanceof IOException) {
            Toast.makeText(mContext, "网络链接异常...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        e.printStackTrace();
//        //失败的时候回调-----一下可以忽略 直接 callBack.onFaild("请求失败");
//        if (e instanceof HttpException) {
//            HttpException httpException = (HttpException) e;
//            //httpException.response().errorBody().string()
//            int code = httpException.code();
//            if (code == 500 || code == 404) {
//                callBack.onFaild("服务器出错");
//            }
//        } else if (e instanceof ConnectException) {
//            callBack.onFaild("网络断开,请打开网络!");
//        } else if (e instanceof SocketTimeoutException) {
//            callBack.onFaild("网络连接超时!!");
//        } else {
//            callBack.onFaild("发生未知错误" + e.getMessage());
//        }
        _onError();

        cancelLoading();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoading();
    }

    /**
     * 可在此处统一显示loading view
     */
    private void showLoading() {
        if (mIsShowLoading) {

        }
    }

    private void cancelLoading() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError();

}
