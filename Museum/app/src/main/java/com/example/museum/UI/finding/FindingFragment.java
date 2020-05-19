package com.example.museum.UI.finding;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.API.API;
import com.example.museum.Adapter.CollectionAdapter_FindingPage;
import com.example.museum.Adapter.ExhibitionAdapter_FindingPage;
import com.example.museum.Adapter.MuseumsAdapter;

import com.example.museum.CollectionsActivity;
import com.example.museum.Datas.Collection;
import com.example.museum.Datas.Exhibition;
import com.example.museum.Datas.Museum;
import com.example.museum.ExhibitionsActivity;
import com.example.museum.HttpRequest;
import com.example.museum.MuseumsActivity;
import com.example.museum.R;
import com.example.museum.RankActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*
*    发现页面
* */
public class FindingFragment extends Fragment implements View.OnClickListener {

    private ExhibitionAdapter_FindingPage adapter1;
    private CollectionAdapter_FindingPage adapter2;
    private MuseumsAdapter adapter3;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    adapter1.notifyDataSetChanged();
                    break;
                case 2:
                    adapter2.notifyDataSetChanged();
                    break;
                case 3:
                    adapter3.notifyDataSetChanged();
                    break;
            }
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_findingpage, container, false);
        //跳转到搜索博物馆页面
        LinearLayout linearLayout1 = root.findViewById(R.id.findingsearch1);
        linearLayout1.setOnClickListener(this);
        //跳转到搜索展览页面
        LinearLayout linearLayout2 = root.findViewById(R.id.findingsearch2);
        linearLayout2.setOnClickListener(this);
        //跳转到搜索藏品页面
        LinearLayout linearLayout3 = root.findViewById(R.id.findingsearch3);
        linearLayout3.setOnClickListener(this);
        //跳转到看博物馆排名页面
        LinearLayout linearLayout4 = root.findViewById(R.id.findingsearch4);
        linearLayout4.setOnClickListener(this);

        //卡片式布局显示展览列表
        RecyclerView recyclerView1 = (RecyclerView) root.findViewById(R.id.recycler_findingpart1);
        GridLayoutManager layoutManager1 = new GridLayoutManager(root.getContext(), 2);
        recyclerView1.setLayoutManager(layoutManager1);
        List<Exhibition> exhibitsList = new ArrayList<>();
        adapter1= new ExhibitionAdapter_FindingPage(exhibitsList);
        recyclerView1.setAdapter(adapter1);
        //更多展览
        LinearLayout linearLayout5 = root.findViewById(R.id.part_more1);
        linearLayout5.setOnClickListener(this);

        //卡片式布局显示藏品列表
        RecyclerView recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_findingpart2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(root.getContext(), 2);
        recyclerView2.setLayoutManager(layoutManager2);
        List<Collection> collectionsList = new ArrayList<>();
        adapter2= new CollectionAdapter_FindingPage(collectionsList);
        recyclerView2.setAdapter(adapter2);
        //更多藏品
        LinearLayout linearLayout6 = root.findViewById(R.id.part_more2);
        linearLayout6.setOnClickListener(this);

        //卡片式布局显示博物馆列表
        RecyclerView recyclerView3 = (RecyclerView) root.findViewById(R.id.recycler_findingpart3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(root.getContext());
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        List<Museum> museumsList = new ArrayList<>();
        adapter3= new MuseumsAdapter(museumsList);
        recyclerView3.setAdapter(adapter3);
        //获取展览数据
        new Thread(()->{
            try {
                String jsonData = HttpRequest.Get(API.showAllExhibitions+"?page=0");
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for (int i = 0; i < 4; i++) {
                    JSONObject item = Jarray.getJSONObject(i);
//                    museumsList.add(new Museum(object.getInt("mid"),object.getString("imrurl"),object.getString("name"),object.getDouble("avgstar")));
                    exhibitsList.add(new Exhibition(item.getInt("eid"),item.getString("name"),item.getString("imgurl"),item.getString("mname")));
                }
                Message message=new Message();
                message.what = 1;
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
        //获取藏品数据
        new Thread(()->{
            try {
                String jsonData = HttpRequest.Get(API.showAllColllections+"?page=0");
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for (int i = 0; i < 4; i++) {
                    JSONObject object = Jarray.getJSONObject(i);
//                    museumsList.add(new Museum(object.getInt("mid"),object.getString("imrurl"),object.getString("name"),object.getDouble("avgstar")));
                    collectionsList.add(new Collection(object.getInt("cid"),object.getString("name"),object.getString("imgurl"),object.getString("mid")));
                }
                Message message=new Message();
                message.what = 2;
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();

        //获取博物馆数据
        new Thread(()->{
            try {
                String jsonData = HttpRequest.Get(API.showAllMuseums+"?page=0");
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray Jarray = jsonObject.getJSONArray("content");
                for (int i = 0; i < 7; i++) {
                    JSONObject object = Jarray.getJSONObject(i);
//                    museumsList.add(new Museum(object.getInt("mid"),object.getString("imrurl"),object.getString("name"),object.getDouble("avgstar")));
                    museumsList.add(new Museum(object.getInt("mid"),object.getString("imgurl"),object.getString("name"),
                            object.getDouble("avgenvironmentstar"),object.getDouble("avgexhibitionstar"),object.getDouble("avgservicestar"),
                            object.getString("address"),object.getDouble("avgstar"),
                            object.getString("introduction"),object.getString("opentime"),object.getString("mobile")));
                }
                Message message=new Message();
                message.what = 3;
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();

        //更多展览
        LinearLayout linearLayout7 = root.findViewById(R.id.part_more3);
        linearLayout7.setOnClickListener(this);

        return root;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.findingsearch1:
                startActivity(new Intent(getContext(), MuseumsActivity.class));
                break;
            case R.id.findingsearch2:
                startActivity(new Intent(getContext(), ExhibitionsActivity.class));
                break;
            case R.id.findingsearch3:
                startActivity(new Intent(getContext(), CollectionsActivity.class));
                break;
            case R.id.findingsearch4:
                startActivity(new Intent(getContext(), RankActivity.class));
                break;
            case R.id.part_more3:
                startActivity(new Intent(getContext(), MuseumsActivity.class));
                break;
            case R.id.part_more1:
                startActivity(new Intent(getContext(), ExhibitionsActivity.class));
                break;
            case R.id.part_more2:
                startActivity(new Intent(getContext(), CollectionsActivity.class));
                break;
        }
    }
}
