package com.zd.dk_dbs.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zd.dk_dbs.Entity.Login;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.activity.base.BaseActivity;
import com.zd.dk_dbs.httprequest.Contants;
import com.zd.dk_dbs.utils.MyUtils;
import com.zd.dk_dbs.utils.PrrferencesUtils;
import com.zd.dk_dbs.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class LoginActivity extends BaseActivity{
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.save)
    Button login_save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String user_id = PrrferencesUtils.getString(this, "user_id");
        if(user_id!=null&&!user_id.equals("")) {
            MyUtils.intentShow(LoginActivity.this,MainActivity.class);
        }
        ButterKnife.bind(this);

    }
    @OnClick(R.id.save)
    public void login(View view){
        boolean boo = true;
        String name = null;
        String pwd = null;
        if(userName!=null&&password!=null) {
            name = userName.getText().toString();
            pwd = password.getText().toString();

        }else{
            ToastUtils.show(this,"输入框不可以为空");
            boo = false;
        }
        if(view.getId()==R.id.save) {
            //开始请求网络
            if(boo) {
                OkHttpUtils
                        .post()
                        .url(Contants.API.LOGIN)
                        .addParams("phone",name)
                        .addParams("password", pwd)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("TAG","打印信息==出错了");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG","登录成功"+response);
                                Gson gson =  new Gson();
                                Login login = gson.fromJson(response, Login.class);
                                long user_id = login.result.id;
                                Log.e("TAG","得到的信息是："+user_id);
                                PrrferencesUtils.putString(LoginActivity.this,"user_id",user_id+"");
                                MyUtils.intentShow(LoginActivity.this,MainActivity.class);
                            }
                        });
            }

        }
    }
}
