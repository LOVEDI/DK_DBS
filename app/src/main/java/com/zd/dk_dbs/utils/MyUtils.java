package com.zd.dk_dbs.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class MyUtils {
    public static void showToast(Context context, String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    public static void intentShow(Context context,Class c){
        Intent intent = new Intent(context,c);
        context.startActivity(intent);
    }
}
