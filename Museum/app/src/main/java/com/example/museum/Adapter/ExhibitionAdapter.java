package com.example.museum.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Exhibition;
import com.example.museum.R;

import java.util.List;

public class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.ViewHolder> {

    private List<Exhibition> exhibitionList;
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView exhibitionImage;
        TextView exhibitionName;


        public ViewHolder(View view) {
            super(view);
            exhibitionImage = (ImageView) view.findViewById(R.id.exhibition_image);
            exhibitionName = (TextView) view.findViewById(R.id.exhibition_name);
        }
    }
    public ExhibitionAdapter() {
        exhibitionList = Exhibition.getTestDatas();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_findingpart1, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exhibition exhibition = exhibitionList.get(position);
        Glide.with(holder.itemView)
                .load(exhibition.getImgurl())
                .into(holder.exhibitionImage);
        holder.exhibitionName.setText(exhibition.getName());
//        holder.niceRatingBar.setRating(Float.parseFloat(museum.getStar()));             //显示博物馆的平均分数
    }
    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }

}