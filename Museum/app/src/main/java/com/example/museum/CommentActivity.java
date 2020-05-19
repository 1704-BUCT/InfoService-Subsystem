package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.classichu.lineseditview.LinesEditView;
import com.example.museum.API.API;
import com.example.museum.Datas.Comment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.FormBody;

/*
* 评论+打分页面
* */

public class CommentActivity extends AppCompatActivity {

    MaterialRatingBar materialRatingBar1;
    MaterialRatingBar materialRatingBar2;
    MaterialRatingBar materialRatingBar3;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1)
            {
                Toast.makeText(CommentActivity.this, "评价成功",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(CommentActivity.this, "评价失败，请检查网络情况",Toast.LENGTH_SHORT).show();
//                Snackbar.make(materialRatingBar2, "评价失败，请检查网络情况", Snackbar.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        Integer mid=intent.getIntExtra("mid",0);
        String mname=intent.getStringExtra("mname");
        String imgurl=intent.getStringExtra("imgurl");
        materialRatingBar1 = findViewById(R.id.ratingbar1);
        materialRatingBar1.setActivated(true);
        materialRatingBar2 = findViewById(R.id.ratingbar2);
        materialRatingBar2.setActivated(true);
        materialRatingBar3 = findViewById(R.id.ratingbar3);
        materialRatingBar3.setActivated(true);
        ImageView imageView = findViewById(R.id.comment_pic);
        Glide.with(this).load(imgurl).into(imageView);
        TextView textView = findViewById(R.id.comment_mname);
        textView.setText(mname);
        ImageView backIcon = findViewById(R.id.backtomuseum);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinesEditView linesEditView = findViewById(R.id.input_comment);
        Button submitButton = findViewById(R.id.submit_comment);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(()->{
                    //获取当前时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                    Date date = new Date(System.currentTimeMillis());
//                    FormBody formBody = new FormBody.Builder().add("mid","3")
//                            .add("ic_account","333333")
//                            .add("content",linesEditView.getContentText())
//                            .add("environmentstar", String.valueOf(materialRatingBar1.getRating()))
//                            .add("exhibitionstar", String.valueOf(materialRatingBar2.getRating()))
//                            .add("servicestar", String.valueOf(materialRatingBar3.getRating()))
//                            .add("time",simpleDateFormat.format(date))
//                            .build();
                    JSONObject Jobject = new JSONObject();
                    try {
                        Jobject.put("uid",1);
                        Jobject.put("mid",mid);
                        Jobject.put("environmentstar",materialRatingBar1.getRating());
                        Jobject.put("exhibitionstar",materialRatingBar2.getRating());
                        Jobject.put("servicestar",materialRatingBar3.getRating());
                        Jobject.put("content",linesEditView.getContentText());
                        Jobject.put("time",simpleDateFormat.format(date).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    String json=Jobject.toString();
                     String state = HttpRequest.Post(API.uploadComment, json);
                     if(state==null)
                         message.what=0;
                     else
                     {
                         try {
                             JSONObject jsonObject=new JSONObject(state);
                             String status=jsonObject.getString("status");
                             if(status.equals("1"))
                                 message.what = 1;
                             else
                                 message.what=0;
                         } catch (JSONException e) {
                             message.what=0;
                             e.printStackTrace();
                         }
                     }
                handler.sendMessage(message);    // 将Message对象发送出去
                 }).start();

            }
        });
//        materialRatingBar.startAnimation(new RatingAnimation());
    }
    //注释部分让星星可以自己循环变化,暂时无用
//    private class RatingAnimation extends Animation {
//
//        public RatingAnimation() {
//            setDuration(materialRatingBar.getNumStars()
//                    * 4 * getResources().getInteger(android.R.integer.config_longAnimTime));
//            setInterpolator(new LinearInterpolator());
//            setRepeatCount(Animation.INFINITE);
//        }
//
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            int progress = Math.round(interpolatedTime * materialRatingBar.getMax());
//            materialRatingBar.setProgress(progress);
//
//        }
//
//        @Override
//        public boolean willChangeTransformationMatrix() {
//            return false;
//        }
//
//        @Override
//        public boolean willChangeBounds() {
//            return false;
//        }
//    }
}
