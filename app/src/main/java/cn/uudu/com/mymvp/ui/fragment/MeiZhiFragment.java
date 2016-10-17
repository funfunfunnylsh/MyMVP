package cn.uudu.com.mymvp.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.Meizi;
import cn.uudu.com.mymvp.bean.MeiziData;
import cn.uudu.com.mymvp.mvp.meizi.MeiziContract;
import cn.uudu.com.mymvp.mvp.meizi.MeiziPresenter;
import cn.uudu.com.mymvp.ui.activity.MeiZhiDetailActivity;
import cn.uudu.com.mymvp.ui.adapter.MeiZiItemAdapter;
import cn.uudu.com.mymvp.utils.ToastUtils;

/**
 * Created by Administrator on 2016/9/6.
 */
public class MeiZhiFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,MeiziContract.IMeiziView{

    @InjectView(R.id.recycle_meizi)
    RecyclerView mRecycleMeizi;
    @InjectView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.prograssbar)
    ProgressBar pbProgress;


    private MeiziPresenter meiziPresenter;
    private List<Meizi> mDatas;
    private MeiZiItemAdapter adapter;
    private int page = 1;//从第几开始
    private boolean isLoadMore=false;
    private boolean isFirst=true;

    @Override
    protected int initLayoutId() {
        return R.layout.meizi_fragment_layout;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecycleMeizi.setLayoutManager(layoutManager);

        adapter = new MeiZiItemAdapter(R.layout.item_girl_layout, new ArrayList<Meizi>());
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnLoadMoreListener(this);
        mRecycleMeizi.setAdapter(adapter);
        adapter.setLoadingView(LayoutInflater.from(getActivity()).inflate(R.layout.load_loading_layout, (ViewGroup) mRecycleMeizi.getParent(), false));
        mRecycleMeizi.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtils.showToast(getActivity(), Integer.toString(position));
                Intent intent = new Intent(getActivity(), MeiZhiDetailActivity.class);
                intent.putExtra("meizi_data", (ArrayList) adapter.getData());
                intent.putExtra("position", position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view, "shareView");
                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());

            }
        });



    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        meiziPresenter = new MeiziPresenter(this);
        meiziPresenter.getData(1);
    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoadMore = false;
                meiziPresenter.getData(1);
            }
        }, 1000);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMsg(String msg) {
        adapter.showLoadMoreFailedView();
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtils.showToast(getActivity(), msg);
        if(page>1){
            page--;
        }
    }

    @Override
    public void showData(MeiziData meiziData) {
        isFirst = false;
        mSwipeRefreshLayout.setRefreshing(false);
        if(isLoadMore){
            adapter.addData(meiziData.results);
        }else{
            adapter.setNewData(meiziData.results);
            adapter.removeAllFooterView();
        }

    }

    @Override
    public void onLoadMoreRequested() {
        mRecycleMeizi.post(new Runnable() {
            @Override
            public void run() {
                if(!isFirst){
                    meiziPresenter.getData(++page);
                    //无更多数据时可以添加footerview   Adapter.addFooterView(notLoadingView);
                    isLoadMore = true;
                }


            }
        });

    }
}
