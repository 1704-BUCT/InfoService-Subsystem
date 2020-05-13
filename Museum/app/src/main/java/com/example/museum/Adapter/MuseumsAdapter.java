package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Museum;
import com.example.museum.Datas.News;
import com.example.museum.MuseumActivity;
import com.example.museum.NewsActivity;
import com.example.museum.R;
import com.kaelli.niceratingbar.NiceRatingBar;
import com.kaelli.niceratingbar.OnRatingChangedListener;
import java.util.List;


/*
    museums的recyclerView组件的适配器定义：设置了列表中每一项的内容和格式
* */
public class MuseumsAdapter extends RecyclerView.Adapter<MuseumsAdapter.ViewHolder> {

    private List<Museum> museumList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View museumview;
        ImageView museumImage;
        TextView museumName;
        NiceRatingBar niceRatingBar;

        public ViewHolder(View view) {
            super(view);
            museumview = view;
            museumImage = (ImageView) view.findViewById(R.id.museum_image);
            museumName = (TextView) view.findViewById(R.id.museum_name);
            niceRatingBar = (NiceRatingBar) view.findViewById(R.id.show_RatingBar);
        }
    }
    public MuseumsAdapter() {
        museumList = Museum.getTestData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_museumsview, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        holder.niceRatingBar.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(float rating) {
//                Toast.makeText(parent.getContext(),rate.toString(),Toast.LENGTH_SHORT).show();
                // 在这里，得到的rating值就是用户通过点击/滑动，给的评分了，可以把这个值传递到服务端
            }
        });
        holder.museumview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Museum museum = museumList.get(position);
//                Toast.makeText(v.getContext(), "you clicked view " + news.getName(),
//                        Toast.LENGTH_SHORT).show();
                //在非activity类中使用startActicity只需写成getContext().startActivity(intent)即可
                Intent intent = new Intent(parent.getContext(), MuseumActivity.class);
//                intent.putExtra("Url",museumList.get(position).getUrl());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Museum museum = museumList.get(position);
        Glide.with(holder.itemView)
                .load(museum.getImgurl())
                .into(holder.museumImage);
        holder.museumName.setText(museum.getName());
        holder.niceRatingBar.setRating((float) museum.getAvgstar());             //显示博物馆的平均分数
}

    @Override
    public int getItemCount() {
        return museumList.size();
    }

}
