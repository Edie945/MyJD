package com.example.hp.jd_dome.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.SuDoKu_TypeBean;
import com.example.hp.jd_dome.model.bean.Type_ChildBean;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.view.activity.MainActivity;
import com.example.hp.jd_dome.view.adapter.RightAdapter;
import com.example.hp.jd_dome.view.adapter.SuDoKu_TypeAdapter;
import com.example.hp.jd_dome.view.adapter.TypeAdapter;
import com.example.hp.jd_dome.view.interfaces.Cid;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fragment_type extends Fragment implements Cid{
    private View view;
    private RecyclerView leftView;
    private RecyclerView rightView;
    private String left = "https://www.zhaoapi.cn/product/getCatagory";
    private String right = "https://www.zhaoapi.cn/product/getProductCatagory";
    private List<SuDoKu_TypeBean.DataBean> leftdata;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                typeAdapter = new TypeAdapter(leftdata,getActivity(),Fragment_type.this);
                leftView.setLayoutManager(new LinearLayoutManager(getActivity(),1,false));
                leftView.setAdapter(typeAdapter);
            }
            if (msg.what == 1){
                if(rightdata!=null){
                    rightAdapter = new RightAdapter(getActivity());
                    rightAdapter.GetNewData(rightdata);
                    rightView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    rightView.setAdapter(rightAdapter);
                }

            }
            if (msg.what == 2){
                if(rightAdapter==null){
                    rightAdapter = new RightAdapter(getActivity());
                    rightAdapter.GetNewData(rightdata);
                    rightView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    rightView.setAdapter(rightAdapter);
                    return;
                }
                if(rightdata!=null){
                    rightAdapter.GetNewData(rightdata);
                }

               // rightAdapter.notifyDataSetChanged();
            }
        }
    };
    private TypeAdapter typeAdapter;
    private List<Type_ChildBean.DataBean> rightdata=new ArrayList<>();
    private List<Type_ChildBean.DataBean> type_childdata;
    private RightAdapter rightAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_type, null);

        initView(view);
        initLeft();//左侧分类
        initRgith();//右侧子分类
        return view;
    }

    private void initRgith() {
        HashMap<String,String> map = new HashMap<>();
        map.put("cids","1");
        MyHttpUtils.doPost(right,map,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                Type_ChildBean type_childBean = gson.fromJson(s, Type_ChildBean.class);
                rightdata = type_childBean.getData();
                handler.sendEmptyMessage(1);
            }
        });
    }

    private void initLeft() {
        MyHttpUtils.doGet(left, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                SuDoKu_TypeBean suDoKu_typeBean = gson.fromJson(s, SuDoKu_TypeBean.class);
                leftdata = suDoKu_typeBean.getData();
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void initView(View view) {
        leftView = (RecyclerView) view.findViewById(R.id.left_view);
        rightView = (RecyclerView) view.findViewById(R.id.right_view);
    }

    @Override
    public void getCid(int cids) {
        Log.e("TAG", "getCid: "+cids );
        Toast.makeText(getContext(),""+cids,Toast.LENGTH_SHORT).show();
        HashMap<String,String> map = new HashMap<>();
        map.put("cid",""+cids);
        map.put("source","android");
        MyHttpUtils.doPost(right, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // rightdata.clear();
                String s = response.body().string();
                Log.d("TAGwswswdada", "onResponse: 新数据"+s);
                Gson gson = new Gson();
                Type_ChildBean type_childBean = gson.fromJson(s, Type_ChildBean.class);
                rightdata = type_childBean.getData();
                Log.i("TAGwasdwasdwasdad", "onResponse:集合"+rightdata);
                handler.sendEmptyMessage(2);
            }
        });
    }
}
