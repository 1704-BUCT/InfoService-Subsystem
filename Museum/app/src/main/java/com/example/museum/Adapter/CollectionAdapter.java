package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Collection;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import java.util.List;

import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;

/*
 * 搜索页面藏品recycleView的适配器定义
 * */
public class CollectionAdapter extends BaseRecyclerAdapter<Collection> {

    private List<Collection> antiqueList;

    public CollectionAdapter(List<Collection> data) {
        super(R.layout.recycleritem_exhibitionview,data);
        antiqueList = data;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycleritem_exhibitionview, parent,
//                        false);
//        ViewHolder holder = new ViewHolder(view);
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                Collection antique = antiqueList.get(position);
//                Intent intent = new Intent(parent.getContext(), NewsActivity.class);
//                intent.putExtra("Url",antique.getImgurl());
//                parent.getContext().startActivity(intent);
//            }
//        });
//        return holder;
//    }

    @Override
    protected void bindView(BaseByViewHolder<Collection> holder, Collection bean, int position) {
        Collection antique = antiqueList.get(position);
        Glide.with(holder.itemView)
                .load(antique.getImgurl())
                .error(R.drawable.pic_null)
                .into((ImageView) holder.getView(R.id.exhibition_image));
        holder.setText(R.id.exhibition_name,antique.getName());
        holder.setText(R.id.museum_name,antique.getMname());
    }


    @Override
    public int getItemCount() {
        return antiqueList.size();
    }
}
