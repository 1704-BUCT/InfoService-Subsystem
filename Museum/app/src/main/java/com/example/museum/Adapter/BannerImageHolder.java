package com.example.museum.Adapter;


import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
    轮播图banner组件放图片的容器类定义
* */
public class BannerImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public BannerImageHolder(@NonNull View view) {
        super(view);
        this.imageView = (ImageView) view;
    }
}