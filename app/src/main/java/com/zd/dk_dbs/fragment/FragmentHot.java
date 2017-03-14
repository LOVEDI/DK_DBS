package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.zd.dk_dbs.Entity.LiveIng;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.RVAdapter;
import com.zd.dk_dbs.fragment.base.BaseFragmet;
import com.zd.dk_dbs.httprequest.Contants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FragmentHot extends BaseFragmet {
    @BindView(R.id.hot_rv)
    RecyclerView mRecyclerVeiw;
    @BindView(R.id.hot_mrl)
    MaterialRefreshLayout mRefreshLayout;

    RVAdapter rvAdapter;
    int page =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_hot,container,false);
        ButterKnife.bind(this,view);
        initListener();
        getData(page);
        return view;
    }

    private void initListener() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Log.e("TAG","上拉刷新");
                refreshDate();
                mRefreshLayout.finishRefresh();
            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Log.e("TAG","下拉加载");
                if(page == 0) {
                    loadMoreData();
                }else {
                    Toast.makeText(getContext(), "已经没有跟多数据了", Toast.LENGTH_SHORT).show();
                }
                mRefreshLayout.finishRefreshLoadMore();
            }
        });
    }
    private void refreshDate(){
        page=1;
        getData(page);
    }
    private void loadMoreData(){
        page += 1;
        getData(page);
    }
    public void getData(int page){
        OkHttpUtils
                .post()
                .url(Contants.API.MINE_URL)
                .addParams("type","1")
                .addParams("page", page+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "onError");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","加载一次");
                        Gson gson = new Gson();
                        LiveIng a = gson.fromJson(response, LiveIng.class);
                        List<LiveIng.ResultBean.ListBean> list = a.result.list;
                        rvAdapter = new RVAdapter(getContext(),0, list);
                        mRecyclerVeiw.setAdapter(rvAdapter);
                        mRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
                        mRecyclerVeiw.setItemAnimator(new DefaultItemAnimator());
                    }
                });
    }

}
