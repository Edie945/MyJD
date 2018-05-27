package com.example.hp.jd_dome.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.view.fragment.Fragment_home;
import com.example.hp.jd_dome.view.fragment.Fragment_mine;
import com.example.hp.jd_dome.view.fragment.Fragment_shopCart;
import com.example.hp.jd_dome.view.fragment.Fragment_type;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment_home fragment_home;
    private Fragment_type fragment_type;
    private Fragment_shopCart fragment_shopCart;
    private Fragment_mine fragment_mine;
    private ArrayList<Fragment> fragments;
    private FrameLayout frame;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImmersion();//沉浸式
        initView();
        initFrame();

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

    private void initView() {
        fragment_home = new Fragment_home();
        fragment_type = new Fragment_type();
        fragment_shopCart = new Fragment_shopCart();
        fragment_mine = new Fragment_mine();
        fragments = new ArrayList<>();
        fragments.add(fragment_home);
        fragments.add(fragment_type);
        fragments.add(fragment_shopCart);
        fragments.add(fragment_mine);

        frame = (FrameLayout) findViewById(R.id.frame);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    private void initFrame() {
        final FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.frame,fragments.get(0)).commit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home:
                        manager.beginTransaction().replace(R.id.frame,fragments.get(0)).commit();
                        break;
                    case R.id.rb_type:
                        manager.beginTransaction().replace(R.id.frame,fragments.get(1)).commit();
                        break;
                    case R.id.rb_shopCart:
                        manager.beginTransaction().replace(R.id.frame,fragments.get(2)).commit();
                        break;
                    case R.id.rb_mine:
                        manager.beginTransaction().replace(R.id.frame,fragments.get(3)).commit();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragment_home.onActivityResult(requestCode,resultCode,data);

    }
}
