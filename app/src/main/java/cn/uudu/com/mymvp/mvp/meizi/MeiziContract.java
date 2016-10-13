package cn.uudu.com.mymvp.mvp.meizi;

import cn.uudu.com.mymvp.bean.MeiziData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MeiziContract {
    /**
     * V视图层
     */
    public interface IMeiziView {

        void showProgress();//可以显示进度条

        void hideProgress();//可以隐藏进度条

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showData( MeiziData meiziData);//显示数据到View上

        void showMoreData( MeiziData meiziData);//显示数据到View上
    }
    /**
     * P视图与逻辑处理的连接层
     */
    public interface IMeziPresenter {
        void getData(int page);
    }

    /**
     * 逻辑处理层
     */
    public interface IMeiziModel {
        void getMeiziData(int page,OnHttpCallBack<MeiziData> callBack);
    }

}
