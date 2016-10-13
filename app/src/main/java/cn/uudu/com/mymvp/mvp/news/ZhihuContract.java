package cn.uudu.com.mymvp.mvp.news;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/9.
 */
public class ZhihuContract {
    /**
     * V视图层
     */
    public interface IZhihuView {

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showData(ZhiHuData news);//显示数据到View上

        void showMoreData(ZhiHuData news);//显示数据到View上
    }
    /**
     * P视图与逻辑处理的连接层
     */
    public interface IZhihuPresenter {
        void getData();

        void getMoreData(String data);
    }

    /**
     * 逻辑处理层
     */
    public interface IZhihuModel {
        Subscription getZhihuData(OnHttpCallBack<ZhiHuData> callBack);

        Subscription getMoreZhihuData(String data,OnHttpCallBack<ZhiHuData> callBack);
    }

}
