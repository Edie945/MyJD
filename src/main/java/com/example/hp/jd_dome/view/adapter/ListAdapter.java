package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.ListBean;
import com.example.hp.jd_dome.view.activity.DetailsActivity;
import com.example.hp.jd_dome.view.interfaces.Pid;
import com.example.hp.jd_dome.view.interfaces.Pscid;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private List<ListBean.DataBean> list;
    private Context context;

    public ListAdapter(List<ListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.linear_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).price.setText("￥"+list.get(position).getPrice());
        ((MyViewHolder) holder).title.setText(list.get(position).getTitle());
        if (list.get(position).getImages()!=null){
            String[] split = list.get(position).getImages().split("\\|");
            Glide.with(context).load(split[0]).into(((MyViewHolder) holder).img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: "+list.get(position).getPid() );
                Intent intent = new Intent(context, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pid",list.get(position).getPid()+"");
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title,price;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_);
            title = itemView.findViewById(R.id.title_text);
            price = itemView.findViewById(R.id.price);
        }
    }
}
