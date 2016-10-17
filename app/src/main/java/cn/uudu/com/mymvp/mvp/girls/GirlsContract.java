package cn.uudu.com.mymvp.mvp.girls;

import java.util.List;

import cn.uudu.com.mymvp.bean.GirlItemData;
import cn.uudu.com.mymvp.http.OnHttpCallBack;

/**
 * Created by Administrator on 2016/10/9.
 */
public class GirlsContract {
    /**
     * V视图层
     */
    public interface IGirlView {

        void showErrorMsg(String msg);//发生错误就显示错误信息

        void showData(List<GirlItemData> data);//显示数据到View上

    }
    /**
     * P视图与逻辑处理的连接层
     */
    public interface IGirlPresenter {
        void getData(String cid, int page);
    }

    /**
     * 逻辑处理层
     */
    public interface IGirlModel {
        void getGirlData(String cid, int page, OnHttpCallBack<String> callBack);
    }

}
