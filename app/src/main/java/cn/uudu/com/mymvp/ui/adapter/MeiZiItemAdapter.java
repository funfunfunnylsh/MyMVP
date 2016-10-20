package cn.uudu.com.mymvp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.uudu.com.mymvp.MyApplication;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.Meizi;

/**
 */
public class MeiZiItemAdapter extends BaseQuickAdapter<Meizi> {


    public MeiZiItemAdapter(int layoutResId, List<Meizi> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Meizi meizi) {
        ImageView imageView = baseViewHolder.getView(R.id.girl_item_iv);
//        ImageLoader.load(MyApplication.getContext(), meizi.url, imageView);
        Glide.with(MyApplication.getContext())
                .load(meizi.url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }
}
