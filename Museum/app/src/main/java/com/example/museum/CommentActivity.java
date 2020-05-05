package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;


import com.classichu.lineseditview.LinesEditView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/*
* 评论+打分页面
* */

public class CommentActivity extends AppCompatActivity {

    MaterialRatingBar materialRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        materialRatingBar = findViewById(R.id.ratingbar1);
        materialRatingBar.setActivated(true);

        materialRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                System.out.println(rating);
            }
        });
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
                System.out.println(linesEditView.getContentText()+materialRatingBar.getRating());
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
