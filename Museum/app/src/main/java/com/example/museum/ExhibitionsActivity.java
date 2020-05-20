package com.example.museum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.museum.API.API;
import com.example.museum.Adapter.ExhibitionAdapter;
import com.example.museum.Datas.Exhibition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.jingbin.library.ByRecyclerView;

/*
 * 搜索展览页面
 * */
public class ExhibitionsActivity extends AppCompatActivity {
    private ExhibitionAdapter adapter;
    private List<Exhibition> museumList = new ArrayList<>();
    private final Context currContext = this;
    private ByRecyclerView recyclerView;
    private Integer page = 0;
    private String key = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                TextView empty = findViewById(R.id.empty);
                empty.setVisibility(View.VISIBLE);
                empty.setText("暂无相关内容");
            }
            else if(msg.what == -1){
                TextView empty = findViewById(R.id.empty);
                empty.setVisibility(View.VISIBLE);
                empty.setText("网络错误，请稍后再试");
            }
            adapter.setNewData(museumList);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions);
        adapter= new ExhibitionAdapter(museumList);
        recyclerView = (ByRecyclerView) findViewById(R.id.recycler_exhibition);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //设置下拉刷新
        recyclerView.setOnRefreshListener(() -> {
            page=0;
            museumList.clear();
            adapter.notifyDataSetChanged();
            if(key.equals("")) {
                refreshMuseums(API.showAllExhibitions + "?page=0");
            }
            else{
                refreshMuseums(API.museumExhibitions + key  + "?page=0");
            }
        });
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnLoadMoreListener(() -> {
            if(key.equals("")) {
                refreshMuseums(API.showAllExhibitions + "?page=" + page.toString());
            }
            else{
                refreshMuseums(API.museumExhibitions + key + "?page=" + page.toString());
            }
//                recyclerView.loadMoreComplete();      // 加载更多完成
//                recyclerView.loadMoreEnd();           // 没有更多内容了
//                recyclerView.loadMoreFail();          // 加载更多失败(点击或再次上拉都会再次调用加载更多接口)
        }, 10);// delayMillis: 延迟多少毫秒调用接口

        refreshMuseums(API.showAllExhibitions+"?page=0");
        ActionBar actionbar = getSupportActionBar();
        // 隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
        SearchView searchView = (SearchView) findViewById(R.id.search_exhibition);
        searchView.setQueryHint("请输入展览名称");
        searchView.setIconified(false);        //展开搜索得内容
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.onActionViewExpanded();      //当展开无输入内容的时候，没有关闭的图标
        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                page = 0;
                museumList.clear();
                adapter.notifyDataSetChanged();
                refreshMuseums(API.findExhibitions+query+"?page=0");
                searchView.clearFocus();  //可以收起键盘
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                key = newText;
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
    private void dealRes(String str){
        museumList.clear();
        Message message = Message.obtain();
        if(str == null){
            message.what = -1;
            handler.sendMessage(message);
            return ;
        }
        try {
            JSONObject jsonObject=new JSONObject(str);
            JSONArray data = jsonObject.getJSONArray("content");
            JSONObject item;
            if(data.length()==0){
                message.what = 0;
            }
            else {
                for (int i = 0; i < data.length(); i++) {
                    item = data.getJSONObject(i);
                    museumList.add(new Exhibition(item.getInt("eid"), item.getString("name"), item.getString("imgurl"), item.getString("mname")));
                }
                message.what = 1;
            }
            handler.sendMessage(message);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    void refreshMuseums(String url){
        new Thread(()->{
            Message message = new Message();
            try{
                String jsonData = HttpRequest.Get(url);
                //考虑网络请求出错的情况
                if(jsonData==null)
                    message.what = -1;
                else
                {
                    JSONObject jsonObject=new JSONObject(jsonData);
                    int pagenum=jsonObject.getInt("totalPages");
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    if(Jarray.length() == 0){
                        message.what = 0;
                    }
                    JSONObject object;
                    for (int i = 0; i < Jarray.length(); i++) {
                        object = Jarray.getJSONObject(i);
                        museumList.add(new Exhibition(object.getInt("eid"), object.getString("name"), object.getString("imgurl"), object.getString("mname")));
                    }
                    if(page>=pagenum)
                        message.what=-2;
                    else
                    {
                        page++;
                        message.what = 1;
                    }
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();
    }
}
