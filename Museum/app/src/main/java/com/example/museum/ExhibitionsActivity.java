package com.example.museum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.Adapter.ExhibitionAdapter;
import com.example.museum.Datas.Exhibition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
 * 搜索展览页面
 * */
public class ExhibitionsActivity extends AppCompatActivity {
//    private static final String TAG = "ExhibitionActivity";
    private ExhibitionAdapter adapter;
    private List<Exhibition> museumList = new ArrayList<>();
    private final Context currContext = this;
    private RecyclerView recyclerView;
//    private ProgressBar progressBar;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition);

        adapter= new ExhibitionAdapter(museumList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_exhibition);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
//                ExhibitionsActivity.this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpRequest.Get("http://api.tianapi.com/areanews/index?key=2fe201b4f3328820dd198843070bda77&areaname=" + "湖北");
                try {
                    JSONArray data = new JSONObject(str).getJSONArray("newslist");
                    JSONObject item;
                    for(int i=0;i<data.length();i++){
                        item = data.getJSONObject(i);
                        museumList.add(new Exhibition(i,item.getString("title"),item.getString("picUrl"),item.getString("source")));
                    }
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        ActionBar actionbar = getSupportActionBar();
        // 隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
        SearchView searchView = (SearchView) findViewById(R.id.search_exhibition);
        searchView.setIconified(false);         //展开搜索得内容
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.onActionViewExpanded();      //当展开无输入内容的时候，没有关闭的图标
        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                progressBar = findViewById(R.id.progress);
//                progressBar.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        String str = Http.Get("http://api.tianapi.com/areanews/index?key=2fe201b4f3328820dd198843070bda77&areaname=" + query);
                        String str = HttpRequest.Get("http://api.tianapi.com/areanews/index?key=2fe201b4f3328820dd198843070bda77&areaname=" + "河北");
                        try {
                            JSONArray data = new JSONObject(str).getJSONArray("newslist");
                            JSONObject item;
                            museumList.clear();
                            for(int i=0;i<data.length();i++){
                                item = data.getJSONObject(i);
                                museumList.add(new Exhibition(i,item.getString("title"),item.getString("picUrl"),item.getString("source")));
                            }
                            Message message = Message.obtain();
                            message.what = 1;
                            handler.sendMessage(message);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                //提交按钮的点击事件，这里应该是根据query即博物馆名称作为关键字进行查询
                //此处需要第五小组的api,需要把博物馆的信息存到museum对象中，再作为参数传递给博物馆具体信息页面
                searchView.clearFocus();  //可以收起键盘
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        //去掉搜索框的下划线
        View viewById1 = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null));
        if (viewById1 != null) {
            viewById1.setBackgroundColor(Color.TRANSPARENT);
        }
        //去掉提交箭头下面的下划线
        View viewById2 = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/submit_area", null, null));
        if (viewById2 != null) {
            viewById2.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
