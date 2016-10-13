package cn.uudu.com.mymvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.InjectView;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.utils.ImageLoader;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Time:
 */
public class MeiZhiDetailFragment extends BaseFragment {
    private static final String URL = "url";

    private String mUrl;

    @InjectView(R.id.girl_detail_iv)
    PhotoView mImageView;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_girl_detail;
    }

    @Override
    protected void initView() {
        ImageLoader.load(getActivity(), mUrl, mImageView);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mImageView);
        attacher.update();
    }

    @Override
    protected void initData() {
        if (getArguments() == null) {
            return;
        }
        mUrl = getArguments().getString(URL);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static MeiZhiDetailFragment newInstance(String url) {
        MeiZhiDetailFragment fragment = new MeiZhiDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(URL, url);
        fragment.setArguments(arguments);
        return fragment;
    }
}
