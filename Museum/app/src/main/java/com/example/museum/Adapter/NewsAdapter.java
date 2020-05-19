package com.example.museum.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.museum.Datas.News;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import java.io.FileNotFoundException;
import java.util.List;

import static java.security.AccessController.getContext;

/*
* 新闻页面的recyclerView组件适配器的定义，主要定义了列表中每一项的内容和格式
* */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView newsImage;
        TextView newsName;
        TextView newsAuthor;
        TextView newsReleasetime;
        public ViewHolder(View view) {
            super(view);
            newsView = view;
            newsImage = (ImageView) view.findViewById(R.id.news_image);
            newsName = (TextView) view.findViewById(R.id.news_title);
            newsAuthor = (TextView)view.findViewById(R.id.news_author);
            newsReleasetime = (TextView)view.findViewById(R.id.news_releasetime);
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
        context=parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
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
        if(news.getImgurl().equals("") || news.getImgurl()==null)
        {
            Glide.with(holder.itemView)
                    .load(R.drawable.news_picnull)
                    .transform(new RoundedCorners(40))
                    .into(holder.newsImage);
        }
        else
        {
            Glide.with(holder.itemView)
                    .load(news.getImgurl())
                    .transform(new RoundedCorners(40))
                    .error(R.drawable.news_picnull)
                    .into(holder.newsImage);

        }

        holder.newsName.setText(news.getName());
        if(position>3)
        {
            holder.newsAuthor.setText(news.getAuthor());
            holder.newsReleasetime.setText(news.getReleasetime().toString());
        }

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
