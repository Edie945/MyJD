package com.example.hp.jd_dome.view.adapter;

import android.content.Context;
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
import com.example.hp.jd_dome.model.bean.SuDoKu_TypeBean;
import com.example.hp.jd_dome.view.activity.MainActivity;
import com.example.hp.jd_dome.view.interfaces.Cid;

import org.w3c.dom.Text;

import java.util.List;

public class SuDoKu_TypeAdapter extends RecyclerView.Adapter {

    private List<SuDoKu_TypeBean.DataBean> list;
    private Context context;
    private Cid cid;

    public SuDoKu_TypeAdapter(List<SuDoKu_TypeBean.DataBean> list, Context context, Cid cid) {
        this.list = list;
        this.context = context;
        this.cid = cid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.sudoku_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ((MyViewHolder) holder).text_sudoku.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(((MyViewHolder) holder).img_sudoku);
        myViewHolder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cid.getCid(list.get(position).getCid());
                Log.e("TAG", "getCid: "+list.get(position).getCid() );
                Toast.makeText(context,""+list.get(position).getCid(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_sudoku;
        TextView text_sudoku;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_sudoku = itemView.findViewById(R.id.img_sudoku);
            text_sudoku = itemView.findViewById(R.id.text_sudoku);
        }
    }
}
