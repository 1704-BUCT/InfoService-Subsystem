package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.museum.API.API;
import com.example.museum.Adapter.RankMuseumsAdapter;
import com.example.museum.Components.FilterHeadView;
import com.example.museum.Datas.Museum;
import com.example.museum.Datas.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
* 排名页面
* */
public class RankActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;

//    private NewsAdapter_test_willbedeleted adapter;
    private RankMuseumsAdapter adapter;
    private ProgressBar progressBar;
    private List<Museum> museumList=new ArrayList<>();
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
            if (msg.what == 1) {
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        progressBar=findViewById(R.id.progressBar_rank);
        RecyclerView recyclerView = findViewById(R.id.recycler_rankpage);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

//        String url = "http://v.juhe.cn/toutiao/index?type=top&key=3f27f65b56ef05ccc3b25e576806f811";
        getRankDatas(API.showMuseumSortByAvgstar);
        adapter = new RankMuseumsAdapter(museumList);
        recyclerView.setAdapter(adapter) ;
        FilterHeadView filterHeadView = findViewById(R.id.filter);
        LinearLayout filter_item1=findViewById(R.id.zonghe);
        //按综合排序
        filter_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHeadView.hide();
                getRankDatas(API.showMuseumSortByAvgstar);
            }
        });
        LinearLayout filter_item2=findViewById(R.id.cishu);
        //按展览排序
        filter_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHeadView.hide();
                getRankDatas(API.showMuseumSortByNum);
            }
        });
        //点击上方返回键返回到上个页面
        ImageView backToHome = findViewById(R.id.backtofinding4);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getRankDatas(String url)
    {

        //         从服务器获取后台数据
        new Thread(()->{
            museumList.clear();
            try{
                String str = HttpRequest.Get(url);
                JSONArray Jarray = new JSONArray(str);
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    museumList.add(new Museum(object.getInt("mid"),object.getString("imgurl"),object.getString("name"),
                            object.getDouble("avgenvironmentstar"),object.getDouble("avgexhibitionstar"),object.getDouble("avgservicestar"),
                            object.getString("address"),object.getDouble("avgstar"),
                            object.getString("introduction"),object.getString("opentime"),object.getString("mobile")));
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);    // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();
    }
}
