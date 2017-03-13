package com.zd.dk_dbs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.fragment.base.BaseFragmet;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class FragmentLink2 extends BaseFragmet{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_linking2, container, false);
        return view;
    }

}
