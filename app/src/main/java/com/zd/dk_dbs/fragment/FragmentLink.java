package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.fragment.base.BaseFragmet;
import com.zd.dk_dbs.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class FragmentLink extends BaseFragmet{
   /* @BindView(R.id.heart_layout)
    HeartLayout heartLayout;*/
    @BindView(R.id.dbsing_rv)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linking, container, false);
        ButterKnife.bind(this,view);
        initRecycler();
        return view;
    }

    private void initRecycler() {

    }

    @OnClick({R.id.link_love})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.link_love:
             //   heartLayout.addFavor();
                ToastUtils.show(getContext(),"点个赞");
                break;
        }
    }
}
