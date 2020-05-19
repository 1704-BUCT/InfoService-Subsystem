package com.example.museum.UI.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.museum.API.API;
import com.example.museum.Datas.Users;
import com.example.museum.EditInfo;
import com.example.museum.HttpRequest;
import com.example.museum.MyStars;
import com.example.museum.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;

/*
    我的页面
* */
public class MyFragment extends Fragment {
    private TextView editing_info;
    private ImageView avater;
    private LinearLayout show_account;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
             avater.setImageResource(R.drawable.ic_avatar);
            editing_info.setVisibility(View.VISIBLE);
            show_account.setVisibility(View.VISIBLE);
            }
        }
    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);
        final TextView textView = root.findViewById(R.id.text_myTitle);
        textView.setText("我的");
        editing_info = root.findViewById(R.id.editing_info);
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
       show_account=root.findViewById(R.id.show_account);
        avater=root.findViewById(R.id.avatar_img);
        if(Users.isLogin==0)
        {
            avater.setImageResource(R.drawable.ic_avater_unsignin);
            editing_info.setVisibility(View.INVISIBLE);
            show_account.setVisibility(View.INVISIBLE);
        }
        TextView name = root.findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(()->{
//                    FormBody formBody1 = new FormBody.Builder().build();
//                        String res=HttpRequest.Post(API.signin+"/slm/12223",formBody1);
//                        System.out.println(res);
                    JSONObject Jobject = new JSONObject();
                    try {
                        Jobject.put("account","slm1");
                        Jobject.put("password","12223");
                        Jobject.put("name","slm1");
                        Jobject.put("grander","women");
                        Jobject.put("birthday","1999-01-08");
                        Jobject.put("mobile","1213001122");
                        Jobject.put("email","stusunlimei@163.com");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String json=Jobject.toString();
                    String res=HttpRequest.Post(API.signup,json);
                    Message message = new Message();
                    if(res.equals("sign up successful"))
                    {
                        Users.isLogin=1;
                        message.what = 1;
                        handler.sendMessage(message);    // 将Message对象发送出去
                    }

            }).start();
        }
        });
        return root;
    }
}
