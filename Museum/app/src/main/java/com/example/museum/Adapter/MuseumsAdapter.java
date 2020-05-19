package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.museum.Datas.Museum;
import com.example.museum.Datas.News;
import com.example.museum.MuseumActivity;
import com.example.museum.NewsActivity;
import com.example.museum.R;
import com.kaelli.niceratingbar.NiceRatingBar;
import com.kaelli.niceratingbar.OnRatingChangedListener;

import org.json.JSONObject;

import java.util.List;

import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;


/*
    museums的recyclerView组件的适配器定义：设置了列表中每一项的内容和格式
* */
//public class MuseumsAdapter extends RecyclerView.Adapter<MuseumsAdapter.ViewHolder> {
//
//    private List<Museum> museumList;
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        View museumview;
//        ImageView museumImage;
//        TextView museumName;
//        NiceRatingBar niceRatingBar;
//
//        public ViewHolder(View view) {
//            super(view);
//            museumview = view;
//            museumImage = (ImageView) view.findViewById(R.id.museum_image);
//            museumName = (TextView) view.findViewById(R.id.museum_name);
//            niceRatingBar = (NiceRatingBar) view.findViewById(R.id.show_RatingBar);
//        }
//    }
//    public MuseumsAdapter() {
//        museumList = Museum.getTestData();
//    }
//    public MuseumsAdapter(List<Museum> mMuseumList) {
//        museumList = mMuseumList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycleritem_museumsview, parent,
//                        false);
//        ViewHolder holder = new ViewHolder(view);
//        holder.museumview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Museum museum = museumList.get(position);
//                //在非activity类中使用startActicity只需写成getContext().startActivity(intent)即可
//                Intent intent = new Intent(parent.getContext(), MuseumActivity.class);
//                intent.putExtra("mid",museum.getMid());
//                intent.putExtra("name",museum.getName());
//                intent.putExtra("introduction",museum.getIntroduction());
//                intent.putExtra("avgstar",museum.getAvgstar());
//                intent.putExtra("opentime",museum.getOpentime());
//                intent.putExtra("address",museum.getAddress());
//                intent.putExtra("imgurl",museum.getImgurl());
//                intent.putExtra("mobile",museum.getMobile());
//                parent.getContext().startActivity(intent);
//            }
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Museum museum = museumList.get(position);
//        Glide.with(holder.itemView)
//                .load(museum.getImgurl())
//                .into(holder.museumImage);
//        holder.museumName.setText(museum.getName());
//        holder.niceRatingBar.setRating((float) museum.getAvgstar());             //显示博物馆的平均分数
//}
//
//    @Override
//    public int getItemCount() {
//        return museumList.size();
//    }
//
//}
public class MuseumsAdapter extends BaseRecyclerAdapter<Museum> {


    public MuseumsAdapter(List<Museum> data) {
        super(R.layout.recycleritem_museumsview, data);
    }

    @Override
    protected void bindView(BaseByViewHolder<Museum> holder,Museum bean, int position) {
//        holder.setText(R.id.view_bottom, bean);
        Museum museum = getData().get(position);

        if(museum.getImgurl().equals("") || museum.getImgurl()==null)
        {
            Glide.with(holder.itemView)
                    .load(R.drawable.pic_null)
                    .into((ImageView) holder.getView(R.id.museum_image));
        }
        else
        {
            Glide.with(holder.itemView)
                    .load(museum.getImgurl())
                    .error(R.drawable.news_picnull)
                    .into((ImageView) holder.getView(R.id.museum_image));

        }
        holder.setText(R.id.museum_name,museum.getName());
        NiceRatingBar ratingBar=(NiceRatingBar) holder.getView(R.id.show_RatingBar);
        ratingBar.setRating((float) museum.getAvgstar());         //显示博物馆的平均分数

    }
}
