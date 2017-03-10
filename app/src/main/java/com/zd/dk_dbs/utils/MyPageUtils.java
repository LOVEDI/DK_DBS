package com.zd.dk_dbs.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zd.dk_dbs.Entity.LiveIng;
import com.zd.dk_dbs.httprequest.Contants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class MyPageUtils {

    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新
    private static final int STATE_MORE = 2;//加载更多
    private int state =STATE_NORMAL;//默认状态是正常状态
    private PageUtils.OnPageListener pageListener;
    private int page = 1;
    private MaterialRefreshLayout refreshLayout;
    private boolean canLoadMore;
    private Context context;
    public MyPageUtils(Context context) {
        this.context = context;
    }
    public void initRefreshLayout(){
        this.refreshLayout.setLoadMore(this.canLoadMore);//开始加载
        this.refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Log.e("TAG","下拉刷新");
                refreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Log.e("TAG","上拉加载");
                if(page < 3) {
                    loadMoreData();
                }else {
                    Toast.makeText(context, "已经没有跟多数据了", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    public interface OnPageListener<T>{
        void load(List<T> datas, int totalPage, int totalCount);
        void refresh(List<T> datas, int totalPage, int totalCount);
        void loadMore(List<T> datas, int totalPage, int totalCount);
    }
    private int refreshDate(){
       page=1;
        state=STATE_REFRESH;
        requestData();
        return page;
    }

    private int loadMoreData(){
        page += 1;
        state = STATE_MORE;
        requestData();
        return  page;
    }
    private void requestData() {
        OkHttpUtils
                .post()
                .url(Contants.API.BASE_URL)
                .addParams("type","1")
                .addParams("page", page+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "onError");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Type type = new TypeToken<List<LiveIng.ResultBean.ListBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        LiveIng a = gson.fromJson(response.toString(), LiveIng.class);
                        List<LiveIng.ResultBean.ListBean> list = a.result.list;


                    }
                });

    }

    public int getPage(){
        return page;
    }
}
