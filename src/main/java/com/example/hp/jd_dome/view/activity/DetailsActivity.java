package com.example.hp.jd_dome.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.DerailsBean;
import com.example.hp.jd_dome.model.bean.Mydenglu;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.view.fragment.Fragment_shopCart;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private XBanner xbanner;
    private TextView gouwuche;
    private DerailsBean derailsBean;
    private String url = "https://www.zhaoapi.cn/product/getProductDetail";
    private String addurl = "https://www.zhaoapi.cn/product/addCart";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.e("TAG", "handleMessage: "+data.getImages() );
                if (data.getImages().isEmpty() || data.getImages() == null || data.getImages() == ""){
                }else {
                    String[] split = data.getImages().split("\\|");
                    for (int i = 0; i < split.length; i++) {
                        bannerlist.add(split[i]);
                    }
                    xbanner.setData(bannerlist, null);
                    xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(DetailsActivity.this).load(bannerlist.get(position)).into((ImageView) view);
                        }
                    });
                    titleDerails.setText(data.getTitle());
                    subhead_derails.setText(data.getSubhead());
                    priceDerails.setText("￥"+data.getPrice());
                }
            }
            if (msg.what == 2){
                Toast.makeText(DetailsActivity.this, "添加失败!", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 3){
                Toast.makeText(DetailsActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private ArrayList<String> bannerlist = new ArrayList<>();

    /**
     * 加入购物车
     */
    private TextView jiagou;
    /**
     * 立即购买
     */
    private TextView goumai;
    private String images;
    /**
     * 我就像那一只励志欧诺个小鸟
     */
    private TextView titleDerails;
    /**
     * ￥8888
     */
    private TextView priceDerails;
    private SharedPreferences types;
    private String dluid;
    private String pid;
    private DerailsBean.DataBean data;
    private TextView subhead_derails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        getShareData();
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        pid = bundle.getString("pid");
        if (pid == "" || pid == null || pid.isEmpty()){
            Toast.makeText(this, "是空的!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            HashMap<String,String> map = new HashMap<>();
            map.put("pid",pid);
            map.put("source","android");
            MyHttpUtils.doPost(url, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    Gson gson = new Gson();
                    DerailsBean derailsBean = gson.fromJson(s, DerailsBean.class);
                    data = derailsBean.getData();
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }

    private void getShareData() {
        types = getSharedPreferences("type", MODE_PRIVATE);
        dluid = types.getString("dluid", "");
    }

    private void initView() {

        xbanner = (XBanner) findViewById(R.id.xbanner);
        gouwuche = (TextView) findViewById(R.id.gouwuche);
        gouwuche.setOnClickListener(this);
        jiagou = (TextView) findViewById(R.id.jiagou);
        jiagou.setOnClickListener(this);
        goumai = (TextView) findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
        titleDerails = (TextView) findViewById(R.id.title_derails);
        priceDerails = (TextView) findViewById(R.id.price_derails);
        subhead_derails = (TextView) findViewById(R.id.subhead_derails);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.gouwuche:
                break;
            case R.id.jiagou:
                insertClick();
                break;
            case R.id.goumai:
                justShop();
                break;
        }
    }

    private void justShop() {

    }

    private void insertClick() {
        if (dluid.isEmpty() || dluid == null || dluid == ""){
            Toast.makeText(DetailsActivity.this,"您当前未登录",Toast.LENGTH_SHORT).show();
            return;
        }else {
            HashMap<String,String> map = new HashMap<>();
            map.put("uid",dluid);
            map.put("pid",pid);
            map.put("soucre","android");
            MyHttpUtils.doPost(addurl, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string();
                    Gson gson = new Gson();
                    Mydenglu mydenglu = gson.fromJson(s, Mydenglu.class);
                    String code = mydenglu.getCode();
                    Log.e("TAG", "onResponse: 加入购物车"+code );
                    if (code.equals("1")){
                        handler.sendEmptyMessage(2);
                    }
                    if (code.equals("0")){
                        handler.sendEmptyMessage(3);
                    }
                }
            });
        }
    }
}
