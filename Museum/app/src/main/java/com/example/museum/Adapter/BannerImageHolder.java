package com.example.museum.Adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.R;

/*
    轮播图banner组件放图片的容器类定义
* */
public class BannerImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public BannerImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view.findViewById(R.id.image);
        this.textView = (TextView) view.findViewById(R.id.banner_news_title);
    }
}