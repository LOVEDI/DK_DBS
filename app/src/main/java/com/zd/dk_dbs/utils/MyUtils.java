package com.zd.dk_dbs.utils;

import android.content.Context;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class MyUtils {
    public static void intentShow(Context context,Class c){
        Intent intent = new Intent(context,c);
        context.startActivity(intent);
    }
    //检验手机号的正则表达式
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
}
