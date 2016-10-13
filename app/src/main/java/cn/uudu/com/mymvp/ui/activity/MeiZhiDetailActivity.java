package cn.uudu.com.mymvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.bean.Meizi;
import cn.uudu.com.mymvp.ui.fragment.BaseFragment;
import cn.uudu.com.mymvp.ui.fragment.MeiZhiDetailFragment;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MeiZhiDetailActivity extends BaseActivity {

    private String title ;
    private ArrayList<Meizi> mDatas;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_detail);

        mDatas = (ArrayList<Meizi>) getIntent().getSerializableExtra("meizi_data");
        position = getIntent().getIntExtra("position", -1);

        //初始化控件及布局
        initToolbar();
        initView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.girl_detail_toolbar);
        //setTitle方法要在setSupportActionBar(toolbar)之前调用，否则不起作用
        title = mDatas.get(position).createdAt;
        title = title.length() > 20 ? title.substring(0, 20) + "..." : title;
        mToolbar.setTitle(title);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.girl_detail_viewpager);

        List<BaseFragment> fragments = new ArrayList<>();
        for (Meizi meizi : mDatas) {
            fragments.add(MeiZhiDetailFragment.newInstance(meizi.url));
        }
        MeizhiDetailAdapter adapter = new MeizhiDetailAdapter(getSupportFragmentManager());
        adapter.setData(fragments);
        mViewPager.setOffscreenPageLimit(mDatas.size());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title = mDatas.get(position).createdAt;
                title = title.length() > 20 ? title.substring(0, 20) + "..." : title;
                mToolbar.setTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * shipeiqi
     */
    public class MeizhiDetailAdapter extends FragmentPagerAdapter {
        public MeizhiDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        private List<BaseFragment> fragments;

        public void setData(List<BaseFragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
