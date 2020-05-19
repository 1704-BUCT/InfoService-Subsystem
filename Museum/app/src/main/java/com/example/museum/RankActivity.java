package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import me.jingbin.library.ByRecyclerView;

/*
* 排名页面
* */
public class RankActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;

//    private NewsAdapter_test_willbedeleted adapter;
    private RankMuseumsAdapter adapter;
    private ProgressBar progressBar;
    private ByRecyclerView recyclerView;
    private List<Museum> museumList=new ArrayList<>();
    private List<Museum> museumList1;
    private Integer page=0;
    private Integer selected=0;
    private TextView selectText;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            progressBar.setVisibility(View.GONE);
//            if (msg.what == 1) {
//                progressBar.setVisibility(View.GONE);
//                adapter.notifyDataSetChanged();
//            }

            if (msg.what == 1) {
                museumList1=new ArrayList<>(museumList);
                if(page==1)
                    recyclerView.setRefreshing(false);
                else
                    recyclerView.loadMoreComplete();      // 加载更多完成
                adapter.setNewData(museumList1);            // 设置及刷新数据
            }
            else if(msg.what==0)
            {
                TextView textView=findViewById(R.id.rank_error);
                //网络异常时
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            else
                recyclerView.loadMoreEnd();            // 没有更多内容了
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        selectText=findViewById(R.id.filter_selected);
        progressBar=findViewById(R.id.progressBar_rank);
        recyclerView = findViewById(R.id.recycler_rankpage);
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

//        String url = "http://v.juhe.cn/toutiao/index?type=top&key=3f27f65b56ef05ccc3b25e576806f811";
        getRankDatas(API.showMuseumSortByAvgstar+"?page=0");
        adapter = new RankMuseumsAdapter(museumList);
        recyclerView.setAdapter(adapter) ;
        //设置列表项的点击事件
        recyclerView.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Museum museum = adapter.getItemData(position);     // 通过adapter获取对应position的数据
                Intent intent = new Intent(RankActivity.this, MuseumActivity.class);
                intent.putExtra("mid",museum.getMid());
                intent.putExtra("name",museum.getName());
                intent.putExtra("introduction",museum.getIntroduction());
                intent.putExtra("avgstar",museum.getAvgstar());
                intent.putExtra("opentime",museum.getOpentime());
                intent.putExtra("address",museum.getAddress());
                intent.putExtra("imgurl",museum.getImgurl());
                intent.putExtra("mobile",museum.getMobile());
                startActivity(intent);
            }
        });
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                switch (selected)
                {
                    case 0:
                        getRankDatas(API.showMuseumSortByAvgstar+"?page="+page.toString());
                        break;
                    case 1:
                        getRankDatas(API.showMuseumSortByNum+"?page="+page.toString());
                        break;
                    case 2:
                        getRankDatas(API.showMuseumSortByAvgenvironstar+"?page="+page.toString());
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }

            }
        }, 10);// delayMillis: 延迟多少毫秒调用接口


        FilterHeadView filterHeadView = findViewById(R.id.filter);
        TextView textView0=findViewById(R.id.filter_zonghe);
        TextView textView1=findViewById(R.id.filter_cishu);
        TextView textView2=findViewById(R.id.filter_huanjing);
        TextView textView3=findViewById(R.id.filter_fuwu);
//        TextView textView0=findViewById(R.id.filter_zonghe);
        textView0.setTextColor(getResources().getColor(R.color.select));
        LinearLayout filter_item1=findViewById(R.id.zonghe);
        //按综合排序
        filter_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHeadView.hide();
                selectText.setText("综合");
                textView0.setTextColor(getResources().getColor(R.color.select));
                textView1.setTextColor(Color.GRAY);
                textView2.setTextColor(Color.GRAY);
                textView3.setTextColor(Color.GRAY);
//                textView0.setTextColor(getResources().getColor(R.color.select));
                page=0;
                getRankDatas(API.showMuseumSortByAvgstar+"?page=0");
            }
        });
        LinearLayout filter_item2=findViewById(R.id.cishu);
        //按展览排序
        filter_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHeadView.hide();
                selectText.setText("展览次数");
                page=0;
                textView1.setTextColor(getResources().getColor(R.color.select));
                textView0.setTextColor(Color.GRAY);
                textView2.setTextColor(Color.GRAY);
                textView3.setTextColor(Color.GRAY);
                getRankDatas(API.showMuseumSortByNum+"?page=0");
            }
        });
        LinearLayout filter_item3=findViewById(R.id.huanjing);
        //按展览排序
        filter_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterHeadView.hide();
                selectText.setText("环境");
                textView2.setTextColor(getResources().getColor(R.color.select));
                textView1.setTextColor(Color.GRAY);
                textView0.setTextColor(Color.GRAY);
                textView3.setTextColor(Color.GRAY);
                page=0;
                getRankDatas(API.showMuseumSortByAvgenvironstar+"?page=0");
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
        // 从服务器获取后台数据
        new Thread(()->{
            Message message = new Message();
            if(page==0)
            museumList.clear();
            try{
                String str = HttpRequest.Get(url);
                if(str==null)
                {
                    message.what=0;
                }
                else
                {
                    JSONObject jsonObject=new JSONObject(str);
                    int pagenum=jsonObject.getInt("totalPages");
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for(int i=0;i<Jarray.length();i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        museumList.add(new Museum(object.getInt("mid"),object.getString("imgurl"),object.getString("name"),
                                object.getDouble("avgenvironmentstar"),object.getDouble("avgexhibitionstar"),object.getDouble("avgservicestar"),
                                object.getString("address"),object.getDouble("avgstar"),
                                object.getString("introduction"),object.getString("opentime"),object.getString("mobile")));
                    }
                    if(page>=pagenum)
                        message.what=-1;
                    else
                    {
                        page++;
                        message.what = 1;
                    }
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            }catch(JSONException e){
                message.what=0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }}).start();
    }
}
