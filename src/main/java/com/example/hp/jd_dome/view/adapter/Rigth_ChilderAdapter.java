package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.Type_ChildBean;
import com.example.hp.jd_dome.view.activity.ListActivity;

import java.util.List;

public class Rigth_ChilderAdapter extends RecyclerView.Adapter {

    private List<Type_ChildBean.DataBean.ListBean> list;
    private Context context;

    public Rigth_ChilderAdapter(List<Type_ChildBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.type_right_child_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).text_childer.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(((MyViewHolder) holder).img_childer);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ;
                Intent intent = new Intent(context, ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pscid",list.get(position).getPscid()+"");
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
                Toast.makeText(context,"Pscid = "+list.get(position).getPscid()+"bundle = "+bundle,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_childer;
        TextView text_childer;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_childer = itemView.findViewById(R.id.img_type_right_child);
            text_childer = itemView.findViewById(R.id.text_type_right_child);
        }
    }
}
