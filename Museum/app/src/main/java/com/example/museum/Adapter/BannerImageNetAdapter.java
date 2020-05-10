package com.example.museum.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
//import com.example.museum.Datas.BannerData;
import com.example.museum.Datas.News;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;
import java.util.List;
import com.example.museum.R;

/*
    轮播图banner组件适配器定义
* */
public class BannerImageNetAdapter extends BannerAdapter<News, BannerImageHolder> {

    public BannerImageNetAdapter(List<News> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ImageView imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_image, parent,
                        false);
        BannerImageHolder holder = new BannerImageHolder(view);
        return holder;
    }

    @Override
    public void onBindView(BannerImageHolder holder, News data, int position, int size) {
        if(data.getImgurl().equals("") || data.getImgurl()==null)
        {
            //可以加载网络图片的一个依赖
            Glide.with(holder.itemView)
                    .load(R.drawable.news_picnull)
                    .into(holder.imageView);
        }
        else
        Glide.with(holder.itemView)
                .load(data.getImgurl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.imageView);
        holder.textView.setText(data.getTitle());
    }

}
