package cn.uudu.com.mymvp.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.ZhiHu;
import cn.uudu.com.mymvp.bean.ZhiHuData;
import cn.uudu.com.mymvp.http.Urls;
import cn.uudu.com.mymvp.mvp.news.ZhihuContract;
import cn.uudu.com.mymvp.mvp.news.ZhihuPresenter;
import cn.uudu.com.mymvp.ui.activity.ZhiHuDetailActivity;
import cn.uudu.com.mymvp.ui.adapter.ZhihuItemAdapter;
import cn.uudu.com.mymvp.utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ZhiHuFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,ZhihuContract.IZhihuView{
    @InjectView(R.id.recycle_meizi)
    RecyclerView mRecycleNews;
    @InjectView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private ZhihuItemAdapter adapter;
    public List<ZhiHu> mDatas;
    private ZhihuPresenter zhihuPresenter;
    private String currentLoadDate;

    @Override
    protected int initLayoutId() {
        return R.layout.meizi_fragment_layout;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleNews.setLayoutManager(linearLayoutManager);


        adapter = new ZhihuItemAdapter(R.layout.item_news_layout,mDatas);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.openLoadMore(5);
        adapter.setOnLoadMoreListener(this);
        mRecycleNews.setAdapter(adapter);
        adapter.setLoadingView(LayoutInflater.from(getActivity()).inflate(R.layout.load_loading_layout,mRecycleNews, false));
        mRecycleNews.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtils.showToast(getActivity(), Integer.toString(position));
                Intent intent = new Intent(getActivity(), ZhiHuDetailActivity.class);
                intent.putExtra("entity", adapter.getData().get(position));
//                startActivity(intent);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_title), "transitionimg");

//                final android.support.v4.util.Pair<View, String>[] pairs = Help.createSafeTransitionParticipants
//                        (getActivity(), false, new android.support.v4.util.Pair<>(view.findViewById(R.id.iv_title), "transitionimg"),
//                                new android.support.v4.util.Pair<>(view.findViewById(R.id.boom), "transitionlin"));
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());


            }
        });
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        currentLoadDate = "0";
        zhihuPresenter = new ZhihuPresenter(this);
        zhihuPresenter.getData();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zhihuPresenter.getData();
            }
        }, 1000);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void showData(ZhiHuData datas) {
        mSwipeRefreshLayout.setRefreshing(false);
        currentLoadDate = datas.date;
        ZhiHu zhihu = new ZhiHu();
        zhihu.type = Urls.TIMES;
        zhihu.title = "今日热门";
        datas.stories.add(0, zhihu);
        adapter.setNewData(datas.stories);
        adapter.removeAllFooterView();

    }

    @Override
    public void showMoreData(ZhiHuData datas) {
        currentLoadDate = datas.date;
        ZhiHu zhihu = new ZhiHu();
        zhihu.type = Urls.TIMES;
        zhihu.title = convertDate(datas.date);
        datas.stories.add(0, zhihu);
        adapter.addData(datas.stories);
    }

    private String convertDate(String date) {
        String result = date.substring(0, 4);
        result += "年";
        result += date.substring(4, 6);
        result += "月";
        result += date.substring(6, 8);
        result += "日";
        return result;
    }

    @Override
    public void onLoadMoreRequested() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zhihuPresenter.getMoreData(currentLoadDate);
                //无更多数据时可以添加footerview   Adapter.addFooterView(notLoadingView);
            }
        }, 1000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
