package cn.uudu.com.mymvp.mvp.news;

import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ZhihuPresenter implements ZhihuContract.IZhihuPresenter{

    private  ZhihuModel zhihuModel;
    private ZhihuContract.IZhihuView iZhihuView;

    public ZhihuPresenter(ZhihuContract.IZhihuView iZhihuView){
        this.iZhihuView = iZhihuView;
        zhihuModel = new ZhihuModel();
    }
    @Override
    public void getData() {
        zhihuModel.getZhihuData(new OnHttpCallBack<ZhiHuData>() {
            @Override
            public void onSuccessful(ZhiHuData zhiHuData) {
                iZhihuView.showData(zhiHuData);
            }

            @Override
            public void onFaild(String errorMsg) {
                iZhihuView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getMoreData(String data) {
        zhihuModel.getMoreZhihuData(data, new OnHttpCallBack<ZhiHuData>() {
            @Override
            public void onSuccessful(ZhiHuData zhiHuData) {
                iZhihuView.showMoreData(zhiHuData);
            }

            @Override
            public void onFaild(String errorMsg) {
                iZhihuView.showErrorMsg(errorMsg);
            }
        });
    }
}
