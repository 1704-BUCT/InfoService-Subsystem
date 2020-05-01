package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.museum.Datas.TrafficRule;
import com.example.museum.R;

import java.util.List;


public class TrafficRuleAdapter  extends RecyclerView.Adapter<TrafficRuleAdapter .ViewHolder> {

    private List<TrafficRule> mTrafficList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        ImageView trafficImage;
        TextView trafficName;
        public ViewHolder(View view) {
            super(view);
            newsView = view;
            trafficImage = (ImageView) view.findViewById(R.id.traffic_image);
            trafficName = (TextView) view.findViewById(R.id.traffic_question);
        }
    }
    public TrafficRuleAdapter() {
//        mNewsList = News.getTestData();
    }
    public TrafficRuleAdapter(List<TrafficRule> trafficList) {
        mTrafficList = trafficList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exhibititem_inmuseum, parent,
                        false);
        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                TrafficRule news = mTrafficList.get(position);
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
        TrafficRule traffic = mTrafficList.get(position);
        Glide.with(holder.itemView)
                .load(traffic.getUrl())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.trafficImage);
//        holder.newsImage.setImageResource(.getImageId());
        holder.trafficName.setText(traffic.getQuestion());
    }

    @Override
    public int getItemCount() {
        return mTrafficList.size();
    }

}
