package com.me.livetv;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;


/**
 * Created by zhangxuan on 2016/3/25.
 */
public class RetrofitManager {
    private static RetrofitManager instance;
    private RetrofitManager(){}
    public static RetrofitManager newInstance(){
        if (instance==null){
            synchronized (RetrofitManager.class){
                if (instance==null){
                    instance=new RetrofitManager();
                }
            }
        }
        return instance;
    }


    public Retrofit getRetrofit(String url){
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(build)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }



}
