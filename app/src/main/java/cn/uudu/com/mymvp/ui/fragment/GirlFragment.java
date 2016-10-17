package cn.uudu.com.mymvp.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.GirlItemData;
import cn.uudu.com.mymvp.mvp.girls.GirlsContract;
import cn.uudu.com.mymvp.mvp.girls.GirlsPresenter;
import cn.uudu.com.mymvp.ui.adapter.GirlsItemAdapter;
import cn.uudu.com.mymvp.utils.ToastUtils;

/**
 * Created by Administrator on 2016/10/14.
 */
public class GirlFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,GirlsContract.IGirlView,BaseQuickAdapter.RequestLoadMoreListener{

    @InjectView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;

    @InjectView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private GirlsPresenter girlsPresenter;
    private String mSubtype = "4";
    private int PAGE_COUNT = 1;
    private GirlsItemAdapter adapter;
    private boolean isLoadMore;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
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

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//可防止Item切换
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new GirlsItemAdapter(R.layout.item_girl_layout, new ArrayList<GirlItemData>());
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setLoadingView(LayoutInflater.from(getActivity()).inflate(R.layout.load_loading_layout, (ViewGroup) mRecyclerView.getParent(), false));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtils.showToast(getActivity(), Integer.toString(position));

            }
        });



    }

    @Override
    protected void initData() {
        girlsPresenter = new GirlsPresenter(this);
        girlsPresenter.getData(mSubtype,PAGE_COUNT);

    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        girlsPresenter.getData(mSubtype,1);
    }

    @Override
    public void showErrorMsg(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<GirlItemData> data) {
        if(isLoadMore){
            adapter.addData(data);
        }else{
            mSwipeRefreshLayout.setRefreshing(false);
            adapter.setNewData(data);
            adapter.removeAllFooterView();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                girlsPresenter.getData(mSubtype,++PAGE_COUNT);
                //无更多数据时可以添加footerview   Adapter.addFooterView(notLoadingView);
                isLoadMore = true;
            }
        });
    }
}
