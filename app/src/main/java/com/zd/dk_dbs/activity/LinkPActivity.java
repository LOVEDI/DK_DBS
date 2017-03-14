package com.zd.dk_dbs.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

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
        list.add(FragmentLink.getInstance());
        return list;
    }
    public void bindData(){
        linkPageAdapter = new LinkPageAdapter(fm,init());
        viewPager.setAdapter(linkPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(1);
        //点击隐藏聊天的工具
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG","点击了主播1");
                //隐藏软键盘
                hidekey(viewPager);
                //发送消息
              /*  Message msg = new Message();
                msg.what=1;
                FragmentLink fragmentLink = new FragmentLink();
                fragmentLink.handler.sendMessage(msg);*/
                FragmentLink fragmentLink = FragmentLink.getInstance();
                fragmentLink.hindeLayout(new FragmentLink.CallBack() {
                    @Override
                    public void hide(TableLayout tableLayout, RelativeLayout bottom) {
                        tableLayout.setVisibility(View.GONE);
                        bottom.setVisibility(View.GONE);
                    }
                });

                return false;
            }
        });
    }
    //安卓隐藏软键盘
    private void hidekey(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
