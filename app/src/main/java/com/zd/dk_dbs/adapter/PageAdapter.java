package com.zd.dk_dbs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zd.dk_dbs.fragment.FragmentHot;
import com.zd.dk_dbs.fragment.FragmentSift;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class PageAdapter extends FragmentStatePagerAdapter {
    int nNumOfTabs;
    public PageAdapter(FragmentManager fm,int nNumOfTabs)
    {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                FragmentSift tab1=new FragmentSift();
                return tab1;
            case 1:
                FragmentHot tab2=new FragmentHot();
                return tab2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }
}
