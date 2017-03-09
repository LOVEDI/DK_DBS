package com.zd.dk_dbs.adapter.base;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder>{
    public SimpleAdapter(Context context, int layoutSourceId, List<T> datas) {
        super(context, layoutSourceId, datas);
    }
}
