package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;

/*
* 藏品类的定义
* */
public class Collection {
    Integer cid;
    String name;
    String imgurl;
    String mname;
    String introudce;


    public Collection(Integer cid, String name, String imgurl, String mname) {
        this.cid = cid;
        this.name = name;
        this.imgurl = imgurl;
        this.mname = mname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }


    public String getIntroudce() {
        return introudce;
    }

    public void setIntroudce(String introudce) {
        this.introudce = introudce;
    }

    public static List<Collection> getTestDatas()
    {
        List<Collection> collections=new ArrayList<>();
        collections.add(new Collection(1,"华东野战军为围歼杜聿明集团发布的作战命令","http://www.jb.mil.cn/gcww/wwjs_new/jfzzsq/201707/./W020170704569682950590.jpg","中国人民革命军事博物馆"));
        collections.add(new Collection(2,"陈毅、粟裕给杜聿明、邱清泉、李弥的劝降信底稿","http://www.jb.mil.cn/gcww/wwjs_new/jfzzsq/201707/./W020170704569685705957.jpg","中国人民革命军事博物馆"));
        collections.add(new Collection(3,"粟裕在淮海战役指挥作战时使用的电话机","http://www.jb.mil.cn/gcww/wwjs_new/jfzzsq/201707/./W020170704569688553201.jpg","中国人民革命军事博物馆"));
        collections.add(new Collection(4,"淮海战役中的支前小车","http://www.jb.mil.cn/gcww/wwjs_new/jfzzsq/201707/./W020170704569686688129.jpg","中国人民革命军事博物馆"));
        return collections;
    }
}
