package com.example.hp.jd_dome.view.activity;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

public class QRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
