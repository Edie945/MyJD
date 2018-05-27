package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class MiaoShaAdapter extends RecyclerView.Adapter {

    private List<TuiJian_MiaoShaBean.MiaoshaBean.ListBeanX> list;
    private Context context;

    public MiaoShaAdapter(List<TuiJian_MiaoShaBean.MiaoshaBean.ListBeanX> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.miaosha_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).bargainPrice_miaosha.setText("￥"+list.get(position).getBargainPrice());
        ((MyViewHolder) holder).price_miaosha.setText("￥"+list.get(position).getPrice());
        ((MyViewHolder) holder).title_miaosha.setText(list.get(position).getTitle());
        ((MyViewHolder) holder).bargainPrice_miaosha.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(myViewHolder.img_miaosha);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_miaosha;
        TextView title_miaosha,price_miaosha,bargainPrice_miaosha;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_miaosha = itemView.findViewById(R.id.img_miaosha);
            title_miaosha = itemView.findViewById(R.id.title_miaosha);
            price_miaosha = itemView.findViewById(R.id.price_miaosha);
            bargainPrice_miaosha = itemView.findViewById(R.id.bargainPrice_miaosha);
        }
    }
}
