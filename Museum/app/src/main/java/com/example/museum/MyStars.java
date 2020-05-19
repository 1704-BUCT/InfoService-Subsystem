package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.museum.Adapter.CusFragmentStarPage;
import com.example.museum.Components.MyViewPager;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 我的收藏页面
* */
public class MyStars extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>(Arrays.asList("展览 藏品 教育".split(" ")));
    private MyViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stars);
        mViewPager = findViewById(R.id.viewpager_starpage);
        for (String s : mTitle) {
            mFragments.add(CusFragmentStarPage.newInStance(s));
        }
        mViewPager.setAdapter(new MyStars.CusAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(4);     //好像是限制viewpager中fragment的个数
        rectFlow();
    }
    // 设置博物馆具体信息页面Tab布局的格式和内容（用来显示展览、藏品、教育活动、新闻、评论）
    private void rectFlow(){
        TabFlowLayout flowLayout = findViewById(R.id.rectflow_starpage);
        //设置tabbar的内容及格式（包括选中颜色和未选中颜色）
        flowLayout.setViewPager(mViewPager)
                .setTextId(R.id.item_text_starpage)
                .setSelectedColor(getResources().getColor(R.color.select))
                .setUnSelectedColor(getResources().getColor(R.color.unselect));
        flowLayout.setAdapter(new TabFlowAdapter<String>(R.layout.item_tab_instarpage,mTitle) {
            @Override
            public void bindView(View view, String data, int position) {
                setText(view,R.id.item_text_starpage,data);
            }
            @Override
            public void onItemClick(View view, String data, int position) {
                super.onItemClick(view, data, position);
                mViewPager.setCurrentItem(position);
            }
        });
        ImageView backToHome = findViewById(R.id.backtomypage);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class CusAdapter extends FragmentPagerAdapter {
        public CusAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
