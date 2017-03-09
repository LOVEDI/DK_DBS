package com.zd.dk_dbs.httprequest;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public  abstract class BaseCallBack<T> {
    public  Type mType;
    /**
     * 将泛型T转为Type
     */
    static Type getSuperclassTypeParameter(Class<?> subclass){
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class){
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType)superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
    public BaseCallBack(){
        mType = getSuperclassTypeParameter(getClass());
    }
  //细分 网络请求
    public abstract void onRequestBefore(Request request);
    //请求网络时出现不可恢复的错误时 调用该方法
    public  abstract void onFailure(Request request, IOException e);
    //请求网络成功时调用该方法
    public  abstract void onSuccess(Response response, T t);

    public  abstract void onError(Response response,int code,Exception e);

    public  abstract void onResponse(Response response);

}
