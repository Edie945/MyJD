package com.example.hp.jd_dome.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.TuiJian_MiaoShaBean;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.view.adapter.MiaoShaAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MiaoShaActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyView;
    private List<TuiJian_MiaoShaBean.MiaoshaBean.ListBeanX> miaoshadata;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                MiaoShaAdapter miaoShaAdapter = new MiaoShaAdapter(miaoshadata, MiaoShaActivity.this);
                recyView.setLayoutManager(new LinearLayoutManager(MiaoShaActivity.this, LinearLayoutManager.VERTICAL, false));
                recyView.setAdapter(miaoShaAdapter);
            }
        }
    };
    private ImageView imgMiaosha;
    /**
     * 【夏季特賣】  AEMAPE短袖 T恤 男裝新品商務休閒純色純棉 T恤 白色 180/XL
     */
    private TextView titleMiaosha;
    /**
     * ￥7999
     */
    private TextView bargainPriceMiaosha;
    /**
     * ￥3999
     */
    private TextView priceMiaosha;
    /**
     * 立即抢购
     */
    private TextView textMiaosha;
    private TextView miaosha_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao_sha);
        initImmersion();
        initView();
        initData();
    }

    private void initImmersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initData() {
        final String path = "https://www.zhaoapi.cn/ad/getAd";
        MyHttpUtils.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MyHttpUtils.doGet(path, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s = response.body().string();
                        Gson gson = new Gson();
                        TuiJian_MiaoShaBean miaoShaBean = gson.fromJson(s, TuiJian_MiaoShaBean.class);
                        miaoshadata = miaoShaBean.getMiaosha().getList();
                        handler.sendEmptyMessage(0);
                    }
                });
            }
        });
    }

    private void initView() {
        recyView = (RecyclerView) findViewById(R.id.miaosha_recy_view);

        imgMiaosha = (ImageView) findViewById(R.id.img_miaosha);
        titleMiaosha = (TextView) findViewById(R.id.title_miaosha);
        bargainPriceMiaosha = (TextView) findViewById(R.id.bargainPrice_miaosha);
        priceMiaosha = (TextView) findViewById(R.id.price_miaosha);
        textMiaosha = (TextView) findViewById(R.id.text_miaosha);
        miaosha_Back = (TextView) findViewById(R.id.miaosha_back);
        miaosha_Back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.miaosha_back:
                MiaoShaActivity.this.finish();
                break;
        }
    }
}
