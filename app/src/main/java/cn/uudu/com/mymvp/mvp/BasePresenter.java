package cn.uudu.com.mymvp.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        //使用了CompositeSubscription来进行避免内存的泄漏。
        this.mCompositeSubscription.add(s);
    }

    public void unsubcrible() {

        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

}
