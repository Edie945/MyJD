package com.example.hp.jd_dome.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.ListBean;
import com.example.hp.jd_dome.model.http.HttpUtils;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.view.adapter.ListAdapter;
import com.example.hp.jd_dome.view.interfaces.Pid;
import com.example.hp.jd_dome.view.interfaces.Pscid;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListActivity extends AppCompatActivity{

    private RecyclerView recy_view;
    private String path = "https://www.zhaoapi.cn/product/getProducts";
    private List<ListBean.DataBean> data;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                recy_view.setLayoutManager(new LinearLayoutManager(ListActivity.this,LinearLayoutManager.VERTICAL,false));
                adapter = new ListAdapter(data,ListActivity.this);
                recy_view.setAdapter(adapter);
            }
            if (msg.what == 1){
                recy_view.setLayoutManager(new LinearLayoutManager(ListActivity.this,LinearLayoutManager.VERTICAL,false));
                adapter = new ListAdapter(data,ListActivity.this);
                recy_view.setAdapter(adapter);
            }

        }
    };
    private ListAdapter adapter;
    private ListBean beans;
    private String pscid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recy_view = findViewById(R.id.recy_view);
        getIntentData();
        initData();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        pscid = bundle.getString("pscid");

    }

    private void initData() {
        HashMap<String,String> map = new HashMap<>();
//        map.put("page",""+page);
        map.put("pscid",pscid);
        MyHttpUtils.doPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.e("TAG", "onResponse: 新的数据 pscid是  "+pscid +"  数据"+s );
                Gson gson = new Gson();
                beans = gson.fromJson(s, ListBean.class);
                data = beans.getData();
                handler.sendEmptyMessage(0);
            }
        });
    }
}
