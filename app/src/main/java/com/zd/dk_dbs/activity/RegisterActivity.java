package com.zd.dk_dbs.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.activity.base.BaseActivity;
import com.zd.dk_dbs.httprequest.Contants;
import com.zd.dk_dbs.utils.MyUtils;
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

public class RegisterActivity extends BaseActivity{
    @BindView(R.id.tel_number)
    EditText tel_number;
    @BindView(R.id.nicheng)
    EditText nicheng;
    @BindView(R.id.mima1)
    EditText mima1;
    @BindView(R.id.mima2)
    EditText mima2;
    @BindView(R.id.save_Register)
    Button save;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.save_Register)
    public void saveReg(View view){
        boolean btn = false;
        String number = null;
        String niC = null;
        String M1 = null;
        String M2 = null;
        if(view.getId()==R.id.save_Register) {
            //先得到输入框的信息
             number = tel_number.getText().toString();
             niC = nicheng.getText().toString();
             M1 = mima1.getText().toString();
             M2 = mima2.getText().toString();
            Log.e("TAG","进来点击事件了");
            if(number==null||niC==null||M1==null||M2==null) {
                ToastUtils.show(this,"注册信息不可以为空");
            }else {
                 if(MyUtils.isMobileNO(number)) {
                     ToastUtils.show(this,"手机号格式不正确");
                 }
                else if(!M1.equals(M2)) {
                    ToastUtils.show(this,"密码输入有误，请重新输入");
                }else {
                     btn = true;
                 }
            }
        }
        if(btn==true) {
            //开始请求网络
            OkHttpUtils
                    .post()
                    .url(Contants.API.REGISTER)
                    .addParams("phone",number)
                    .addParams("user_name",niC )
                    .addParams("avatar","123" )
                    .addParams("sign", "123")
                    .addParams("password", M1)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("TAG","打印信息==出错了");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("TAG","注册成功"+response);
                            MyUtils.intentShow(RegisterActivity.this,LoginActivity.class);
                        }
                    });
        }
        

    }
}
