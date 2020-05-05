package com.example.museum.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.museum.Datas.Collection;
import com.example.museum.R;


import java.util.List;

public class CollectionAdapter_FindingPage extends RecyclerView.Adapter<CollectionAdapter_FindingPage.ViewHolder> {

    private List<Collection>    collecitonList;
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView collectionImage;
        TextView collectionName;


        public ViewHolder(View view) {
            super(view);
            collectionImage = (ImageView) view.findViewById(R.id.collection_image);
            collectionName = (TextView) view.findViewById(R.id.collection_name);
        }
    }
    public CollectionAdapter_FindingPage() {
        collecitonList = Collection.getTestDatas();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_findingpart2, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Collection collection = collecitonList.get(position);
        Glide.with(holder.itemView)
                .load(collection.getImgurl())
                .into(holder.collectionImage);
        holder.collectionName.setText(collection.getName());
//        holder.niceRatingBar.setRating(Float.parseFloat(museum.getStar()));             //显示博物馆的平均分数
    }
    @Override
    public int getItemCount() {
        return collecitonList.size();
    }

}