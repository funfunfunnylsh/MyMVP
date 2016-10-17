package cn.uudu.com.mymvp.ui.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.http.Urls;
import cn.uudu.com.mymvp.bean.ZhiHu;
import cn.uudu.com.mymvp.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/12.
 */
public class ZhihuItemAdapter extends BaseQuickAdapter<ZhiHu>{


    public ZhihuItemAdapter(int layoutResId, List<ZhiHu> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhiHu zhiHu) {
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_topic = baseViewHolder.getView(R.id.tv_topic);
        ImageView imageView = baseViewHolder.getView(R.id.iv_title);
        CardView cardview = baseViewHolder.getView(R.id.cardview);
        if(zhiHu.type == Urls.TIMES ){
            tv_topic.setVisibility(View.VISIBLE);
            cardview.setVisibility(View.GONE);
            tv_topic.setText(zhiHu.title);
        }else{
            tv_topic.setVisibility(View.GONE);
            cardview.setVisibility(View.VISIBLE);
            tv_title.setText(zhiHu.title);
            ImageLoader.load(mContext, zhiHu.images.get(0), imageView, R.color.background);

        }
    }
}
