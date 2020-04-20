package cn.beingyi.sample.application;

import android.app.Application;
import android.content.Context;

import cn.beingyi.androidcore.AndroidCore;

public class MyApp extends Application {

    static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context=base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

        AndroidCore.init(context);

    }


    public static Context getContext(){
        return context;
    }

}
