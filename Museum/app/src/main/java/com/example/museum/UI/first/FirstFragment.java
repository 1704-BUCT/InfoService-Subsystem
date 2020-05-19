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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    private ProgressBar progressBar;
    private NewsAdapter adapter;
    private List<News> bannarDatas;
    private TextView textView;
    private List<News> newsList;
    private BannerImageNetAdapter adapterBanner;
    private SwipeRefreshLayout swipeRefresh;
    private Integer page=0;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                adapterBanner.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
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
        Banner banner = root.findViewById(R.id.banner);
        bannarDatas = new ArrayList<>();
        newsList = new ArrayList<>();
        //设置指示器
        banner.setIndicator(new CircleIndicator(banner.getContext()));
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));
        //设置适配器

        adapterBanner=new BannerImageNetAdapter(bannarDatas);
        banner.setAdapter(adapterBanner);


        //设置点击事件
        banner.setOnBannerListener((data, position) -> {
            //转到新闻页面，并传递存储新闻的数组下标给该页面
            Intent intent = new Intent(getContext(), NewsActivity.class);
            intent.putExtra("Url",newsList.get(position).getUrl());
            startActivity(intent);
        });

        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycler_firstview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //为RecyclerView添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        swipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.
                OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews(API.showAllNews);
            }
        });
        progressBar = root.findViewById(R.id.progressBar2);
        adapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(adapter);
        textView=root.findViewById(R.id.news_error);
        refreshNews(API.showAllNews);
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
    // 从服务器获取后台数据
    void refreshNews(String url){

        new Thread(()->{
            if(bannarDatas.size()>=3)
            {
                newsList.add(0,bannarDatas.get(2));
                newsList.add(0,bannarDatas.get(1));
                newsList.add(0,bannarDatas.get(0));
                bannarDatas.clear();
            }

            Message message = new Message();
            try{
                String jsonData = HttpRequest.Get(API.showAllNews+"?page="+page.toString());
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);
//                    if (i < 3 )
//                        bannarDatas.add(new News(object.getString("title"), object.getString("imgurl"), object.getString("url")));
//                    else
                        newsList.add(0,new News(object.getString("title"), object.getString("imgurl"), object.getString("url"), object.getString("author"), object.getString("releasetime"), object.getInt("nature")));
                }
                bannarDatas.add(newsList.get(2));
                bannarDatas.add(newsList.get(1));
                bannarDatas.add(newsList.get(0));
                newsList.remove(0);
                newsList.remove(0);
                newsList.remove(0);
                page++;
                message.what = 1;
                handler.sendMessage(message);    // 将Message对象发送出去
            }catch(JSONException e){
                message.what = 0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }}).start();
    }
}
