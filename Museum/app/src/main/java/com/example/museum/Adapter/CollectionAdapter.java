package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Collection;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import java.util.List;

/*
* 搜索页面藏品recycleView的适配器定义
* */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<Collection> antiqueList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image;
        TextView name;
        TextView mname;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            image = (ImageView) view.findViewById(R.id.exhibition_image);
            name = (TextView) view.findViewById(R.id.exhibition_name);
            mname = (TextView) view.findViewById(R.id.museum_name);
        }
    }

    public CollectionAdapter(List<Collection> data) {
        antiqueList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_exhibitionview, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Collection antique = antiqueList.get(position);
                Intent intent = new Intent(parent.getContext(), NewsActivity.class);
                intent.putExtra("Url",antique.getImgurl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Collection antique = antiqueList.get(position);
        Glide.with(holder.itemView)
                .load(antique.getImgurl())
                .into(holder.image);
        holder.name.setText(antique.getName());
        holder.mname.setText(antique.getMname());
    }

    @Override
    public int getItemCount() {
        return antiqueList.size();
    }
}
