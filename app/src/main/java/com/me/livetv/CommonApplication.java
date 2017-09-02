package com.me.livetv;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by hp on 2016/5/26.
 */
public class CommonApplication extends Application {

    public static CommonApplication context;
    private static CommonApplication instance;
    public static String kefuId;

    // login user name
    public static String hxUsername = "";
    // 当前用户nickname,为了苹果推送不是userid而是昵称
    public static String currentUserNick = "";
    private static Typeface typeface;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;

        //加载字体
        try {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/DINPro-Regular.ttf");
        }catch (Exception exception){
            typeface = null;
        }

       // Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());



    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    public static Context getContext() {
        return context;
    }

    public static CommonApplication getInstance() {
        return instance;
    }

    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}

