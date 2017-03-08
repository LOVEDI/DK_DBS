package com.zd.dk_dbs.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.fragment.FragmentHome;
import com.zd.dk_dbs.fragment.FragmentMy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.mian_fl)
    FrameLayout fl;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        fm = getSupportFragmentManager();
        changFragment( new FragmentHome(),false);
    }

    public  void  changFragment(Fragment fragment, boolean isInit){

        //开启事物
        FragmentTransaction ft=fm.beginTransaction();
        //替换Fragemnt
        ft.replace(R.id.mian_fl,fragment);

        //防止出现多个碎片重叠效果
        if(!isInit) {
            ft.addToBackStack(null);
        }
        //提交事务
        ft.commit();
    }
    @OnClick({R.id.mine_bot_left,R.id.mine_bot_right})
    public void setEven(View view){
        switch (view.getId()){
            case R.id.mine_bot_left:
                changFragment( new FragmentHome(),false);
                break;
            case R.id.mine_bot_right:
                changFragment( new FragmentMy(),false);
                break;
        }

    }
    private   long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }



}
