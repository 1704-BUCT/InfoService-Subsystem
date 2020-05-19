package com.example.museum.UI.first;

import com.example.museum.API.API;
import com.example.museum.Adapter.BannerImageNetAdapter;
import com.example.museum.Adapter.NewsAdapter;
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

import com.youth.banner.*;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.SpacesItemDecoration;
import okhttp3.*;


/*
    首页精选页面：显示轮播图和新闻列表
    以下内容是对页面组件的设置以及新闻数据的获取
* */
public class FirstFragment extends Fragment implements OnPageChangeListener {

    private  ByRecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;
    private List<News> bannarDatas;
    private List<News> bannarDatas1;
    private List<News> newsList=new ArrayList<>();
    private List<News> newsList1=new ArrayList<>();
    private NewsAdapter adapter=new NewsAdapter(newsList);
    private BannerImageNetAdapter adapterBanner;
    private Integer page=0;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                if(page==1)
                    recyclerView.setRefreshing(false);
                else
                    recyclerView.loadMoreComplete();      // 加载更多完成
                progressBar.setVisibility(View.GONE);
                //此处用了两个List的副本，来防止在网络数据时用户点击列表项闪退的问题
                bannarDatas1=new ArrayList<>(bannarDatas);
                adapter.setNewData(bannarDatas1);
                newsList1=new ArrayList<>(newsList);
                adapter.setNewData(newsList1);            // 设置及刷新数据
            }
            else if(msg.what==0)
            {
                //网络异常时
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            else
                recyclerView.loadMoreEnd();            // 没有更多内容了
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_firstpage, container, false);
        Banner banner = root.findViewById(R.id.banner);
        bannarDatas = new ArrayList<>();
        //设置指示器
        banner.setIndicator(new CircleIndicator(banner.getContext()));
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));
        //设置适配器
        adapterBanner=new BannerImageNetAdapter(bannarDatas);
        banner.setAdapter(adapterBanner);
        //设置轮播图点击事件
        banner.setOnBannerListener((data, position) -> {
            //转到新闻页面，并传递存储新闻的数组下标给该页面
            Intent intent = new Intent(getContext(), NewsActivity.class);
            intent.putExtra("Url",newsList.get(position).getUrl());
            startActivity(intent);
        });
        recyclerView = (ByRecyclerView)root.findViewById(R.id.recycler_firstview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //为RecyclerView添加分割线
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getContext(), SpacesItemDecoration.VERTICAL)
                .setNoShowDivider(1, 1)  // 第一个参数：头部不显示分割线的个数，第二个参数：尾部不显示分割线的个数，默认为1
                .setDrawable(R.drawable.divider);// 设置drawable文件
        recyclerView.addItemDecoration(itemDecoration);
        progressBar = root.findViewById(R.id.progressBar2);
        refreshNews(API.showAllNews+"?page="+page.toString());
        recyclerView.setAdapter(adapter);
        textView=root.findViewById(R.id.news_error);

        //设置列表项的点击事件
        recyclerView.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                News news = adapter.getItemData(position);     // 通过adapter获取对应position的数据
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("Url",news.getUrl());
                startActivity(intent);
            }
        });
        //设置下拉刷新
        recyclerView.setOnRefreshListener(new ByRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                refreshNews(API.showAllNews+"?page=0");
            }
        });
//        // 需要在设置监听后设置，设置监听会开启自动刷新
//        recyclerView.setRefreshEnabled(true);
//        recyclerView.setRefreshing(true);  // 手动启动刷新
//        recyclerView.setRefreshing(true); // 取消刷新重置参数，包括加载更多的参数
        // 想要使用加载更多，必须设置监听或将加载更多开关打开。
        // 只打开开关不设置加载更多监听，多出现在只想在列表最后设置`没有更多数据了`的布局。
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                refreshNews(API.showAllNews+"?page="+page.toString());
//                recyclerView.loadMoreComplete();      // 加载更多完成
//                recyclerView.loadMoreEnd();           // 没有更多内容了
//                recyclerView.loadMoreFail();          // 加载更多失败(点击或再次上拉都会再次调用加载更多接口)
            }
        }, 10);// delayMillis: 延迟多少毫秒调用接口

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
            if(page==0)
            {
                bannarDatas.clear();
                newsList.clear();
            }
            Message message = new Message();
            try{
                String jsonData = HttpRequest.Get(url);
                //考虑网络请求出错的情况
                if(jsonData==null)
                    message.what = 0;
                else
                {
                    JSONObject jsonObject=new JSONObject(jsonData);
                    int pagenum=jsonObject.getInt("totalPages");
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        if (i < 3 && page==0)
                            bannarDatas.add(new News(object.getString("title"), object.getString("imgurl"), object.getString("url")));
                        else
                            newsList.add(new News(object.getString("title"), object.getString("imgurl"), object.getString("url"), object.getString("author"), object.getString("releasetime"), object.getInt("nature")));
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
                message.what = 0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }}).start();
    }
}
