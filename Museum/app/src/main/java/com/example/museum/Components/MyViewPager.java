package com.example.museum.Components;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/*
    解决原有viewpager不能自适应高度的问题
* */
//public class MyViewPager extends ViewPager {
//
//    public MyViewPager(@NonNull Context context) {
//        super(context);
//    }
//    public MyViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int viewHeight = 0;
//        View childView = getChildAt(getCurrentItem());
//        if (childView != null) {
//            childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            viewHeight = childView.getMeasuredHeight();
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//}

/*
    自定义的viewpager，解决不能自适应高度的问题
  */
public class MyViewPager extends ViewPager
{       public MyViewPager(Context context) { super(context); }
        public MyViewPager(Context context, AttributeSet attrs)       { super(context, attrs); }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {    int height = 0;
        for (int i = 0; i < getChildCount(); i++)
        {   View child = getChildAt(i);
           child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
           int h = child.getMeasuredHeight(); if (h > height) height = h;
        }
    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
}
}