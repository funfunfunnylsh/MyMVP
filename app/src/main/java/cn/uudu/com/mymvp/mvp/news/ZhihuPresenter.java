package cn.uudu.com.mymvp.mvp.news;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;
import cn.uudu.com.mymvp.mvp.BasePresenter;
import rx.Subscription;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ZhihuPresenter extends BasePresenter implements ZhihuContract.IZhihuPresenter{

    private  ZhihuModel zhihuModel;
    private ZhihuContract.IZhihuView iZhihuView;

    public ZhihuPresenter(ZhihuContract.IZhihuView iZhihuView){
        this.iZhihuView = iZhihuView;
        zhihuModel = new ZhihuModel();
    }
    @Override
    public void getData() {
        Subscription subscription = zhihuModel.getZhihuData(new OnHttpCallBack<ZhiHuData>() {
            @Override
            public void onSuccessful(ZhiHuData zhiHuData) {
                iZhihuView.showData(zhiHuData);
            }

            @Override
            public void onFaild(String errorMsg) {
                iZhihuView.showErrorMsg(errorMsg);
            }
        });
        addSubscription(subscription);
    }

    @Override
    public void getMoreData(String data) {
        Subscription subscription = zhihuModel.getMoreZhihuData(data, new OnHttpCallBack<ZhiHuData>() {
            @Override
            public void onSuccessful(ZhiHuData zhiHuData) {
                iZhihuView.showMoreData(zhiHuData);
            }

            @Override
            public void onFaild(String errorMsg) {
                iZhihuView.showErrorMsg(errorMsg);
            }
        });
        addSubscription(subscription);
    }
}
