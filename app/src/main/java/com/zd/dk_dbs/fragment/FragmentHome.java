package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.PageAdapter;
import com.zd.dk_dbs.fragment.base.BaseFragmet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FragmentHome extends BaseFragmet{
    @BindView(R.id.home_tabLayout)
    TabLayout tabLayout ;

    @BindView(R.id.home_viewPager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
        initData();
        initListener();
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInsta) {
        return null;
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        final PageAdapter adapter = new PageAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
    }
    private void initFragment() {
        tabLayout.addTab(tabLayout.newTab().setText("精选"));
        tabLayout.addTab(tabLayout.newTab().setText("热门"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
