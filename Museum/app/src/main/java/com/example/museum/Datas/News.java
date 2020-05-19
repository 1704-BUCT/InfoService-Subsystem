package com.example.museum.Datas;



import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
* 定义新闻类，存放一个的新闻的相关信息
* */
public class News {
    private Integer id;
    private String title;
    private String imgurl;
    private String url;
    private String author;
    private String releasetime;
    private Integer nature;

    public News(String title, String imgurl, String url, String author, String releasetime, Integer nature) {
        this.title = title;
        this.imgurl = imgurl;
        this.url = url;
        this.author = author;
        this.releasetime = releasetime;
        this.nature = nature;
    }

    public News(Integer id, String title, String imgurl) {
        this.id = id;
        this.title = title;
        this.imgurl = imgurl;
    }
    public News( String title, String imgurl,String url) {
        this.id = 0;
        this.title = title;
        this.imgurl = imgurl;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleasetime() {
        return releasetime.substring(0,10);
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title= name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static List<News> getTestData() {
        List<News> list = new ArrayList<>();
        list.add(new News(1,"博物馆之夜1","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(2,"博物馆之夜2","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(3,"博物馆之夜3","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(4,"博物馆之夜4","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(5,"博物馆之夜5","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(6,"博物馆之夜6","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(7,"博物馆之夜7","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(8,"博物馆之夜8","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(9,"博物馆之夜9","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        list.add(new News(10,"博物馆之夜10","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        return list;
    }

}
