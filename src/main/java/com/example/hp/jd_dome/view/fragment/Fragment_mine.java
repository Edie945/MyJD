package com.example.hp.jd_dome.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.view.activity.LoginActivity;
import com.example.hp.jd_dome.view.activity.MainActivity;


public class Fragment_mine extends Fragment implements View.OnClickListener {

    /*private View view;
    private LinearLayout dlzc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_mine, null);

        initView();

        return view;
    }

    private void initView() {

        dlzc = (LinearLayout) view.findViewById(R.id.dlzc);
        dlzc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dlzc:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
        }
    }*/
    private LinearLayout dlzc;
    private SharedPreferences types;
    private TextView myfragment_dl_zc;
    private ImageView myfragment_img;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        initView();
        getShareData();
        return view;
    }

    private void getShareData() {
        types = getActivity().getSharedPreferences("types", Context.MODE_PRIVATE);
        boolean dltype = types.getBoolean("dltype", false);
        if (dltype){
            String dlname = types.getString("dlname", "");
            String dlimg = types.getString("dlimg", "");
            String dlzh = types.getString("dlzh", "");
            if (!dlname.isEmpty()){
                myfragment_dl_zc.setText(dlname);
            }
            if(!dlimg.isEmpty()){
                Glide.with(getActivity()).load(dlimg).into(myfragment_img);
            }
            Toast.makeText(getActivity(), "欢迎回来-"+dlname+"!", Toast.LENGTH_SHORT).show();
        }else{
            myfragment_dl_zc.setText("登录/注册>");
            Glide.with(getActivity()).load(R.drawable.mqq);
        }

    }

    private void initView() {
        dlzc = (LinearLayout) view.findViewById(R.id.dlzc);
        myfragment_dl_zc = view.findViewById(R.id.myfragment_dl_zc);
        myfragment_img = view.findViewById(R.id.myfragment_img);
        dlzc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dlzc:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
        }
    }
}
