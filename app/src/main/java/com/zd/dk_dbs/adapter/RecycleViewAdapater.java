package com.zd.dk_dbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zd.dk_dbs.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class RecycleViewAdapater extends RecyclerView.Adapter<RecycleViewAdapater.ViewHolder>{
    Context context;
    List<String> data;
    LayoutInflater layoutInflater;

    public RecycleViewAdapater(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        return new ViewHolder(layoutInflater.inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.msg_text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
   //   @BindView(R.id.dbs_civ)
        ImageView iv_title;
    //  @BindView(R.id.dbs_cover)
        ImageView iv_cover;
   //     @BindView(R.id.dbs_name)
        TextView dbs_name;
    //    @BindView(R.id.dbs_position)
        TextView msg_text;
     //   @BindView(R.id.dbs_state)
        TextView dbs_state;
        public ViewHolder(View itemView) {
            super(itemView);
            msg_text = (TextView) itemView.findViewById(R.id.msg_text);
        }
    }
}
