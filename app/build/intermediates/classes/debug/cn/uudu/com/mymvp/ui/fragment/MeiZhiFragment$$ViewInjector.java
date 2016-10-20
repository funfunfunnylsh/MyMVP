// Generated code from Butter Knife. Do not modify!
package cn.uudu.com.mymvp.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MeiZhiFragment$$ViewInjector {
  public static void inject(Finder finder, final cn.uudu.com.mymvp.ui.fragment.MeiZhiFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493008, "field 'mRecycleMeizi'");
    target.mRecycleMeizi = (android.support.v7.widget.RecyclerView) view;
    view = finder.findRequiredView(source, 2131493000, "field 'mSwipeRefreshLayout'");
    target.mSwipeRefreshLayout = (android.support.v4.widget.SwipeRefreshLayout) view;
    view = finder.findRequiredView(source, 2131493009, "field 'pbProgress'");
    target.pbProgress = (android.widget.ProgressBar) view;
  }

  public static void reset(cn.uudu.com.mymvp.ui.fragment.MeiZhiFragment target) {
    target.mRecycleMeizi = null;
    target.mSwipeRefreshLayout = null;
    target.pbProgress = null;
  }
}
