package cn.uudu.com.mymvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.uudu.com.mymvp.MyApplication;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.GirlItemData;
import cn.uudu.com.mymvp.utils.ImageLoader;

/**
 */
public class GirlsItemAdapter extends BaseQuickAdapter<GirlItemData> {


    public GirlsItemAdapter(int layoutResId, List<GirlItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GirlItemData girl) {
        ImageView imageView = baseViewHolder.getView(R.id.girl_item_iv);
        ImageLoader.load(MyApplication.getContext(), girl.getUrl(), imageView);
    }
}
