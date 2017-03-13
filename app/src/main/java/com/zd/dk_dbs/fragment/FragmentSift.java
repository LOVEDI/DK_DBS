package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.zd.dk_dbs.activity.LinkIngActivity;
import com.zd.dk_dbs.adapter.RVAdapter;
import com.zd.dk_dbs.adapter.base.BaseAdapter;
import com.zd.dk_dbs.httprequest.Contants;
import com.zd.dk_dbs.utils.MyUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FragmentSift extends Fragment {

    @BindView(R.id.sift_rv)
    RecyclerView mRecyclerVeiw;
    @BindView(R.id.sift_mrl)
    MaterialRefreshLayout mRefreshLayout;
    RVAdapter rvAdapter;
    int page =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sift,container,false);
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
                //上拉加载更多...
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void getData(int page){
        OkHttpUtils
                .post()
                .url(Contants.API.BASE_URL)
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
                     /*   Type type = new TypeToken<List<LiveIng.ResultBean.ListBean>>() {
                        }.getType();*/
                        Gson gson = new Gson();
                        LiveIng a = gson.fromJson(response.toString(), LiveIng.class);
                        List<LiveIng.ResultBean.ListBean> list = a.result.list;
                        rvAdapter = new RVAdapter(getContext(),0, list);
                        mRecyclerVeiw.setAdapter(rvAdapter);
                        mRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
                        mRecyclerVeiw.setItemAnimator(new DefaultItemAnimator());
                        rvAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                MyUtils.intentShow(getContext(), LinkIngActivity.class);
                            }
                        });
                    }
                });
    }
    private void refreshDate(){
        Log.e("TAG","加载了么？");
        page=1;
        getData(page);
    }
    private void loadMoreData(){
        page += 1;
        getData(page);
    }
}
