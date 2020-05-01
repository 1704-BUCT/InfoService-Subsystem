package com.example.museum.UI.finding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.museum.Adapter.CollectionAdapter;
import com.example.museum.Adapter.ExhibitionAdapter;
import com.example.museum.Adapter.MuseumsAdapter;
import com.example.museum.Datas.Collection;
import com.example.museum.Datas.Exhibition;
import com.example.museum.Datas.Museum;
import com.example.museum.MuseumsActivity;
import com.example.museum.R;
import com.example.museum.RankActivity;


/*
    发现页面：提供
* */
public class FindingFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_findingpage, container, false);
        //跳转到博物馆列表页面
        LinearLayout linearLayout1 = root.findViewById(R.id.findingsearch1);
        linearLayout1.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), MuseumsActivity.class));
        });
        //跳转到看博物馆排名页面
        LinearLayout linearLayout4 = root.findViewById(R.id.findingsearch4);
        linearLayout4.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RankActivity.class));
        });
        //卡片式布局显示展览列表
        RecyclerView recyclerView1 = (RecyclerView) root.findViewById(R.id.recycler_findingpart1);
        GridLayoutManager layoutManager1 = new GridLayoutManager(root.getContext(), 2);
        recyclerView1.setLayoutManager(layoutManager1);
        ExhibitionAdapter adapter1= new ExhibitionAdapter();
        recyclerView1.setAdapter(adapter1);

        //卡片式布局显示藏品列表
        RecyclerView recyclerView2 = (RecyclerView) root.findViewById(R.id.recycler_findingpart2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(root.getContext(), 2);
        recyclerView2.setLayoutManager(layoutManager2);
        CollectionAdapter adapter2= new CollectionAdapter();
        recyclerView2.setAdapter(adapter2);

        //卡片式布局显示博物馆列表
        RecyclerView recyclerView3 = (RecyclerView) root.findViewById(R.id.recycler_findingpart3);
        GridLayoutManager layoutManager3 = new GridLayoutManager(root.getContext(), 1);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        MuseumsAdapter adapter3= new MuseumsAdapter();
        recyclerView3.setAdapter(adapter3);
        return root;
    }
}
