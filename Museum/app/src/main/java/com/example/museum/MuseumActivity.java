package com.example.museum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.museum.Adapter.CusFragment;
import com.example.museum.Components.MyViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
* 博物馆详细信息页面
* */
public class MuseumActivity extends AppCompatActivity {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>(Arrays.asList("展览 藏品 教育活动 新闻 评论".split(" ")));
    private MyViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        //显示博物馆图片
        ImageView imageView = (ImageView)findViewById(R.id.Appbar_imageview);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_bar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // 给ToolBar上的返回键添加销毁活动的动作
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setTitle("故宫博物馆");
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark1));
        Glide.with(this).load(R.drawable.museum_test).into(imageView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        //显示博物馆的具体介绍（用可折叠的文本框）
        ExpandableTextView museumdetails = (ExpandableTextView)findViewById(R.id.expand_text_view);
        museumdetails.setText("北京故宫博物院建立于1925年10月10日，位于北京故宫紫禁城内。是在明朝、清朝两代皇宫机器收藏的基础上建立起来的中国性综合博物馆，也是中国最大的古代文化艺术博物馆，其文物收藏主要来源于清代宫中旧藏，是第一批全国爱国主义教育示范基地。从2014年1月1日起，北京故宫博物院几乎每周一闭馆。\n" +
                "北京故宫博物院位于北京故宫即紫禁城内。北京故宫是第一批全国重点文物保护单位、第一批国家5A级旅游景区、全国未成年人道德建设思想工作先进单位，1987年入选《世界文化遗产名录》。\n" +
                "2018年10月，故宫博物院发布首款主体功能游戏和首张古画主题音乐专辑，拉开“智慧故宫”序幕。\n");

        mViewPager = findViewById(R.id.viewpager);
        for (String s : mTitle) {
            mFragments.add(CusFragment.newInStance(s));
        }
        mViewPager.setAdapter(new CusAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(5);     //好像是限制viewpager中fragment的个数
        rectFlow();
        FloatingActionButton floatingActionButton = findViewById(R.id.AddComments);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MuseumActivity.this,CommentActivity.class);
                intent.putExtra("ic_name","故宫博物馆");
                startActivity(intent);
            }
        });

    }

    // 设置博物馆具体信息页面Tab布局的格式和内容（用来显示展览、藏品、教育活动、新闻、评论）
    private void rectFlow(){
        TabFlowLayout flowLayout = findViewById(R.id.rectflow);
        //设置tabbar的内容及格式（包括选中颜色和未选中颜色）
        flowLayout.setViewPager(mViewPager)
                .setTextId(R.id.item_text)
                .setSelectedColor(getResources().getColor(R.color.select))
                .setUnSelectedColor(getResources().getColor(R.color.unselect));
        flowLayout.setAdapter(new TabFlowAdapter<String>(R.layout.item_tab_inmuseum,mTitle) {
            @Override
            public void bindView(View view, String data, int position) {
                setText(view,R.id.item_text,data);
            }
            @Override
            public void onItemClick(View view, String data, int position) {
                super.onItemClick(view, data, position);
                mViewPager.setCurrentItem(position);
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
