package com.example.museum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.museum.API.API;
import com.example.museum.Adapter.CusFragment;
import com.example.museum.Components.MyViewPager;
import com.example.museum.Datas.Users;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kaelli.niceratingbar.NiceRatingBar;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;
import com.zhengsr.tablib.view.flow.TabFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

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
    private  Intent intent2;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1)
                startActivity(intent2);
            else
//                Snackbar.make(materialRatingBar1, "评价成功", Snackbar.LENGTH_SHORT).show();
                Toast.makeText(MuseumActivity.this, "您已经评论过，不可以再进行评论",Toast.LENGTH_SHORT).show();
//                Snackbar.make(materialRatingBar2, "评价失败，请检查网络情况", Snackbar.LENGTH_SHORT).show();
        }
    };
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
        Intent intent=getIntent();
        Integer mid=intent.getIntExtra("mid",0);
        String name=intent.getStringExtra("name");
        String introduction=intent.getStringExtra("introduction");
        Double avgstar=intent.getDoubleExtra("avgstar",3.5);
        String opentime=intent.getStringExtra("opentime");
        String address=intent.getStringExtra("address");
        String imgurl=intent.getStringExtra("imgurl");
        String mobile=intent.getStringExtra("mobile");
        TextView time = findViewById(R.id.museum_time);
        TextView maddress=findViewById(R.id.museum_location);
        TextView mtitle=findViewById(R.id.museum_title);
        TextView mmobile=findViewById(R.id.museum_mobile);
        if(opentime.equals("")||opentime==null)
            time.setText("暂无时间信息");
        else
            time.setText(opentime.replaceAll("\\s*", ""));
        if(address.equals("")||address==null)
            maddress.setText("暂无地址信息");
        else
            maddress.setText(address.replaceAll("\\s*", ""));

        if(mobile.equals("")||mobile==null)
            mmobile.setText("暂无联系方式");
        else
            mmobile.setText(mobile.replaceAll("\\s*", ""));
        intent2 = new Intent(MuseumActivity.this,CommentActivity.class);
        intent2.putExtra("mname",name);
        intent2.putExtra("imgurl",imgurl);
        intent2.putExtra("mid",mid);
        mtitle.setText(name.replaceAll("\\s*", ""));
        collapsingToolbarLayout.setTitle(name.replaceAll("\\s*", ""));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark1));
        Glide.with(this).load(imgurl).into(imageView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        //显示博物馆的具体介绍（用可折叠的文本框）
        ExpandableTextView museumdetails = (ExpandableTextView)findViewById(R.id.expand_text_view);
//        museumdetails.setText("北京故宫博物院建立于1925年10月10日，位于北京故宫紫禁城内。是在明朝、清朝两代皇宫机器收藏的基础上建立起来的中国性综合博物馆，也是中国最大的古代文化艺术博物馆，其文物收藏主要来源于清代宫中旧藏，是第一批全国爱国主义教育示范基地。从2014年1月1日起，北京故宫博物院几乎每周一闭馆。\n" +
//                "北京故宫博物院位于北京故宫即紫禁城内。北京故宫是第一批全国重点文物保护单位、第一批国家5A级旅游景区、全国未成年人道德建设思想工作先进单位，1987年入选《世界文化遗产名录》。\n" +
//                "2018年10月，故宫博物院发布首款主体功能游戏和首张古画主题音乐专辑，拉开“智慧故宫”序幕。\n");
        museumdetails.setText(introduction.replaceAll("\\s*", ""));
        NiceRatingBar museumRating = (NiceRatingBar)findViewById(R.id.show_RatingBar);
        museumRating.setRating(Float.parseFloat(avgstar.toString()));
        mViewPager = findViewById(R.id.viewpager);

        for (String s : mTitle) {
            mFragments.add(CusFragment.newInStance(s,name.replaceAll("\\s*", "")));
        }
        mViewPager.setAdapter(new CusAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(5);     //好像是限制viewpager中fragment的个数
        rectFlow();
        FloatingActionButton floatingActionButton = findViewById(R.id.AddComments);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Users.isLogin==0)
                {
                    Snackbar.make(view, "请先进行登陆", Snackbar.LENGTH_SHORT)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MuseumActivity.this, "Data restored",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
                else
                {
                    new Thread(()->{
                        String data=HttpRequest.Get(API.isAlreadyComment+"1"+"/"+mid.toString());
                        Message message = new Message();
                        if(data.equals("0"))
                            message.what =1;
                        else
                            message.what= 0;
                        handler.sendMessage(message);    // 将Message对象发送出去
                    }).start();

                }

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
