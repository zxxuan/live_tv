package com.me.livetv;


import com.me.livetv.domain.LiveList;
import com.me.livetv.domain.LoginResponseData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangxuan on 2016/3/25.
 */
public class ApiManager {


    /**
     * 登录
     */
    public interface LoginApi {

        @Headers({
                "Content-Type: application/x-www-form-urlencoded"
        })
        @FormUrlEncoded
        @POST("oauth/token")
        Observable<LoginResponseData> getLoginResponseData(@Header("Authorization") String authorization, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grant_type);
    }

    public static LoginApi getLoginApi() {
        return RetrofitManager.newInstance().getRetrofit(BuildConfig.LOCALHOST).create(LoginApi.class);
    }

    /**
     * course/tclive/list
     */
    public interface LiveListApi {

        @GET("course/tclive/list")
        Observable<LiveList> getLoginResponseData();
    }

    public static LiveListApi getLiveListApi() {
        return RetrofitManager.newInstance().getRetrofit(BuildConfig.LOCALHOST).create(LiveListApi.class);
    }

}


