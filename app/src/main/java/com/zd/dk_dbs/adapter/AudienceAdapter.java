package com.zd.dk_dbs.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zd.dk_dbs.Entity.LiveIng;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.base.BaseViewHolder;
import com.zd.dk_dbs.adapter.base.SimpleAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class AudienceAdapter extends SimpleAdapter<LiveIng.ResultBean.ListBean>{
    public AudienceAdapter(Context context, int layoutSourceId, List<LiveIng.ResultBean.ListBean> datas) {
        super(context, R.layout.audience_item, datas);
    }
    @Override
    protected void convert(BaseViewHolder holder, LiveIng.ResultBean.ListBean item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.audience_icon);
    }
}
