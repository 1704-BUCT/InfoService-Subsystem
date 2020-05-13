package com.example.museum.UI.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.museum.EditInfo;
import com.example.museum.MyStars;
import com.example.museum.R;

/*
    我的页面
* */
public class MyFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);
        final TextView textView = root.findViewById(R.id.text_myTitle);
        textView.setText("我的");
        TextView editing_info = root.findViewById(R.id.editing_info);
        editing_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditInfo.class);
                startActivity(intent);
            }
        });
        LinearLayout mycollections=(LinearLayout)root.findViewById(R.id.my_collections);
        mycollections.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyStars.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
