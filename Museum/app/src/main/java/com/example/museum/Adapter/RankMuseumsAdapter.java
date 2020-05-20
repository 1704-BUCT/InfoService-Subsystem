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
import java.util.List;

import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;


/*
    排名页面的museums的recyclerView组件的适配器定义：设置了列表中每一项的内容和格式
* */
public class RankMuseumsAdapter extends BaseRecyclerAdapter<Museum> {

    public RankMuseumsAdapter(List<Museum> data) {
        super(R.layout.recycleritem_inrankpage, data);
    }

    @Override
    protected void bindView(BaseByViewHolder<Museum> holder, Museum bean, int position) {

        Museum museum = getData().get(position);


        if(museum.getImgurl().equals("") || museum.getImgurl()==null)
        {
            Glide.with(holder.itemView)
                    .load(R.drawable.pic_null)
                    .transform(new RoundedCorners(20))
                    .into((ImageView) holder.getView(R.id.rank_museum_image));
        }
        else
        {
            Glide.with(holder.itemView)
                    .load(museum.getImgurl())
                    .error(R.drawable.pic_null)
                    .transform(new RoundedCorners(20))
                    .into((ImageView) holder.getView(R.id.rank_museum_image));
        }
        holder.setText(R.id.rank_museum_name,museum.getName());
        holder.setText( R.id.rank_stars,String.valueOf(museum.getAvgstar())+"分");
        if(position<3)
        {

            holder.setVisible(R.id.rank_id,false);
            if(position==0)
                holder.setImageResource(R.id.rank_idpic,R.drawable.no_1);
            if(position==1)
                holder.setImageResource(R.id.rank_idpic,R.drawable.no_2);
            if(position==2)
                holder.setImageResource(R.id.rank_idpic,R.drawable.no_3);
            holder.setVisible(R.id.rank_idpic,true);
        }
        else
        {
            holder.setVisible(R.id.rank_id,true);
            holder.setVisible(R.id.rank_idpic,false);
            holder.setText(R.id.rank_id,String.valueOf(position+1));
        }


    }
}
//public class RankMuseumsAdapter extends RecyclerView.Adapter<RankMuseumsAdapter.ViewHolder> {
//
//    private List<Museum> museumList;
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        View museumview;
//        ImageView museumImage;
//        TextView museumName;
//        TextView museumStar;
//        TextView rankid;
//        ImageView rankidpic;
//        public ViewHolder(View view) {
//            super(view);
//            museumview = view;
//            museumImage = (ImageView) view.findViewById(R.id.rank_museum_image);
//            museumName = (TextView) view.findViewById(R.id.rank_museum_name);
//            museumStar = (TextView)view.findViewById(R.id.rank_stars);
//            rankid = view.findViewById(R.id.rank_id);
//            rankidpic =  (ImageView)view.findViewById(R.id.rank_idpic);
//        }
//    }
//    public RankMuseumsAdapter() {
//        museumList = Museum.getTestData();
//    }
//    public RankMuseumsAdapter(List<Museum> mMuseumList) {
//        museumList = mMuseumList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycleritem_inrankpage, parent,
//                        false);
//        ViewHolder holder = new ViewHolder(view);
//        holder.museumview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Museum museum = museumList.get(position);
//                //在非activity类中使用startActicity只需写成getContext().startActivity(intent)即可
//                Intent intent = new Intent(parent.getContext(), MuseumActivity.class);
////                intent.putExtra("Url",museumList.get(position).getUrl());
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
//        holder.museumStar.setText( String.valueOf(museum.getAvgstar())+"分");
//        if(position<3)
//        {
//            holder.rankid.setVisibility(View.INVISIBLE);
//            if(position==0)
//                holder.rankidpic.setImageResource(R.drawable.no_1);
//            if(position==1)
//                holder.rankidpic.setImageResource(R.drawable.no_2);
//            if(position==2)
//                holder.rankidpic.setImageResource(R.drawable.no_3);
//            holder.rankidpic.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            holder.rankid.setVisibility(View.VISIBLE);
//            holder.rankidpic.setVisibility(View.INVISIBLE);
//            holder.rankid.setText(String.valueOf(position+1));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return museumList.size();
//    }
//
//}
