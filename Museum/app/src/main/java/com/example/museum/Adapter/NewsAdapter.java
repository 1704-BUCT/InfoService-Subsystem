package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.museum.Datas.News;
import com.example.museum.NewsActivity;
import com.example.museum.R;
import java.util.List;

/*
* 新闻页面的recyclerView组件适配器的定义，主要定义了列表中每一项的内容和格式
* */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView newsImage;
        TextView newsName;
        public ViewHolder(View view) {
            super(view);
            newsView = view;
            newsImage = (ImageView) view.findViewById(R.id.news_image);
            newsName = (TextView) view.findViewById(R.id.news_title);
        }
    }
    // 自己找的测试数据
    public NewsAdapter() {
        mNewsList = News.getTestData();
    }
    // 从服务器上直接获取的数据
    public NewsAdapter(List<News> newsList) {
        mNewsList = newsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_firstnewsview, parent,
                        false);
        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
//                Toast.makeText(v.getContext(), "you clicked view " + news.getName(),
//                        Toast.LENGTH_SHORT).show();
                //在非activity类中使用startActicity只需写成getContext().startActivity(intent)即可
                Intent intent = new Intent(parent.getContext(), NewsActivity.class);
                intent.putExtra("Url",mNewsList.get(position).getUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mNewsList.get(position);
        Glide.with(holder.itemView)
                .load(news.getImgurl())
                .into(holder.newsImage);
        holder.newsName.setText(news.getName());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
