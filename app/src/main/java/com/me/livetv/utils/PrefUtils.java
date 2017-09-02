package com.me.livetv.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/11/16.
 * SharedPreferences 工具类
 */
public class PrefUtils {

    public static final String CHAT_CONFIG = "chat_config";

    public static final String CHAT_CONFIG_TYPE_FORBID = "forbid_";
    public static final String CHAT_CONFIG_TYPE_MYQUANZI_AVATAR = "myQuanziAvatar_";
    public static final String CHAT_CONFIG_TYPE_MYQUANZI_NAME = "myQuanziName_";
    public static final String CHAT_CONFIG_TYPE_MYQUANZI_QZTYPE = "myQuanziQztype_";
    public static final String CHAT_CONFIG_TYPE_MSG_NO_REMINDING = "msgNoReminding_"; //消息免打扰
    public static final String CHAT_CONFIG_TYPE_MSG_TOPS = "msgTops"; //消息置顶集合

    public static void putBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putInt(Context ctx, String key, int value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void putLong(Context ctx, String key, long value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }

    public static long getLong(Context ctx, String key, long defValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }


    /**
     * 环信配置
     */
    public static void putBooleanOfChatConfig(Context ctx, String key, boolean value, String configType) {
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_CONFIG,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(configType + key, value).commit();
    }

    public static boolean getBooleanOfChatConfig(Context ctx, String key, boolean defValue, String configType) {
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_CONFIG,
                Context.MODE_PRIVATE);
        return sp.getBoolean(configType + key, defValue);
    }

    public static void putStringOfChatConfig(Context ctx, String key, String value, String configType) {
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_CONFIG,
                Context.MODE_PRIVATE);
        sp.edit().putString(configType + key, value).commit();
    }

    public static String getStringOfChatConfig(Context ctx, String key, String defValue, String configType) {
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_CONFIG,
                Context.MODE_PRIVATE);
        return sp.getString(configType + key, defValue);
    }

    public static void removeChatConfig(Context ctx, String key, String configType) {
        SharedPreferences sp = ctx.getSharedPreferences(CHAT_CONFIG,
                Context.MODE_PRIVATE);
        sp.edit().remove(configType + key).commit();
    }


}


