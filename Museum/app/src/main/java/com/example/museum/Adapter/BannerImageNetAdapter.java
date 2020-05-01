package com.example.museum.Adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.museum.Datas.BannerData;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;
import java.util.List;
import com.example.museum.R;

/*
    轮播图banner组件适配器定义
* */
public class BannerImageNetAdapter extends BannerAdapter<BannerData, BannerImageHolder> {

    public BannerImageNetAdapter(List<BannerData> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = (ImageView) BannerUtils.getView(parent, R.layout.banner_image);
        return new BannerImageHolder(imageView);
    }

    @Override
    public void onBindView(BannerImageHolder holder, BannerData data, int position, int size) {
        //可以加载网络图片的一个依赖
        Glide.with(holder.itemView)
                .load(data.imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.imageView);
    }

}
