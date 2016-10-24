package cn.uudu.com.mymvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import cn.uudu.com.mymvp.MyApplication;
import cn.uudu.com.mymvp.R;
import cn.uudu.com.mymvp.api.ZhiHuApi;
import cn.uudu.com.mymvp.bean.ZhiHu;
import cn.uudu.com.mymvp.bean.ZhiHuStory;
import cn.uudu.com.mymvp.http.RetrofitUtils;
import cn.uudu.com.mymvp.http.Urls;
import cn.uudu.com.mymvp.utils.ImageLoader;
import cn.uudu.com.mymvp.utils.WebUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ZhiHuDetailActivity extends BaseActivity{
    private WebView mWebView;
    private ZhiHu entity;
    private ImageView back_drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihudetail);

        Toolbar mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });

        //使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示
        //扩张时候的title颜色：mCollapsingToolbarLayout.setExpandedTitleColor();
        //收缩后在Toolbar上显示时的title的颜色：mCollapsingToolbarLayout.setCollapsedTitleTextColor();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Matthew");
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色


        back_drop = (ImageView) findViewById(R.id.back_drop);
        //设置过渡动画
        ViewCompat.setTransitionName(back_drop, "transitionimg");

        entity = (ZhiHu) getIntent().getSerializableExtra("entity");


        collapsingToolbar.setTitle(entity.title);

        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        //settings.setUseWideViewPort(true);造成文字太小
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
        settings.setDatabaseEnabled(true);
        // 开启Application Cache功能
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());


        getdata(entity.id+"");

    }

    private void getdata(String id) {
        RetrofitUtils.newInstence(Urls.ZHIHU_URL)
                .create(ZhiHuApi.class)
                .getZhihuStory(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ZhiHuStory>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhiHuStory zhiHuStory) {
                        showData(zhiHuStory);

                    }
                });
    }

    private void showData(ZhiHuStory zhiHuStory) {
        ImageLoader.load(MyApplication.getContext(), zhiHuStory.image, back_drop);
        String url = zhiHuStory.mShareUrl;
        boolean isEmpty = TextUtils.isEmpty(zhiHuStory.body);
        String mBody = zhiHuStory.body;
        String[] scc = zhiHuStory.css;
        if (isEmpty) {
            mWebView.loadUrl(url);
        } else {
            String data = WebUtil.buildHtmlWithCss(mBody, scc, false);
            mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
        }
    }


    @Override
    protected void onDestroy() {
        //webview内存泄露
        if (mWebView != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();

    }
}
