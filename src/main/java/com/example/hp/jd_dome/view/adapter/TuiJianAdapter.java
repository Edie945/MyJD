package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.TuiJian_MiaoShaBean;

import java.util.List;

public class TuiJianAdapter extends RecyclerView.Adapter {

    private List<TuiJian_MiaoShaBean.TuijianBean.ListBean> list;
    private Context context;

    public TuiJianAdapter(List<TuiJian_MiaoShaBean.TuijianBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tuijian_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).title_tuijian.setText(list.get(position).getTitle());
        ((MyViewHolder) holder).price_tuijian.setText(list.get(position).getPrice()+"");
//        ((MyViewHolder) holder).price_tuijian.setText(list.get(position).getPrice());
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(myViewHolder.img_tuijian);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_tuijian;
        TextView title_tuijian,price_tuijian;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_tuijian = itemView.findViewById(R.id.img_tuijian);
            title_tuijian = itemView.findViewById(R.id.title_tuijian);
            price_tuijian = itemView.findViewById(R.id.price_tuijian);
        }
    }
}
