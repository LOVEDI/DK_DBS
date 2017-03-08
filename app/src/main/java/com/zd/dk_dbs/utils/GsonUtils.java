package com.zd.dk_dbs.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class GsonUtils {
    //用builder可以添加更多的设置
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    public static Gson getGson(){
        return gson;
    }

    public static <T> T fromJson(String json,Class<T> clz){
        return gson.fromJson(json,clz);
    }
    public static <T> T fromJson(String json,Type type){
        return gson.fromJson(json,type);
    }
    public static String toJson(Object o){
        return gson.toJson(o);
    }
}
