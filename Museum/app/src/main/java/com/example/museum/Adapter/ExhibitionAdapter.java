package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Exhibition;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import java.util.ArrayList;
import java.util.List;


/*
* 搜索展览页面的recycleView的适配器定义
* */
public class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.ViewHolder> {

    private List<Exhibition> exhibitionList=new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder {
        View exhibitionView;
        ImageView exhibitionImage;
        TextView exhibitionName;
        TextView museumName;

        public ViewHolder(View view) {
            super(view);
            exhibitionView = view;
            exhibitionImage = (ImageView) view.findViewById(R.id.exhibition_image);
            exhibitionName = (TextView) view.findViewById(R.id.exhibition_name);
            museumName = (TextView) view.findViewById(R.id.museum_name);
        }
    }

    public ExhibitionAdapter(List<Exhibition> data) {
        exhibitionList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_exhibitionview, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        holder.exhibitionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Exhibition exhibition = exhibitionList.get(position);
                Intent intent = new Intent(parent.getContext(), NewsActivity.class);
                intent.putExtra("Url",exhibition.getImgurl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exhibition antique = exhibitionList.get(position);
        Glide.with(holder.itemView)
                .load(antique.getImgurl())
                .error(R.drawable.pic_null)
                .into(holder.exhibitionImage);
        holder.exhibitionName.setText(antique.getName());
        holder.museumName.setText(antique.getMname());
    }

    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }
}
