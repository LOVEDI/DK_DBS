package com.zd.dk_dbs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class LinkPageAdapter extends FragmentPagerAdapter {
    //存放碎片的集合
    private ArrayList<Fragment> list;

    public LinkPageAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int arg0) {
        Log.e("TAG","进来getItem没？meiyou");
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
