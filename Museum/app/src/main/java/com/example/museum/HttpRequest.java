package com.example.museum;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
* 网络请求的一些函数
* */
public class HttpRequest {
    public static OkHttpClient client = new OkHttpClient();
    public static String Get(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        String str = null;
        try{
            Response response = null;
            response = call.execute();
            str = response.body().string();
        }catch(IOException e){
            e.printStackTrace();
        }
        return str;
    }
    public static String Post(String url, FormBody formBody)
    {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        String str = null;
        try{
            Response response = null;
            response = call.execute();
            str = response.body().string();
        }catch(IOException e){
            str = "0";
            e.printStackTrace();
        }
        return str;
    }
}
