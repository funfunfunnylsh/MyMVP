package cn.uudu.com.mymvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.ui.fragment.GirlFragment;
import cn.uudu.com.mymvp.ui.fragment.MeiZhiFragment;
import cn.uudu.com.mymvp.ui.fragment.ZhiHuFragment;
import cn.uudu.com.mymvp.utils.SnackBarUtil;

public class MainActivity extends BaseActivity {

    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //将ToolBar与TabLayout结合放入AppBarLayout
    private Toolbar mToolbar;
    private boolean isBackPressed;


    MenuItem currentMenuItem;
    Fragment currentFragment;
    SimpleArrayMap<Integer, String> mTitleArryMap = new SimpleArrayMap<>();
    private MeiZhiFragment meizifragment;
    public GirlFragment girlfragment;
    private ZhiHuFragment zhihufragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //初始化控件及布局
        initView();
    }

    private void initView() {
        //MainActivity的布局文件中的主要控件初始化
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);

        //初始化ToolBar
        mToolbar.setTitle("MVP");
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //对NavigationView添加item的监听事件
        mNavigationView.setNavigationItemSelectedListener(naviListener);

        meizifragment = new MeiZhiFragment();

        switchFragment(meizifragment);
        mToolbar.setTitle("meizhi");

        mTitleArryMap.put(0, "meizhi");
        mTitleArryMap.put(1, "zhihu");
        mTitleArryMap.put(2, "girls");

        if (currentMenuItem==null){
            currentMenuItem = mNavigationView.getMenu().findItem(R.id.menu_info_details);
            currentMenuItem.setChecked(true);
        }

    }

    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {

        public Fragment fragment;

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.menu_info_details:
                    fragment = meizifragment;
                    mToolbar.setTitle("meizhi");
                    break;
                case R.id.menu_share:
                    if(null == zhihufragment){
                        zhihufragment= new ZhiHuFragment();
                    }
                    fragment = zhihufragment;
                    mToolbar.setTitle("zhihu");
                    break;
                case R.id.menu_agenda:
                    if(null == girlfragment){
                        girlfragment = new GirlFragment();
                    }
                    fragment = girlfragment;
                    mToolbar.setTitle("girls");
                    break;
            }

            if (currentMenuItem != menuItem && currentMenuItem != null) {

                switchFragment(fragment);
                currentMenuItem.setChecked(false);
                menuItem.setChecked(true);
                currentMenuItem = menuItem;
            }

            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };

    private void switchFragment(Fragment fragment) {

        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName()))
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        currentFragment = fragment;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isBackPressed) {
                super.onBackPressed();
                return;
            }

            isBackPressed = true;

            SnackBarUtil.show(mDrawerLayout, R.string.back_pressed_tip);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressed = false;
                }
            }, 2000);
        }
    }
}
