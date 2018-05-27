package com.example.hp.jd_dome.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.hp.jd_dome.R;

public class ZXingActivity extends Activity {
    //控件设了个全局
    private WebView wb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        //找控件
        wb = findViewById(R.id.wb);
        TextView receptionTv = findViewById(R.id.receptionTv);
        //支持js语言
        wb.getSettings().setJavaScriptEnabled(true);
        // 缩放至屏幕的大小
        wb.getSettings().setLoadWithOverviewMode(true);
        //支持缩放
        wb.getSettings().setSupportZoom(true);
        //声明一个Intent意图，用来接受MainActivity传过来的值
        Intent intent = getIntent();
        //拿到MainActivity传过来的值并返回一个字符串，
        //口令要一致
        String jxString = intent.getStringExtra("path");
        //把字符串赋值给输入框
        receptionTv.setText(jxString);
        //webVew去加载网页
        wb.loadUrl(jxString);
        //设置用自己的浏览器打开
        wb.setWebViewClient(new MyWebViewClient());

        //设置它的进度
        /*wb.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //拿到当前进度的int值

            }
        });

    }*/

        //是否允许返回
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            //让webView返回上一级
            wb.goBack();
        }
        return true;
    }*/


    }

    //自定义浏览器
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    //预防内存泄露
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
