package com.example.museum.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Museum;
import com.example.museum.R;
import com.kaelli.niceratingbar.NiceRatingBar;
import com.kaelli.niceratingbar.OnRatingChangedListener;

import java.util.List;



public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> {

    private List<Museum> museumList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView museumImage;
        TextView museumName;
        NiceRatingBar niceRatingBar;

        public ViewHolder(View view) {
            super(view);
            museumImage = (ImageView) view.findViewById(R.id.museum_image);
            museumName = (TextView) view.findViewById(R.id.museum_name);
            niceRatingBar = (NiceRatingBar) view.findViewById(R.id.show_RatingBar);
        }
    }

    public MuseumAdapter() {
        museumList = Museum.getTestData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_museumsview, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        holder.niceRatingBar.setRating(3.5f);
        holder.niceRatingBar.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(float rating) {
//                Integer rate= (int) rating;
//                Toast.makeText(parent.getContext(),rate.toString(),Toast.LENGTH_SHORT).show();
                // 在这里，得到的rating值就是用户通过点击/滑动，给的评分了，可以把这个值传递到服务端
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Museum museum = museumList.get(position);
        Glide.with(holder.itemView)
                .load(museum.getImgurl())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.museumImage);

//        holder.newsImage.setImageResource(.getImageId());
        holder.museumName.setText(museum.getName());
    }

    @Override
    public int getItemCount() {
        return museumList.size();
    }

}
