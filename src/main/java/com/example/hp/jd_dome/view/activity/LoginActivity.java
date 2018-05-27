package com.example.hp.jd_dome.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.ModelImpel;
import com.example.hp.jd_dome.model.bean.AddressGetUid;
import com.example.hp.jd_dome.model.bean.dlBean;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.presenter.PresenterImpel;
import com.example.hp.jd_dome.view.interfaces.ILoginView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements /*ILoginView,*/ View.OnClickListener {

/*    private RelativeLayout cancelLogin;
    *//**
     * 用户名/手机号/邮箱
     *//*
    private EditText name;
    *//**
     * 请输入密码
     *//*
    private EditText pwd;
    *//**
     * 登录
     *//*
    private Button login;
    *//**
     * 注册
     *//*
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        initView();

    }

    @Override
    public String getMobile() {
        return name.getText ().toString ();
    }

    @Override
    public String getPwd() {
        return pwd.getText ().toString ();
    }

    @Override
    public void showSuccess() {
        Toast.makeText (LoginActivity.this,"登录成功··",Toast.LENGTH_SHORT).show ();
        LoginActivity.this.finish();
    }

    @Override
    public void showError() {
        Toast.makeText (LoginActivity.this,"登录失败··",Toast.LENGTH_SHORT).show ();

    }

    private void initView() {
        cancelLogin = (RelativeLayout) findViewById(R.id.cancel_login);
        cancelLogin.setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        reg = (Button) findViewById(R.id.reg);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cancel_login:
                LoginActivity.this.finish();
                break;
            case R.id.login:
                PresenterImpel presenterImpel = new PresenterImpel ();

                presenterImpel.ShowLoginToView (new ModelImpel(),LoginActivity.this);
                break;
            case R.id.reg:
                startActivity (new Intent(LoginActivity.this,RegActivity.class));
                break;
        }
    }*/
    /**
     * 请输入手机号
     */
    private EditText editPhone;
    /**
     * 请输入密码
     */
    private EditText editPass;
    /**
     * 登录
     */
    private Button dlBut;
    /**
     * 手机快速注册
     */
    private TextView zcBut;
    /**
     * 忘记密码
     */
    private RelativeLayout cancelLogin;
    private TextView zhBut;
    private ImageView qqDlImg;
    private String edit_phone;
    private String edit_pass;
    private String dlurl="https://www.zhaoapi.cn/user/login";
    private dlBean.DataBean data;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==3){
                Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                setResult(22);
//                startActivity(new Intent(MainActivity.this,MineActivity.class));
                finish();
            }
            if(msg.what==4){
                Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private SharedPreferences types;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initReg() {
        startActivity(new Intent(LoginActivity.this,RegActivity.class));
    }

    private void initLogin() {
        edit_phone = editPhone.getText().toString();
        edit_pass = editPass.getText().toString();
        if(edit_phone==""){
            Toast.makeText(LoginActivity.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edit_pass ==""){
            Toast.makeText(LoginActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean telPhoneNumber = isTelPhoneNumber(edit_phone);
        if (telPhoneNumber){
            boolean numeric = isNumeric(edit_pass);
            if (numeric){
                HashMap<String,String> map = new HashMap<>();
                map.put("mobile",edit_phone);
                map.put("password",edit_pass);
                MyHttpUtils.doPost(dlurl, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Log.e("TAG", "onResponse: "+s );
                        Gson gson = new Gson();
                        dlBean dlBean = gson.fromJson(s, dlBean.class);
                        data = dlBean.getData();
                        if (dlBean.getCode().equals("1")){
                            handler.sendEmptyMessage(4);
                            return;
                        }
                        String icon = (String) data.getIcon();
                        String nickname = (String) data.getNickname();
                        String mobile = data.getMobile();
                        int uid = data.getUid();
                        String token = data.getToken();

                        types = getSharedPreferences("types", MODE_PRIVATE);
                        edit = types.edit();

                        Log.d("TAG", "onResponse: 头像："+icon+"  ---  昵称："+nickname+"  ---  手机号："+mobile+"  ---  uid："+uid+"  ---  token值："+token);
                        edit.putBoolean("dltype",true);
                        edit.putString("dlimg", icon);
                        edit.putString("dlname",nickname);
                        edit.putString("dlzh",mobile);
                        edit.putString("dluid",uid+"");
                        edit.putString("dltoken",token);
                        edit.commit();

                        AddressGetUid addressGetUid = new AddressGetUid();
                        addressGetUid.setUid(uid+"");
                        EventBus.getDefault().postSticky(addressGetUid);
                        handler.sendEmptyMessageDelayed(3,200);
                    }
                });
            }else {
                Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this, "账号错误!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        cancelLogin = (RelativeLayout) findViewById(R.id.cancel_login);
        cancelLogin.setOnClickListener(this);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editPass = (EditText) findViewById(R.id.edit_pass);
        dlBut = (Button) findViewById(R.id.dl_but);
        dlBut.setOnClickListener(this);
        zcBut = (TextView) findViewById(R.id.zc_but);
        zcBut.setOnClickListener(this);
        zhBut = (TextView) findViewById(R.id.zh_but);
        zhBut.setOnClickListener(this);
        qqDlImg = (ImageView) findViewById(R.id.qq_dl_img);
        qqDlImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dl_but:
                initLogin();
                break;
            case R.id.zc_but:
                initReg();
                break;
            case R.id.zh_but:
                break;
            case R.id.qq_dl_img:
                break;
            case R.id.cancel_login:
                LoginActivity.this.finish();
                break;
        }
    }
    /**
     * 校验手机号
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
