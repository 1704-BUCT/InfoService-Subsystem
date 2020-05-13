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


/*
* 发现页面展览部分recycleView的适配器定义
* */
public class ExhibitionAdapter_FindingPage extends RecyclerView.Adapter<ExhibitionAdapter_FindingPage.ViewHolder> {

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
    //获得测试数据
    public ExhibitionAdapter_FindingPage() {
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
    }
    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }

}
