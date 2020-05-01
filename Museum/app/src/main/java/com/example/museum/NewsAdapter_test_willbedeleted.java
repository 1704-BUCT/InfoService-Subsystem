package com.example.museum;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.News;

import java.util.List;

/*
* 新闻页面的recyclerView组件适配器的定义，主要定义了列表中每一项的内容和格式
* */
public class NewsAdapter_test_willbedeleted extends RecyclerView.Adapter<NewsAdapter_test_willbedeleted.ViewHolder> {

    private List<News> mNewsList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView newsImage;
        TextView newsName;
        ImageView rankidpic;
        TextView rankid;
        @SuppressLint("WrongViewCast")
        public ViewHolder(View view) {
            super(view);
            newsView = view;
            newsImage = (ImageView) view.findViewById(R.id.news_image);
            newsName = (TextView) view.findViewById(R.id.news_title);
            rankid = view.findViewById(R.id.rank_id);
            rankidpic =  (ImageView)view.findViewById(R.id.rank_idpic);
        }
    }
    // 自己找的测试数据
    public NewsAdapter_test_willbedeleted() {
        mNewsList = News.getTestData();
    }
    // 从服务器上直接获取的数据
    public NewsAdapter_test_willbedeleted(List<News> newsList) {
        mNewsList = newsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_test, parent,
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
//                Intent intent = new Intent(parent.getContext(), NewsActivity.class);
//                intent.putExtra("Url",mNewsList.get(position).getUrl());
//                parent.getContext().startActivity(intent);
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
        if(position<3)
        {
            holder.rankid.setVisibility(View.INVISIBLE);
            if(position==0)
                holder.rankidpic.setImageResource(R.drawable.no_1);
            if(position==1)
                holder.rankidpic.setImageResource(R.drawable.no_2);
            if(position==2)
                holder.rankidpic.setImageResource(R.drawable.no_3);
            holder.rankidpic.setVisibility(View.VISIBLE);
        }

        else
        {
            holder.rankid.setVisibility(View.VISIBLE);
            holder.rankidpic.setVisibility(View.INVISIBLE);
            holder.rankid.setText(news.getId().toString());
        }

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
