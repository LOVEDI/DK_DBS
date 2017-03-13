package com.zd.dk_dbs.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.LinkPageAdapter;
import com.zd.dk_dbs.fragment.FragmentLink;
import com.zd.dk_dbs.fragment.FragmentLink2;
import com.zd.dk_dbs.fragment.base.BaseFragmet;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class LinkPActivity extends BaseFragmet{
    @BindView(R.id.activity_viewPager)
    ViewPager viewPager;
    LinkPageAdapter linkPageAdapter;
    FragmentManager fm;

    public LinkPActivity(FragmentManager fm) {
        this.fm = fm;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_pager, container, false);
        ButterKnife.bind(this,view);
        bindData();
        return view;
    }



    public ArrayList<Fragment> init(){
        ArrayList<Fragment> list =  new ArrayList<>();
        list.add(new FragmentLink2());
        list.add(new FragmentLink());
        return list;
    }
    public void bindData(){
        linkPageAdapter = new LinkPageAdapter(fm,init());
        viewPager.setAdapter(linkPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
    }
}
