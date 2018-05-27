package com.example.hp.jd_dome.view.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.hp.jd_dome.R;

public class MyHome_Title extends RelativeLayout {
    public MyHome_Title(Context context) {
        super(context);
        initView(context);
    }

    public MyHome_Title(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyHome_Title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_title, this, true);
    }

}
