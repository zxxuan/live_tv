package com.me.livetv.utils;

import android.content.Context;

/**
 * Created by zhangxuan on 2016/3/28.
 */
public class ApiUtils {
    public static String getAuthorization(Context context){
        return "Bearer "+ PrefUtils.getString(context,"access_token","");
    }

}
