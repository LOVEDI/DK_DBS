package com.zd.dk_dbs.adapter;

import android.content.Context;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zd.dk_dbs.Entity.LiveIng;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.base.BaseViewHolder;
import com.zd.dk_dbs.adapter.base.SimpleAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class RVAdapter extends SimpleAdapter<LiveIng.ResultBean.ListBean>{
    public RVAdapter(Context context, int layoutSourceId, List<LiveIng.ResultBean.ListBean> datas) {
        super(context, R.layout.dbs_item, datas);
    }
    @Override
    protected void convert(BaseViewHolder holder, LiveIng.ResultBean.ListBean item) {
        SimpleDraweeView draweeView = (SimpleDraweeView) holder.getView(R.id.dbs_title);
        draweeView.setImageURI(item.user.user_data.avatar);
        SimpleDraweeView draweeView2 = (SimpleDraweeView) holder.getView(R.id.dbs_cover);
        draweeView2.setImageURI(item.data.pic);
        Log.e("TAG","得到的URL==="+item.data.pic);
        holder.getTextView(R.id.dbs_name).setText(item.data.live_name);
        Log.e("TAG","信息是："+item.user.user_data.user_name);
        holder.getTextView(R.id.dbs_position).setText("北京");
        int state = item.data.live_type;
        if(state==0) {
            holder.getTextView(R.id.dbs_state).setText("直播中");
        }else{
            holder.getTextView(R.id.dbs_state).setText("已直播完");
        }

    }
}
