package com.example.museum.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.API.API;
import com.example.museum.Datas.News;
import com.example.museum.Datas.TrafficRule;
import com.example.museum.HttpRequest;
import com.example.museum.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
    设置我的收藏页面的viewpager内fragment的内容
* */
public class CusFragmentStarPage extends Fragment {

    public static final String ARGUMENT_KEY = "tab_title";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private TrafficRuleAdapter adapter1;
    private NewsAdapter adapter2;
    private EducationsAdapter adapter3;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            switch (msg.what)
            {
                case 1:
                    recyclerView.setAdapter(adapter1) ; //收藏的展览的适配器
                    break;
                case 2:
                    recyclerView.setAdapter(adapter2) ; //收藏的藏品的适配器
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    Snackbar.make(recyclerView, "部分数据加载失败，请检查网络情况", Snackbar.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.recyclerview_inmuseum,container,
                        false);
        TextView textView =view.findViewById(R.id.item_tab1) ;
        if (bundle != null){
            textView.setText(bundle.getString(ARGUMENT_KEY));
            textView.setVisibility(View.GONE);
        }else{
            textView.setText("nothing here");
        }

        if (bundle.getString(ARGUMENT_KEY).equals("展览"))
        {
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
            progressBar=view.findViewById(R.id.progressBarinMuseum);
            getTraffic("http://v.juhe.cn/jztk/query?subject=1&model=c1&testType=rand&=&key=d1d92112120c130e989b1f0b27f79c4f");
        }
        if(bundle.getString(ARGUMENT_KEY).equals("新闻"))
        {
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
            progressBar=view.findViewById(R.id.progressBarinMuseum);
            getNews(API.showAllNews);
        }
        if(bundle.getString(ARGUMENT_KEY).equals("教育"))
        {
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
            progressBar=view.findViewById(R.id.progressBarinMuseum);
            progressBar.setVisibility(View.GONE);
            adapter3 = new EducationsAdapter();
            recyclerView.setAdapter(adapter3) ;
        }
        return view;
    }

    public static CusFragmentStarPage newInStance(String key) {
        CusFragmentStarPage fragment = new CusFragmentStarPage();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY,key);
        fragment.setArguments(bundle);
        return fragment;
    }

    // 从服务器上获取测试数据
    private void getTraffic(String url)
    {
        List<TrafficRule> trafficList = new ArrayList<>();
        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
                Message message = new Message();
                if(jsonData==null)
                    message.what=0;
                else {
                    JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = Jobject.getJSONArray("result");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        trafficList.add(new TrafficRule(object.getString("id"), object.getString("question"), object.getString("answer"), object.getString("explains"), object.getString("url")));
                    }
                    adapter1 = new TrafficRuleAdapter(trafficList);
                    message.what = 1;
                }
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
    // 从服务器上获取测试数据
    private void getNews(String url)
    {

        List<News> newsList = new ArrayList<>();
        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
//                JSONObject Jobject = new JSONObject(jsonData);
//                JSONArray Jarray = Jobject.getJSONArray("newslist");
                JSONArray Jarray = new JSONArray(jsonData);
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    newsList.add(new News(object.getString("title"), object.getString("imgurl"),object.getString("url"),object.getString("author"), object.getString("releasetime"),object.getInt("nature")));
                }
                adapter2 = new NewsAdapter(newsList);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
}
