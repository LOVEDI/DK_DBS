package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;
import com.zd.dk_dbs.Entity.PageResult;
import com.zd.dk_dbs.Entity.Sift;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.RVAdapter;
import com.zd.dk_dbs.adapter.RecycleViewAdapater;
import com.zd.dk_dbs.httprequest.Contants;
import com.zd.dk_dbs.utils.PageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class FragmentSift extends Fragment implements PageUtils.OnPageListener{
    @BindView(R.id.sift_rv)
    RecyclerView mRecyclerVeiw;
    @BindView(R.id.sift_mrl)
    MaterialRefreshLayout mRefreshLayout;

    RecycleViewAdapater recycleViewAdapater;
    RVAdapter rvAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sift,container,false);
        ButterKnife.bind(this,view);
        PageUtils pageUtils =  PageUtils.newBuilder()
                .setUrl(Contants.API.BASE_URL)
                .setLoadMore(true)
                .setOnpageListener(this)
                .setPageSize(20)
                .setRefreshLayout(mRefreshLayout)
                .build(getContext(),new TypeToken<PageResult<Sift>>(){}.getType());
        pageUtils.request();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void load(List datas, int totalPage, int totalCount) {
      //  recycleViewAdapater = new RecycleViewAdapater(getContext(), DataUtils.getData());

        rvAdapter = new RVAdapter(getContext(),0, (List<Sift>) datas);
        mRecyclerVeiw.setAdapter(rvAdapter);
        mRecyclerVeiw.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerVeiw.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void refresh(List datas, int totalPage, int totalCount) {
    rvAdapter.clearData();
        rvAdapter.addData(datas);
        mRecyclerVeiw.scrollToPosition(0);

    }

    @Override
    public void loadMore(List datas, int totalPage, int totalCount) {
        rvAdapter.addData(rvAdapter.getItemCount(),datas);
        mRecyclerVeiw.scrollToPosition(rvAdapter.getItemCount());
    }
}
