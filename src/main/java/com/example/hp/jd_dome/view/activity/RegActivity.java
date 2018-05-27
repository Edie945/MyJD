package com.example.hp.jd_dome.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.ModelImpel;
import com.example.hp.jd_dome.presenter.PresenterImpel;
import com.example.hp.jd_dome.view.interfaces.IRegView;

public class RegActivity extends AppCompatActivity implements IRegView {

    private EditText name;
    private EditText pwd;
    private RelativeLayout cancel_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_reg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        initDatas();
    }

    private void initDatas() {
        name = findViewById (R.id.name);

        pwd = findViewById (R.id.pwd);

        cancel_reg = findViewById(R.id.cancel_reg);

        Button reg = findViewById (R.id.reg);
        cancel_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegActivity.this.finish();
            }
        });
        reg.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                PresenterImpel presenterImpel = new PresenterImpel ();

                presenterImpel.ShowRegToView (new ModelImpel(), RegActivity.this);
            }
        });
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
        Toast.makeText (this,"注册成功--", Toast.LENGTH_SHORT).show ();
        RegActivity.this.finish();
    }

    @Override
    public void showError() {
        Toast.makeText (this,"注册失败--", Toast.LENGTH_SHORT).show ();
    }
}
