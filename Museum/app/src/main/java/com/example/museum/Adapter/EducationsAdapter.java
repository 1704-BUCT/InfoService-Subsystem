package com.example.museum.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.EducationActivity;
import com.example.museum.R;

import java.util.List;

/*
* 博物馆信息页面教育recycleView的适配器定义
* */
public class EducationsAdapter  extends RecyclerView.Adapter<EducationsAdapter .ViewHolder> {

    private List<EducationActivity> educationActivities;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View educationView;
        ImageView educaitonImage;
        TextView educationName;
        TextView educationTime;
        public ViewHolder(View view) {
            super(view);
            educationView=view;
            educaitonImage = (ImageView) view.findViewById(R.id.educations_image);
            educationName = (TextView) view.findViewById(R.id.educations_name);
            educationTime = (TextView) view.findViewById(R.id.educations_time);
        }
    }
    //测试数据
    public EducationsAdapter() {
        educationActivities = EducationActivity.getTestDatas();
    }
    public EducationsAdapter(List<EducationActivity> educationsActivities) {
        educationActivities = educationsActivities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_educations, parent,
                        false);
        final ViewHolder holder = new ViewHolder(view);
        holder.educationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                EducationActivity education = educationActivities.get(position);

                //在非activity类中使用startActicity只需写成getContext().startActivity(intent)即可
                Intent intent = new Intent(parent.getContext(), com.example.museum.EducationActivity.class);
                intent.putExtra("id",education.getId());
//                intent.putExtra("Url",mNewsList.get(position).getUrl());
                parent.getContext().startActivity(intent);
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EducationActivity educations = educationActivities.get(position);
        Glide.with(holder.itemView)
                .load(educations.getImgurl())
                .error(R.drawable.pic_null)
                .into(holder.educaitonImage);
        holder.educationName.setText(educations.getName());
        holder.educationTime.setText(educations.getTime());
    }

    @Override
    public int getItemCount() {
        return educationActivities.size();
    }

}
