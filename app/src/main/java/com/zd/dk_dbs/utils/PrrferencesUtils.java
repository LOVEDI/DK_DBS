package com.zd.dk_dbs.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class PrrferencesUtils {
    private static final String PREFERENCE_NAME = "shop_common";
    public static boolean putString(Context context,String key,String value){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        return editor.commit();
    }
    public static String getString(Context context,String key){
        return getString(context,key,null);
    }
    public static String getString(Context context,String key,String defaultValue){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
        return settings.getString(key,defaultValue);
    }
}
