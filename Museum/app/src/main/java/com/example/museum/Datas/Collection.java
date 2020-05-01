package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    String cid;
    String name;
    String imgurl;
    String mname;
    String maddress;
    String introudce;
    String explainations;

    public Collection(String cid, String name, String imgurl, String mname) {
        this.cid = cid;
        this.name = name;
        this.imgurl = imgurl;
        this.mname = mname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
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

    public String getMaddress() {
        return maddress;
    }

    public void setMaddress(String maddress) {
        this.maddress = maddress;
    }

    public String getIntroudce() {
        return introudce;
    }

    public void setIntroudce(String introudce) {
        this.introudce = introudce;
    }

    public String getExplainations() {
        return explainations;
    }

    public void setExplainations(String explainations) {
        this.explainations = explainations;
    }
    public static List<Collection> getTestDatas()
    {
        List<Collection> collections=new ArrayList<>();
        collections.add(new Collection("1","清乾隆胭脂红彩缠枝螭龙纹瓶","https://www.shanghaimuseum.net/resource/museum_files/resource_files/20160524/1464058444777_162.jpg","上海博物馆"));
        collections.add(new Collection("2","点翠花蝶纹头花","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588150169294&di=14da76ed6163fe844bc2bba1b37af5c3&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_70%2Cc_zoom%2Cw_640%2Fimages%2F20171030%2F1010839bed114777aede4cdd07537d66.jpeg","故宫博物馆"));
        collections.add(new Collection("3","北宋景德镇窑青白釉注碗","https://www.shanghaimuseum.net/resource/museum_files/resource_files/20160524/1464058399786_162.jpg","上海博物馆"));
        collections.add(new Collection("4","碧玉竹林七贤图笔筒","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1588150236620&di=aa6ead76e8ae13bb2583e7aa244d6650&imgtype=0&src=http%3A%2F%2Fwww.kfzimg.com%2FS01%2F20171011%2F5529263%2F4njsVrBRpW_b.jpg","故宫博物馆"));
        return collections;
    }
}
