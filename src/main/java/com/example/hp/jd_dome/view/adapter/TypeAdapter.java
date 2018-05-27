package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.SuDoKu_TypeBean;
import com.example.hp.jd_dome.view.interfaces.Cid;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter {

    private List<SuDoKu_TypeBean.DataBean> list;
    private Context context;
    private Cid cid;


    public TypeAdapter(List<SuDoKu_TypeBean.DataBean> list, Context context,Cid cid) {
        this.list = list;
        this.context = context;
        this.cid=cid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.type_item_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.text_type.setText(list.get(position).getName());
        myViewHolder.itemView.setTag(position);
        Log.i("TAG", "onBindViewHolder: "+cid);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cid.getCid(list.get(position).getCid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_type;
        public MyViewHolder(View itemView) {
            super(itemView);
            text_type = itemView.findViewById(R.id.item_text_type);
        }
    }
}
