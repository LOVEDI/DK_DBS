package com.zd.dk_dbs.adapter;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zd.dk_dbs.Entity.Sift;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.base.BaseViewHolder;
import com.zd.dk_dbs.adapter.base.SimpleAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class RVAdapter extends SimpleAdapter<Sift>{
    public RVAdapter(Context context, int layoutSourceId, List<Sift> datas) {
        super(context, R.layout.dbs_item, datas);
    }
    @Override
    protected void convert(BaseViewHolder holder, Sift item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.dbs_title);
        draweeView.setImageURI(item.getHeadIcon());
        SimpleDraweeView draweeView2 = (SimpleDraweeView) holder.getView(R.id.dbs_cover);
        draweeView2.setImageURI(item.getCover());
        Log.e("TAG","得到的URL==="+item.getHeadIcon());
        holder.getTextView(R.id.dbs_name).setText(item.getName());
        Log.e("TAG","信息是："+item.getName()+item.getPosition()+item.getState());
        holder.getTextView(R.id.dbs_position).setText(item.getPosition());
        holder.getTextView(R.id.dbs_state).setText(item.getState());
    }
}
