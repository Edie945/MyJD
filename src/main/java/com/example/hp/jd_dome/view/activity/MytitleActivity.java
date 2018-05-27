package com.example.hp.jd_dome.view.activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.view.fragment.Fragment_home;

public class MytitleActivity extends RelativeLayout implements View.OnClickListener {


    private onsetHuida onsethuida;
    private EditText et;
    private ImageView img;
    private TextView tvv;
    private TextView tv;

    public MytitleActivity(Context context) {
        this(context, null);
    }


    public MytitleActivity(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MytitleActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater from = LayoutInflater.from(context);
        View inflate = from.inflate(R.layout.search_title_main, this, true);

        img = (ImageView)inflate.findViewById(R.id.img);
        tvv = inflate.findViewById(R.id.tvv);
        et = (EditText)inflate.findViewById(R.id.et);
        tv = (TextView)inflate.findViewById(R.id.tv);
        tv.setOnClickListener(this);
        tvv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv:
                view.getContext().startActivity(new Intent(view.getContext(), MytitleActivity.class));
                Toast.makeText(getContext(),"返回",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvv:
                onsethuida.huida(et.getText().toString());
                break;
        }
    }

    public interface onsetHuida{
        void huida(String aa);
    }

    public void setJiekou(onsetHuida onsethuida){
        this.onsethuida=onsethuida;
    }
}
