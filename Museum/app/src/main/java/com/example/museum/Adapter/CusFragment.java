package com.example.museum.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.API.API;
import com.example.museum.Datas.Collection;
import com.example.museum.Datas.EducationActivity;
import com.example.museum.Datas.Exhibition;
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
    设置博物馆详细信息页面的viewpager内fragment的内容
*/
public class CusFragment extends Fragment {
    //传递tab的title
    public static final String ARGUMENT_KEY = "tab_title";
    public static final String ARGUMENT_KEY2 = "mname";
    private LinearLayout label;
    private Button buttonAll;
    private Button buttonPos;
    private Button buttonNeg;

    private ProgressBar progressBar;
    private TextView textView;
    private LinearLayoutManager layoutManager;

    private ExhibitionAdapter adapter1;
    private CollectionAdapter adapter2;
    private EducationsAdapter adapter3;
    private NewsAdapter adapter4;
    private Integer pagenum_exhibit=0;
    private Integer pagenum_collection=0;
    private Integer pagenum_educaition=0;
    private Integer pagenum_news=0;
    private Integer pagenum_comment=0;
    private  List<Exhibition> exhibitionsList = new ArrayList<>();
    private  List<Collection> collectionsList = new ArrayList<>();
    private  List<News> newsList = new ArrayList<>();
    private  List<EducationActivity> educationActivities = new ArrayList<>();
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setVisibility(View.VISIBLE);
            switch (msg.what)
            {
                case 1:
                    adapter1.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
//                    progressBar1.setVisibility(View.GONE);
                    if(exhibitionsList.isEmpty())
                        textView.setText("此处暂时没有信息");
                    else
                        textView.setText("已到达底部~");
                    break;
                case 2:
                    adapter2.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
//                    progressBar2.setVisibility(View.GONE);
                    if(collectionsList.isEmpty())
                        textView.setText("此处暂时没有信息");
                    else
                        textView.setText("已到达底部~");
                    break;
                case 3:
                    adapter3.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    if(educationActivities.isEmpty())
                        textView.setText("此处暂时没有信息");
                    else
                        textView.setText("已到达底部~");
                    break;
                case 4:
                    label.setVisibility(View.VISIBLE);
                    adapter4.notifyDataSetChanged();
                    if(newsList.isEmpty())
                        textView.setText("此处暂时没有信息");
                    else
                        textView.setText("已到达底部~");
                    progressBar.setVisibility(View.GONE);
                    break;
                case 5:
                    break;
                case 0:
                    textView.setText("数据加载失败，请检查网络");
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
        label=view.findViewById(R.id.label);
        buttonAll=view.findViewById(R.id.button_all);
        buttonPos=view.findViewById(R.id.button_pos);
        buttonNeg=view.findViewById(R.id.button_neg);

        //提示是否有信息
        textView =view.findViewById(R.id.item_tab1);
        RecyclerView  recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
        progressBar=view.findViewById(R.id.progressBarinMuseum);
        //获取博物馆名字
        String mname=bundle.getString(ARGUMENT_KEY2);
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAll.setBackground(getResources().getDrawable(R.drawable.button_select));
                buttonAll.setHintTextColor(getResources().getColor(R.color.select));
                buttonPos.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonPos.setHintTextColor(getResources().getColor(R.color.select_color));
                buttonNeg.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonNeg.setHintTextColor(getResources().getColor(R.color.select_color));
                progressBar.setVisibility(View.VISIBLE);
                getNews(API.museumNews+mname,0);
            }
        });
        buttonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAll.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonAll.setHintTextColor(getResources().getColor(R.color.select_color));
                buttonPos.setBackground(getResources().getDrawable(R.drawable.button_select));
                buttonPos.setHintTextColor(getResources().getColor(R.color.select));
                buttonNeg.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonNeg.setHintTextColor(getResources().getColor(R.color.select_color));
                progressBar.setVisibility(View.VISIBLE);
                getNews(API.museumNews+mname,1);
            }
        });
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAll.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonPos.setHintTextColor(getResources().getColor(R.color.select_color));
                buttonPos.setBackground(getResources().getDrawable(R.drawable.button_unselect));
                buttonPos.setHintTextColor(getResources().getColor(R.color.select_color));
                buttonNeg.setBackground(getResources().getDrawable(R.drawable.button_select));
                buttonNeg.setHintTextColor(getResources().getColor(R.color.select));
                progressBar.setVisibility(View.VISIBLE);
                getNews(API.museumNews+mname,2);
            }
        });
        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (bundle.getString(ARGUMENT_KEY).equals("展览"))
        {
            adapter1 = new ExhibitionAdapter(exhibitionsList);
            recyclerView.setAdapter(adapter1);
            getExhibitions(API.museumExhibitions+mname+"?page="+pagenum_exhibit.toString());
        }
        if(bundle.getString(ARGUMENT_KEY).equals("藏品"))
        {
            adapter2 = new CollectionAdapter(collectionsList);
            recyclerView.setAdapter(adapter2) ;
            getCollections(API.museumCollections+mname+"?page="+pagenum_collection.toString());
        }
        if(bundle.getString(ARGUMENT_KEY).equals("教育活动"))
        {
            adapter3 = new EducationsAdapter(educationActivities);
            recyclerView.setAdapter(adapter3);
            getEducation(API.museumEducations+mname+"?page="+pagenum_educaition.toString());
        }
        if(bundle.getString(ARGUMENT_KEY).equals("新闻"))
        {
            adapter4 = new NewsAdapter(newsList);
            recyclerView.setAdapter(adapter4);
            getNews(API.museumNews+mname+"?page="+pagenum_news.toString(),0);
        }
        return view;
    }

    /**
     * 弄一个静态工厂的方法调用 用于传参
     * @param key
     * @return
     */
    public static CusFragment newInStance(String key,String mname) {
        CusFragment fragment = new CusFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY,key);
        //传递过来博物馆的参数
        bundle.putString(ARGUMENT_KEY2,mname);
        fragment.setArguments(bundle);
        return fragment;
    }

    //从服务器上获取展览数据
    private void getExhibitions(String url) {
        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    exhibitionsList.add(new Exhibition(object.getInt("eid"),object.getString("name"),object.getString("imgurl"),object.getString("mname")));
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message); // 将Message对象发送出去


            }catch(JSONException e){
                e.printStackTrace();
            }}).start();
    }
    //从服务器上获取藏品数据
    private void getCollections(String url) {
        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    collectionsList.add(new Collection(object.getInt("cid"),object.getString("name"),object.getString("imgurl"),object.getString("mid")));
                }
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();
    }
    // 从服务器上获取教育活动数据
    private void getEducation(String url)
    {

        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
//                    newsList.add(new News(object.getString("title"), object.getString("imgurl"),object.getString("url"),object.getString("author"), object.getString("releasetime"),object.getInt("nature")));
                    educationActivities.add(new EducationActivity(object.getInt("eid"),object.getString("imgurl"),object.getString("time"),object.getString("name")));
                }
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
    // 从服务器上获取新闻数据,f标记是要全部新闻还是部分（0全部，1正面，2负面）
    private void getNews(String url,int f)
    {

        new Thread(()->{
            newsList.clear();
            try{
                String jsonData = HttpRequest.Get(url);
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    int nature=object.getInt("nature");
                    if(f==1)
                    {
                        if(nature==0)
                            continue;
                    }
                    if(f==2)
                    {
                            if(nature!=0)
                                continue;
                    }
                    newsList.add(new News(object.getString("title"), object.getString("imgurl"),object.getString("url"),object.getString("author"), object.getString("releasetime"),object.getInt("nature")));
                }
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
}
