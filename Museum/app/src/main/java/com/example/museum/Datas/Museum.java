package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;

/*
* 定义博物馆类，存放一个的博物馆的相关信息
* */
public class Museum {
    Integer mid;
    String imgurl;
    String name;
    String star;

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public Museum(Integer mid, String imgurl, String name, String star) {
        this.mid = mid;
        this.imgurl = imgurl;
        this.name = name;
        this.star = star;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static List<Museum> getTestData() {
        List<Museum> list = new ArrayList<>();
        list.add(new Museum(1,"https://img.dpm.org.cn/Uploads/Picture/2017/03/20/s58cf5042cd1fd.jpg", "故宫博物院","5"));
        list.add(new Museum(2,"http://www.portmuseum.cn/images/pic_06.jpg", "中国港口博物馆","4"));
        list.add(new Museum(3,"http://www.chnmuseum.cn/wc/wclb/201902/W020190322577718865604.jpg", "中国国家博物馆","4"));
        list.add(new Museum(4,"http://mus.cug.edu.cn/__local/2/3F/6F/055ADFFBD7A07B91214B04CD1E0_B2D66812_9558.jpeg", "中国地质大学逸夫博物馆","4"));
        list.add(new Museum(5,"http://www.capitalmuseum.org.cn/zjsb/classicon/jpg/site2/20110104/1294109304921.jpg", "首都博物馆","4"));
        list.add(new Museum(6,"https://www.shanghaimuseum.net/museum/show-resource.action?id=285", "上海博物馆","4"));
        list.add(new Museum(7,"http://www.hzmuseum.com/File/Image/20130415093652231.jpg", "杭州博物馆","4"));

        return list;

    }
}
