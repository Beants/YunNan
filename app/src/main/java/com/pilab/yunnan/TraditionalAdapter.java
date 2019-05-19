package com.pilab.yunnan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TraditionalAdapter extends RecyclerView.Adapter<TraditionalAdapter.ViewHolder> {
    //设置点击事件
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    private Context context;

    private List<Traditional> traditionals = new ArrayList<>();

    public TraditionalAdapter(List<Traditional> traditionals) {
        this.traditionals = traditionals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {//设置上下文环境
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.traditional_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Traditional traditional = traditionals.get(position);
        holder.type.setText(traditional.getType());
        holder.info.setText(traditional.getInfo());
        Glide.with(context).load(traditional.getImgUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return traditionals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView type;
        TextView info;
//        ImageButton like;


        public ViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            image = (ImageView) itemView.findViewById(R.id.tr_image);
            type = (TextView) itemView.findViewById(R.id.tr_type);
            info = (TextView) itemView.findViewById(R.id.tr_info);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "onclick", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), traditionalDetail.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
