// Generated code from Butter Knife. Do not modify!
package cn.uudu.com.mymvp.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ZhiHuFragment$$ViewInjector {
  public static void inject(Finder finder, final cn.uudu.com.mymvp.ui.fragment.ZhiHuFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493008, "field 'mRecycleNews'");
    target.mRecycleNews = (android.support.v7.widget.RecyclerView) view;
    view = finder.findRequiredView(source, 2131493000, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view;
  }

  public static void reset(cn.uudu.com.mymvp.ui.fragment.ZhiHuFragment target) {
    target.mRecycleNews = null;
    target.mSwipeRefreshLayout = null;
  }
}
