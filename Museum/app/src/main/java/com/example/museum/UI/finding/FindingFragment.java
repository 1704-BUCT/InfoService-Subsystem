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
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.museum.MuseumActivity;
import com.example.museum.MuseumsActivity;
import com.example.museum.R;
import com.example.museum.RankActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.SpacesItemDecoration;


/*
*    发现页面
* */
public class FindingFragment extends Fragment implements View.OnClickListener {

    private ExhibitionAdapter_FindingPage adapter1;
    private CollectionAdapter_FindingPage adapter2;
    private MuseumsAdapter adapter3;
    private View findingpart1;
    private View findingpart2;
    private View findingpart3;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what)
            {
                case 1:
                    ProgressBar progressBar=getView().findViewById(R.id.finding_process);
                    progressBar.setVisibility(View.GONE);
                    findingpart1.setVisibility(View.VISIBLE);
                    adapter1.notifyDataSetChanged();
                    break;
                case 2:
                    progressBar=getView().findViewById(R.id.finding_process);
                    progressBar.setVisibility(View.GONE);
                    findingpart2.setVisibility(View.VISIBLE);
                    adapter2.notifyDataSetChanged();
                    break;
                case 3:
                    progressBar=getView().findViewById(R.id.finding_process);
                    progressBar.setVisibility(View.GONE);
                    findingpart3.setVisibility(View.VISIBLE);
                    adapter3.notifyDataSetChanged();
                    break;
                case 0:
                    progressBar=getView().findViewById(R.id.finding_process);
                    progressBar.setVisibility(View.GONE);
                    TextView error=getView().findViewById(R.id.finding_error);
                    error.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_findingpage, container, false);
        findingpart1=(View)root.findViewById(R.id.findingpart1);
        findingpart2=(View)root.findViewById(R.id.findingpart2);
        findingpart3=(View)root.findViewById(R.id.findingpart3);
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
        ByRecyclerView recyclerView3 = (ByRecyclerView) root.findViewById(R.id.recycler_findingpart3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(root.getContext());
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        // 选择2：设置颜色、高度、间距等
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(recyclerView3.getContext(), SpacesItemDecoration.HORIZONTAL)
                .setNoShowDivider(0, 1)
                // 颜色，分割线间距，左边距(单位dp)，右边距(单位dp)
                .setParam(R.color.white, 20);

        recyclerView3.addItemDecoration(itemDecoration);
        recyclerView3.setLayoutManager(layoutManager3);

        List<Museum> museumsList = new ArrayList<>();
        adapter3= new MuseumsAdapter(museumsList);
        recyclerView3.setAdapter(adapter3);
        //设置列表项的点击事件
        recyclerView3.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Museum museum = adapter3.getItemData(position);     // 通过adapter获取对应position的数据
                Intent intent = new Intent(getContext(), MuseumActivity.class);
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
        //获取展览数据
        new Thread(()->{
            Message message=new Message();
            try {
                String jsonData = HttpRequest.Get(API.showAllExhibitions+"?page=2");
                if(jsonData==null)
                {
                    message.what=0;
                }
                else
                {
                    JSONObject jsonObject=new JSONObject(jsonData);
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < 4; i++) {
                        JSONObject item = Jarray.getJSONObject(i);
                        exhibitsList.add(new Exhibition(item.getInt("eid"),item.getString("name"),item.getString("imgurl"),item.getString("mname")));
                    }
                    message.what = 1;
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                message.what=0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }

        }).start();
        //获取藏品数据
        new Thread(()->{
            Message message=new Message();
            try {
                String jsonData = HttpRequest.Get(API.showAllColllections+"?page=2");
                if(jsonData==null)
                {
                    message.what=0;
                }
                else
                {
                    JSONObject jsonObject=new JSONObject(jsonData);
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < 4; i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        collectionsList.add(new Collection(object.getInt("cid"),object.getString("name"),object.getString("imgurl"),object.getString("mid")));
                    }
                    message.what = 2;
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                message.what=0;
                handler.sendMessage(message);    // 将Message对象发送出去
                e.printStackTrace();
            }
        }).start();
        //获取博物馆数据
        new Thread(()->{
            Message message=new Message();
            try {
                String jsonData = HttpRequest.Get(API.showAllMuseums+"?page=2");
                if(jsonData==null)
                {
                    message.what=0;
                }
                else
                {
                    JSONObject jsonObject=new JSONObject(jsonData);
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < 7; i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        museumsList.add(new Museum(object.getInt("mid"),object.getString("imgurl"),object.getString("name"),
                                object.getDouble("avgenvironmentstar"),object.getDouble("avgexhibitionstar"),object.getDouble("avgservicestar"),
                                object.getString("address"),object.getDouble("avgstar"),
                                object.getString("introduction"),object.getString("opentime"),object.getString("mobile")));
                    }
                    message.what = 3;
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                message.what = 0;
                handler.sendMessage(message);    // 将Message对象发送出去
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
