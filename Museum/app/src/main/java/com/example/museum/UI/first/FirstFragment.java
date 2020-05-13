package com.example.museum.UI.first;

import com.example.museum.API.API;
import com.example.museum.Adapter.BannerImageNetAdapter;
import com.example.museum.Adapter.NewsAdapter;
import com.example.museum.Datas.BannerData;
import com.example.museum.Datas.News;
import com.example.museum.HttpRequest;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import android.annotation.*;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.*;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;


/*
    首页精选页面：显示轮播图和新闻列表
    以下内容是对页面组件的设置以及新闻数据的获取
* */
public class FirstFragment extends Fragment implements OnPageChangeListener {
    private  RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private NewsAdapter adapter;
    private Banner banner;
    private List<News> bannarDatas;
    private TextView textView;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progressBar.setVisibility(View.GONE);
                //设置适配器
                banner.setAdapter(new BannerImageNetAdapter(bannarDatas));
                //设置点击事件
                banner.setOnBannerListener((data, position) -> {
                    //转到新闻页面，并传递存储新闻的数组下标给该页面
                    Intent intent = new Intent(getContext(), NewsActivity.class);
//                intent.putExtra("Index",position);
                    intent.putExtra("Url",bannarDatas.get(position).getUrl());
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter) ; //UI更改操作
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_firstpage, container, false);
        banner = root.findViewById(R.id.banner);
        bannarDatas = new ArrayList<>();
        //设置指示器
        banner.setIndicator(new CircleIndicator(getContext()));

        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));
        recyclerView = (RecyclerView)root.findViewById(R.id.recycler_firstview);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //为RecyclerView添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // 从服务器获取后台数据
        List<News> newsList = new ArrayList<>();
        new Thread(()->{
            Message message = new Message();
            try{
//                String jsonData = HttpRequest.Get("http://v.juhe.cn/toutiao/index?type=top&key=3f27f65b56ef05ccc3b25e576806f811");
                String jsonData = HttpRequest.Get(API.SHOW_ALLNews);
//                JSONObject Jobject = new JSONObject(jsonData);
//                JSONArray Jarray = Jobject.getJSONObject("result").getJSONArray("data");
                JSONArray Jarray = new JSONArray(jsonData);
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    if (i < 3)
                        bannarDatas.add(new News(object.getString("title"), object.getString("imgurl"), object.getString("url")));
                    else
//                    newsList.ic_add(new News(object.getString("title"), object.getString("thumbnail_pic_s"),object.getString("url")));
                        newsList.add(new News(object.getString("title"), object.getString("imgurl"), object.getString("url"), object.getString("author"), object.getString("releasetime"), object.getInt("nature")));
                }
                progressBar = root.findViewById(R.id.progressBar2);
                adapter = new NewsAdapter(newsList);
                message.what = 1;
                handler.sendMessage(message);    // 将Message对象发送出去
            }catch(JSONException e){
                progressBar = root.findViewById(R.id.progressBar2);
                textView=root.findViewById(R.id.news_error);
                message.what = 0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }}).start();
        return root;
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
