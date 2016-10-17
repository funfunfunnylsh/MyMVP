package cn.uudu.com.mymvp.mvp.girls;

import cn.uudu.com.mymvp.http.OnHttpCallBack;
import cn.uudu.com.mymvp.utils.JsoupUtil;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GirlsPresenter implements GirlsContract.IGirlPresenter{

    private GirlsContract.IGirlView iGirlView;
    private GirlsModel GirlModel;

    public GirlsPresenter(GirlsContract.IGirlView view){
        this.iGirlView = view;
        GirlModel = new GirlsModel();
    };

    @Override
    public void getData(String cid, int page) {
        GirlModel.getGirlData(cid,page, new OnHttpCallBack<String>() {


            @Override
            public void onSuccessful(String res) {
                iGirlView.showData(JsoupUtil.parseGirls(res));
            }

            @Override
            public void onFaild(String errorMsg) {
                iGirlView.showErrorMsg(errorMsg);
            }
        });
    }


}
