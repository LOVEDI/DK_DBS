package com.zd.dk_dbs.activity.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class BaseActivity extends AppCompatActivity {
    public void intentShow(Context context, Class c){
        Intent intent = new Intent(context,c);
        context.startActivity(intent);
    }
}
