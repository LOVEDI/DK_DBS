package com.zd.dk_dbs.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        //基本使用的初始化方法
        Fresco.initialize(this);
    }
}
