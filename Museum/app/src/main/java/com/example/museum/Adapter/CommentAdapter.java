package com.example.museum.Adapter;

import com.bumptech.glide.Glide;
import com.example.museum.Datas.Comment;
import com.example.museum.Datas.News;
import com.example.museum.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import me.jingbin.library.adapter.BaseByViewHolder;
import me.jingbin.library.adapter.BaseRecyclerAdapter;

public class CommentAdapter extends BaseRecyclerAdapter<Comment> {

    public CommentAdapter(List<Comment> data) {
        super(R.layout.recycleritem_comment, data);
    }

    @Override
    protected void bindView(BaseByViewHolder<Comment> holder, Comment bean, int position) {
//        holder.setText(R.id.view_bottom, bean);
        Comment comment = getData().get(position);
        List<Integer> pics=new ArrayList<>();
        pics.add(R.drawable.pic_null1);
        pics.add(R.drawable.pic_null2);
        pics.add(R.drawable.pic_null3);
        pics.add(R.drawable.pic_null4);
        pics.add(R.drawable.pic_null5);
        pics.add(R.drawable.pic_null6);
        Random ran = new Random();
        Integer index=ran.nextInt(6);
        if(comment.getAvatarurl().equals("") || comment.getAvatarurl()==null)
        {
            Glide.with(holder.itemView)
                    .load(pics.get(index))
                    .into((CircleImageView) holder.getView(R.id.avatar_comment));
        }
        else
        {
            Glide.with(holder.itemView)
                    .load(comment.getAvatarurl())
                    .error(pics.get(index))
                    .into((CircleImageView) holder.getView(R.id.avatar_comment));
        }
        holder.setText(R.id.user_name_comment,"用户"+comment.getUid().toString());
        if(comment.getContent()==null||comment.getContent().equals(""))
            holder.setText(R.id.comment_content,"无评价内容");
        else
            holder.setText(R.id.comment_content,comment.getContent());
        holder.setText(R.id.comment_time,comment.getTime());
//        holder.setText(R.id.news_title,news.getName());
//        holder.setText(R.id.news_author,news.getAuthor());
//        holder.setText(R.id.news_releasetime,news.getReleasetime().toString());

    }
}