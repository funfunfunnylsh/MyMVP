package cn.uudu.com.mymvp.mvp.meizi;

import cn.uudu.com.mymvp.bean.MeiziData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MeiziPresenter implements MeiziContract.IMeziPresenter{

    private MeiziContract.IMeiziView iMeiziView;
    private MeiziModel meiziModel;

    public MeiziPresenter(MeiziContract.IMeiziView view){
        this.iMeiziView = view;
        meiziModel = new MeiziModel();
    };

    @Override
    public void getData(int page) {
        iMeiziView.showProgress();
        meiziModel.getMeiziData(page, new OnHttpCallBack<MeiziData>() {


            @Override
            public void onSuccessful(MeiziData meiziData) {
                iMeiziView.hideProgress();

                iMeiziView.showData(meiziData);


            }

            @Override
            public void onFaild(String errorMsg) {
                iMeiziView.hideProgress();
                iMeiziView.showErrorMsg(errorMsg);
            }
        });
    }


}
