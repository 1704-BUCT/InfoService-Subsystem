package com.example.museum.Datas;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Exhibition {
    Integer eid;
    String name;
    String imgurl;
    String mname;
    String maddress;
    String introduce;
    String explainations;
    public Exhibition(Integer eid1,  String name,String imgurl,String mname) {
        this.eid = eid;
        this.name = name;
        this.imgurl = imgurl;
        this.mname = mname;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getExplainations() {
        return explainations;
    }

    public void setExplainations(String explainations) {
        this.explainations = explainations;
    }

    public static List<Exhibition> getTestDatas(){
        List<Exhibition> exhibitions=new ArrayList<>();
        exhibitions.add(new Exhibition(1,"清代军机处史料展","https://img.dpm.org.cn/Uploads/Picture/2020/04/17/s5e997affc5961.jpg","故宫博物馆"));
        exhibitions.add(new Exhibition(2,"储秀宫","http://img5.imgtn.bdimg.com/it/u=187327660,165237829&fm=26&gp=0.jpg","故宫博物馆"));
        exhibitions.add(new Exhibition(3,"美术作品展","http://www.chnmuseum.cn/portals/0/web/zt/20190809yldf/img/title.jpg","中国国家博物馆"));
        exhibitions.add(new Exhibition(4,"河北衡水文化展","http://www.chnmuseum.cn/Portals/0/web/zt/template/2019/20190423wenhuahengshui/images/banner.jpg","中国国家博物馆"));
        return exhibitions;
    }
}
