package com.zd.dk_dbs.httprequest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class OkHttpHelper {
    private static OkHttpClient okHttpClient;
    private Gson mGson;
    private static OkHttpHelper mInstance;
    private Handler mHandler;
    static {
        mInstance = new OkHttpHelper();
    }
    private OkHttpHelper(){
        //设置时间
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }
    //和上面的单利模式对应
    public static OkHttpHelper getInstance(){
        return mInstance;
    }
    //网络请求的方法
    public void doRequest(final Request request, final BaseCallBack callBack){
        //请求网络之前  做一些  进度条呀 初始话等操作
        callBack.onRequestBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(request ,e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String resultStr = response.body().string();//把流转化为String类型
                   // Log.e("TAG","得到的类型是："+callBack.mType);
                    if(callBack.mType == String.class) {//看看 String 是不是这个类型
                        //callBack.onSuccess(response,resultStr);
                        callBackSuccess(callBack,response,resultStr);
                    }else{
                        try {

                            Object object = mGson.fromJson(resultStr,callBack.mType);

                            //封装 在handler中运行（主线程）
                            callBackSuccess(callBack, response, object);
                        } catch (JsonSyntaxException e) {//json语法异常
                           // callBack.onError(response,response.code(),e);
                            Log.e("TAG","出错的信息是："+e.toString());
                            callBackError(callBack,response,e);
                        }
                    }
                } else {
                    callBack.onError(response, response.code(),null);
                }
                callBack.onResponse(response);

            }
        });
    }
    public void get(String url,BaseCallBack callBack){
        Request request = buildRequest(url,null, HttpMethodType.GET);
        doRequest(request, callBack);
    }
    public void post(String url ,Map<String,Object> params,BaseCallBack callBack){
        Request request = buildRequest(url,params, HttpMethodType.POST);
        doRequest(request,callBack);
    }
    private Request buildRequest(String url,Map<String,Object> params,
                                 HttpMethodType methodType){
        Request.Builder builder = new  Request.Builder();
        builder.url(url);
        if(methodType == HttpMethodType.GET) {
            builder.get();
        }else if(methodType == HttpMethodType.POST){
            RequestBody body = buildFormData(params);
             builder.post(body);
        }
        return builder.build();
    }
    private RequestBody buildFormData(Map<String,Object> params){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null) {
            for(Map.Entry<String,Object> entry : params.entrySet()) {
                builder.add(entry.getKey(),
                        entry.getValue()==null ? "" : entry.getValue().toString());
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack,
                                 final Response response, final Object o){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response,o);

            }
        });
    }
    private void callBackError(final BaseCallBack callBack,
                                 final Response response, final Exception e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(response, response.code(), e);
            }
        });
    }

    enum HttpMethodType{
        GET,
        POST
    }
}

