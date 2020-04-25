package com.example.museum.ui.first;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.museum.Adapter.ImageNetAdapter;
import com.example.museum.Adapter.NewsAdapter;
import com.example.museum.Datas.DataBean;

import com.example.museum.Datas.News;
import com.example.museum.NewsActivity;
import com.example.museum.R;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment implements OnPageChangeListener {
    private  RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private NewsAdapter adapter;
    //防止在子线程中更新UI时程序会崩溃
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter) ; //UI更改操作
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_firstpage, container, false);
        final TextView textView = root.findViewById(R.id.text_firstTitle);
        textView.setText("精选");


        final Banner banner = root.findViewById(R.id.banner);
        //设置适配器
        banner.setAdapter(new ImageNetAdapter(DataBean.getTestData()));
        //设置指示器
        banner.setIndicator(new CircleIndicator(getContext()));
        //设置点击事件

        banner.setOnBannerListener((data, position) -> {
                //转到新闻页面，并传递存储新闻的数组下标给该页面
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("Index",position);
                startActivity(intent);
        });
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));
        recyclerView = (RecyclerView)root.findViewById(R.id.recycler_firstview);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        List<News> newsList = new ArrayList<>();
        new Thread(()->{
            String url = "http://v.juhe.cn/toutiao/index?type=top&key=3f27f65b56ef05ccc3b25e576806f811";
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);
            try{
                Response response = call.execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                JSONArray Jarray = Jobject.getJSONObject("result").getJSONArray("data");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    newsList.add(new News(object.getString("title"), object.getString("thumbnail_pic_s"),object.getString("url")));
                }
                progressBar=root.findViewById(R.id.progressBar2);
                adapter = new NewsAdapter(newsList);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(IOException | JSONException e){
                e.printStackTrace();
            }}).start();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        
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
