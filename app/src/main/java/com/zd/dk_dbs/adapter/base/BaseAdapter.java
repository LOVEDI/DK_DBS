package com.zd.dk_dbs.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public  abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder>{
    protected final Context mContext;
    protected int mLayoutSourceId;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public BaseAdapter(Context context, int layoutSourceId, List<T> datas){
        this.mContext = context;
        this.mLayoutSourceId = layoutSourceId;
        this.mDatas = datas == null ? new ArrayList<T>() : datas;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutSourceId,
                parent,false);
        return new BaseViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        convert((H)holder,item);
    }

    protected  abstract  void  convert(H holder, T item);

    public T getItem(int position) {
        return position >= mDatas.size() ? null: mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    public void addData(List<T> datas){
        addData(0,datas);
    }
    public void addData(int position,List<T> datas){
        if(datas != null && datas.size()>0) {
            mDatas.addAll(datas);
            notifyItemRangeRemoved(position,mDatas.size());
        }
    }
    public void clearData(){
        mDatas.clear();
        notifyItemRangeRemoved(0, mDatas.size());
    }
    public void refreshDate(List<T> list){
        if(list != null &&list.size()>0) {
            clearData();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                mDatas.add(i,list.get(i));
                notifyItemInserted(i);//通知更新
            }
        }

    }
    public void loadMoreDate(List<T> datas) {
        if(datas != null && datas.size()>0) {
            int size = datas.size();
            int begin = mDatas.size();
            for(int i = 0; i < datas.size(); i++) {
             mDatas.add(datas.get(i));
                notifyItemInserted(i+begin);
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
