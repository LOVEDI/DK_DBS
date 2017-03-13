package com.zd.dk_dbs.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.zd.dk_dbs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class LinkIngActivity extends FragmentActivity{
    @BindView(R.id.activity_link_fragment)
    FrameLayout fragment;
    FragmentManager fm;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linking);
        ButterKnife.bind(this);
        initView();

    }
    private void initView() {
        fm = getSupportFragmentManager();
        changFragment( new LinkPActivity(getSupportFragmentManager()),false);
    }

    public  void  changFragment(Fragment fragment, boolean isInit){

        //开启事物
        FragmentTransaction ft=fm.beginTransaction();
        //替换Fragemnt
        ft.replace(R.id.activity_link_fragment,fragment);

        //防止出现多个碎片重叠效果
//        if(!isInit) {
//            ft.addToBackStack(null);
//        }
        //提交事务
        ft.commit();
    }
}
