package com.zd.dk_dbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zd.dk_dbs.Entity.Sift;
import com.zd.dk_dbs.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class RecycleViewAdapater extends RecyclerView.Adapter<RecycleViewAdapater.ViewHolder>{
    Context context;
    List<Sift> data;
    LayoutInflater layoutInflater;

    public RecycleViewAdapater(Context context, List<Sift> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        return new ViewHolder(layoutInflater.inflate(R.layout.dbs_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      /*  holder.iv_title.setImageResource(data.get(position).getHead_img());
        holder.iv_cover.setImageResource(data.get(position).getCover());*/
        holder.dbs_name.setText(data.get(position).getName());

       // if(data.get(position).getState()==1) {
            holder.dbs_state.setText("正在直播");
      //  }else{
            holder.dbs_state.setText("等待中");
      //  }
        holder.dbs_position.setText(data.get(position).getPosition());
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
        TextView dbs_position;
     //   @BindView(R.id.dbs_state)
        TextView dbs_state;
        public ViewHolder(View itemView) {
            super(itemView);
       //     ButterKnife.bind(context,itemView);
            iv_title = (ImageView) itemView.findViewById(R.id.dbs_title);
            iv_cover = (ImageView) itemView.findViewById(R.id.dbs_cover);
            dbs_name = (TextView) itemView.findViewById(R.id.dbs_name);
            dbs_state = (TextView) itemView.findViewById(R.id.dbs_state);
            dbs_position = (TextView) itemView.findViewById(R.id.dbs_position);

        }
    }
}
