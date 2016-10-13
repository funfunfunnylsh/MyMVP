package cn.uudu.com.mymvp;

import android.app.Application;


public class MyApplication extends Application {

    public static MyApplication myApplication;

    public static Application getContext() {

        return myApplication;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;

    }


}
