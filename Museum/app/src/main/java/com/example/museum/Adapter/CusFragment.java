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


import com.example.museum.Datas.TrafficRule;
import com.example.museum.R;

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

// 设置博物馆详细信息页面的viewpager内fragment的内容
public class CusFragment extends Fragment {

    public static final String ARGUMENT_KEY = "argument1";
    public static final String ARGUMENT_INFO = "argument2";
    private String mTitle;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private TrafficRuleAdapter adapter;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter) ; //UI更改操作
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

        }else{
            textView.setText("nothing here");
        }
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        if (bundle.getString(ARGUMENT_KEY).equals("展览"))
        {
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
            progressBar=view.findViewById(R.id.progressBarinMuseum);
            getTraffic();
        }
        return view;
    }

    /**
     * 弄一个静态工厂的方法调用 用于传参
     * @param key
     * @return
     */
    public static CusFragment newInStance(String key) {
        CusFragment fragment = new CusFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY,key);
        fragment.setArguments(bundle);
        return fragment;
    }

    // 从服务器上获取测试数据
    private void getTraffic()
    {

        List<TrafficRule> trafficList = new ArrayList<>();
        new Thread(()->{
            String url = "http://v.juhe.cn/jztk/query?subject=1&model=c1&testType=rand&=&key=d1d92112120c130e989b1f0b27f79c4f";
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);
            try{
                Response response = call.execute();
                String jsonData = response.body().string();
                JSONObject Jobject = new JSONObject(jsonData);
                JSONArray Jarray = Jobject.getJSONArray("result");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    trafficList.add(new TrafficRule(object.getString("id"), object.getString("question"),object.getString("answer"),object.getString("explains"),object.getString("url")));
                }
                adapter = new TrafficRuleAdapter(trafficList);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(IOException | JSONException e){
                e.printStackTrace();
            }}).start();

    }
}
