package com.zd.dk_dbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zd.dk_dbs.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class RvTitleIconAdapater extends RecyclerView.Adapter<RvTitleIconAdapater.ViewHolder>{
    Context context;
    List<String> data;
    LayoutInflater layoutInflater;

    public RvTitleIconAdapater(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        return new ViewHolder(layoutInflater.inflate(R.layout.title_icon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.draweeView.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127900&di=9bb269dd704b9e4ceb3ee6ab6a2d2a3c&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F2fdda3cc7cd98d10efc8b338233fb80e7bec909f.jpg");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView draweeView ;
        public ViewHolder(View itemView) {
            super(itemView);
            draweeView= (SimpleDraweeView) itemView.findViewById(R.id.dbsing_title_icon);
        }
    }
}
