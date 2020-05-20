package com.example.museum;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
* 网络请求的一些函数
* */
public class HttpRequest {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static OkHttpClient client = new OkHttpClient();
    public static String Get(String url)  {
        Request request = new Request.Builder()
                .addHeader("Connection", "close")
                .url(url)
                .build();
        Call call = client.newCall(request);
        String str = null;
        try{
            Response response = null;
            response = call.execute();
            int code=response.code();
            if(code == 200){
                str = response.body().string();
            }
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

    public static String Post(String url,String json)
    {
        RequestBody body=RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
}
