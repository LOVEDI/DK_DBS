package com.zd.dk_dbs.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zd.dk_dbs.Entity.PageResult;
import com.zd.dk_dbs.httprequest.OkHttpHelper;
import com.zd.dk_dbs.httprequest.SpotsCallBack;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/28 0028.
 */

public class PageUtils {
    private static final int STATE_NORMAL = 0;//正常状态
    private static final int STATE_REFRESH = 1;//刷新
    private static final int STATE_MORE = 2;//加载更多
    private int state =STATE_NORMAL;//默认状态是正常状态
    private OkHttpHelper okHttpHelper;
    private static Builder builder;
    private  OnPageListener pageListener;

    private Context context;
    private String url;
    private MaterialRefreshLayout refreshLayout;
    private boolean canLoadMore;
    private int tatalPage = 1;
    private int pageIndex = 1;//页码
    private int pageSize = 2;
    private HashMap<String, Object> params = new HashMap<>(5);
    //网络请求你
    public void request(){
        requestData();
    }

    private PageUtils(){
        okHttpHelper = OkHttpHelper.getInstance();
    }
    public static Builder newBuilder(){
        builder = new Builder();
        return builder;
    }
    public void putParam(String key,Object value){
        this.putParam(key,value);
    }
    private void initRefreshLayout(){
        this.refreshLayout.setLoadMore(this.canLoadMore);//开始加载
        this.refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshDate();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Log.e("TAG","pageIndex===="+pageIndex);
                if(pageIndex < tatalPage) {
                    loadMoreData();
                }else {
                    Toast.makeText(context, "已经没有跟多数据了", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }
    private void refreshDate(){
        this.pageIndex=1;
        state=STATE_REFRESH;
        requestData();
    }
    private void loadMoreData(){
        this.pageIndex += 1;
        state = STATE_MORE;
        requestData();
    }
    private void requestData(){
       /* okHttpHelper.get(buildUrl(),
                new RequestCallBack(this.context));*/
        okHttpHelper.post(buildUrl(), params,
                new RequestCallBack(this.context));
    }
    private String buildUrl(){
        Log.e("TAG","打印出来的url是什么："+this.url +"?" +builderUrlParams());
        return  this.url +"?" +builderUrlParams();
    }

    private String builderUrlParams() {
        HashMap<String,Object> map = this.params;
        map.put("curPage",this.pageIndex);
        map.put("pageSize",this.pageSize);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String,Object> entry : map.entrySet()){
            sb.append(entry.getKey()+"="+entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if(s.endsWith("&")) {
            s = s.substring(0,s.length());
        }
        Log.e("TAG","s的值是多少？=="+s);
        return s;

    }

    private <T> void showData(List<T> datas,int totalPage,int totalCount){
        Log.e("TAG","展示数据时的状态是怎样的？"+state);
        Log.e("TAG","datas===？"+datas.size());
        Log.e("TAG","totalCount===？"+totalCount);
        switch (state) {
            case  STATE_NORMAL:
                if(this.pageListener != null) {
                    this.pageListener.load(datas,totalPage,totalCount);
                }
                break;
            case STATE_REFRESH:
                if(this.pageListener != null) {
                    this.pageListener.refresh(datas,totalPage,totalCount);
                }
                this.refreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                if(this.pageListener != null) {
                    this.pageListener.loadMore(datas,totalPage,totalCount);
                }
                this.refreshLayout.finishRefreshLoadMore();
                break;
        }
    }
    public interface OnPageListener<T>{
        void load(List<T> datas, int totalPage, int totalCount);
        void refresh(List<T> datas, int totalPage, int totalCount);
        void loadMore(List<T> datas, int totalPage, int totalCount);
    }


    public static class Builder {
        private Type type;
        private Context context;
        private String url;
        private MaterialRefreshLayout refreshLayout;
        private boolean canLoadMore;
        private int tatalPage = 1;
        private int pageIndex = 1;//页码
        private int pageSize = 2;

        private HashMap<String, Object> params = new HashMap<>(5);
        private OnPageListener onPageListener;
        public Builder setOnpageListener(OnPageListener onpageListener){
            this.onPageListener = onpageListener;
            return builder;
        }
        public Builder setUrl(String url) {
            this.url = url;
            return builder;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return builder;
        }

        public Builder setLoadMore(Boolean loadMore) {
            this.canLoadMore = loadMore;
            return builder;
        }

        public Builder putParam(String key, Object value) {
            params.put(key, value);
            return builder;
        }


        public PageUtils build(Context context,Type type) {
            this.type = type;
            this.context = context;
            validate();
            PageUtils pageUtils = new PageUtils();
            pageUtils.pageListener = this.onPageListener;
            pageUtils.context = this.context;
            pageUtils.url = this.url;
            pageUtils.refreshLayout = this.refreshLayout;
            pageUtils.canLoadMore = this.canLoadMore;
            pageUtils.tatalPage = this.tatalPage;
            pageUtils.pageIndex = this.pageIndex;
            pageUtils.pageSize = this.pageSize;
            pageUtils.params = this.params;
            pageUtils.initRefreshLayout();
            return pageUtils;
        }
        private void validate(){
            if(context==null) {
                throw new RuntimeException(" context can't be null");
            }
            if(this.url == null ||"" .equals(this.url)) {
                throw new RuntimeException(" URL can't be null");
            }
            if(this.refreshLayout == null) {
                throw new RuntimeException(" MaterialRefreshLayout can't be null");
            }
        }

    }
    class RequestCallBack<T> extends SpotsCallBack<PageResult<T>> {

        public RequestCallBack(Context mContext) {
            super(mContext);
            super.mType = builder.type;
        }

        @Override
        public void onSuccess(Response response, PageResult<T> pageResult) {
            Log.e("TAG","链接成功");
            builder.pageIndex = pageResult.getCurrentPage();
            builder.tatalPage = pageResult.getTotalPage();
            Log.e("TAG","得到的数据是："+pageResult.getList().size());
            showData(pageResult.getList(), builder.tatalPage,
                    pageResult.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {
            Log.e("TAG","链接失败");
        }
    }
}
