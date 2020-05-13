package com.example.museum.UI.finding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.Adapter.CollectionAdapter_FindingPage;
import com.example.museum.Adapter.ExhibitionAdapter_FindingPage;
import com.example.museum.Adapter.MuseumsAdapter;

import com.example.museum.CollectionsActivity;
import com.example.museum.ExhibitionsActivity;
import com.example.museum.MuseumsActivity;
import com.example.museum.R;
import com.example.museum.RankActivity;


/*
*    发现页面
* */
public class FindingFragment extends Fragment implements View.OnClickListener {


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
        ExhibitionAdapter_FindingPage adapter1= new ExhibitionAdapter_FindingPage();
        recyclerView1.setAdapter(adapter1);
        //更多展览
        LinearLayout linearLayout5 = root.findViewById(R.id.part_more1);
        linearLayout5.setOnClickListener(this);

        //卡片式布局显示藏品列表
        RecyclerView recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_findingpart2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(root.getContext(), 2);
        recyclerView2.setLayoutManager(layoutManager2);
        CollectionAdapter_FindingPage adapter2= new CollectionAdapter_FindingPage();
        recyclerView2.setAdapter(adapter2);
        //更多藏品
        LinearLayout linearLayout6 = root.findViewById(R.id.part_more2);
        linearLayout6.setOnClickListener(this);

        //卡片式布局显示博物馆列表
        RecyclerView recyclerView3 = (RecyclerView) root.findViewById(R.id.recycler_findingpart3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(root.getContext());
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        MuseumsAdapter adapter3= new MuseumsAdapter();
        recyclerView3.setAdapter(adapter3);
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
            case R.id.part_more2:
                startActivity(new Intent(getContext(), ExhibitionsActivity.class));
                break;
            case R.id.part_more1:
                startActivity(new Intent(getContext(), CollectionsActivity.class));
                break;
        }
    }
}
