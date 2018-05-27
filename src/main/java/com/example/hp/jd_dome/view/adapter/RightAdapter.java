package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.SuDoKu_TypeBean;
import com.example.hp.jd_dome.model.bean.Type_ChildBean;

import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter {
    private List<Type_ChildBean.DataBean> list=new ArrayList<>();
    private Context context;
    private final SharedPreferences types;
    private final SharedPreferences.Editor edit;
    MyViewHolder recyViewHolder;
    private final SharedPreferences classtype;

    public RightAdapter(Context context) {
        this.context = context;
        types = context.getSharedPreferences("types", Context.MODE_PRIVATE);
        edit = types.edit();
        classtype = context.getSharedPreferences("classtype", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.type_right_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).textView.setText(list.get(position).getName());
        Rigth_ChilderAdapter rigth_childerAdapter = new Rigth_ChilderAdapter(list.get(position).getList(), context);
        ((MyViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL,false));
        ((MyViewHolder) holder).recyclerView.setAdapter(rigth_childerAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void GetNewData(List<Type_ChildBean.DataBean> rightdata) {
        this.list = rightdata;
        notifyDataSetChanged();//更新页面，刷新适配器
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.right_title);
            recyclerView = itemView.findViewById(R.id.right_cycler);
        }
    }
}
