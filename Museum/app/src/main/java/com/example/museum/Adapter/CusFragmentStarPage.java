package com.example.museum.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.API.API;
import com.example.museum.Datas.Collection;
import com.example.museum.Datas.Exhibition;
import com.example.museum.Datas.Users;
import com.example.museum.HttpRequest;
import com.example.museum.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


/*
    设置我的收藏页面的viewpager内fragment的内容
* */
public class CusFragmentStarPage extends Fragment {

    public static final String ARGUMENT_KEY = "tab_title";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private ExhibitionAdapter adapter1;
    private CollectionAdapter adapter2;
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
                    recyclerView.setAdapter(adapter3) ; //收藏的藏品的适配器
                    break;
                case 0:
                    Toast.makeText(getContext(), "部分数据加载失败，请检查网络情况", LENGTH_SHORT).show();
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
            getExhibitions(API.starExhibition+ Users.uid.toString());
        }
        if(bundle.getString(ARGUMENT_KEY).equals("藏品"))
        {
            recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_inmuseum);
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
            progressBar=view.findViewById(R.id.progressBarinMuseum);
            getCollections(API.starCollection+ Users.uid.toString());
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
    private void getExhibitions(String url)
    {
        List<Exhibition> exhibitionList = new ArrayList<>();
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
//                        exhibitionList.add(new Exhibition(object.getString("id"), object.getString("question"), object.getString("answer"), object.getString("explains"), object.getString("url")));
                    }
                    adapter1 = new ExhibitionAdapter(exhibitionList);
                    message.what = 1;
                }
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
    // 从服务器上获取测试数据
    private void getCollections(String url)
    {
        List<Collection> collectionList = new ArrayList<>();
        new Thread(()->{
            try{
                String jsonData = HttpRequest.Get(url);
//                JSONObject Jobject = new JSONObject(jsonData);
//                JSONArray Jarray = Jobject.getJSONArray("newslist");
                JSONArray Jarray = new JSONArray(jsonData);
                for(int i=0;i<Jarray.length();i++) {
                    JSONObject object = Jarray.getJSONObject(i);
//                    collectionList.add(new Collection(object.getString("title"), object.getString("imgurl"),object.getString("url"),object.getString("author"), object.getString("releasetime"),object.getInt("nature")));
                }
                adapter2 = new CollectionAdapter(collectionList);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message); // 将Message对象发送出去
            }catch(JSONException e){
                e.printStackTrace();
            }}).start();

    }
}
