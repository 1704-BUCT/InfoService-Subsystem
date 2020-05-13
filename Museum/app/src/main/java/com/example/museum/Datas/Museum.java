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
    double avgenvironmentstar;
    double avgexhibitionstar;
    double avgservicestar;
    double avgstar;
    String introduction;
    String opentime;
    String mobile;


    public Museum(Integer mid, String imgurl, String name, double star) {
        this.mid = mid;
        this.imgurl = imgurl;
        this.name = name;
        this.avgstar = star;
    }

    public double getAvgenvironmentstar() {
        return avgenvironmentstar;
    }

    public void setAvgenvironmentstar(double avgenvironmentstar) {
        this.avgenvironmentstar = avgenvironmentstar;
    }

    public double getAvgexhibitionstar() {
        return avgexhibitionstar;
    }

    public void setAvgexhibitionstar(double avgexhibitionstar) {
        this.avgexhibitionstar = avgexhibitionstar;
    }

    public double getAvgservicestar() {
        return avgservicestar;
    }

    public void setAvgservicestar(double avgservicestar) {
        this.avgservicestar = avgservicestar;
    }

    public double getAvgstar() {
        return avgstar;
    }

    public void setAvgstar(double avgstar) {
        this.avgstar = avgstar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        list.add(new Museum(1,"http://pic.baike.soso.com/ugc/baikepic2/23180/cut-20190730175708-2017983333_jpg_287_229_9999.jpg/300", "中国科学技术馆",3.5));
        list.add(new Museum(2,"http://pic.baike.soso.com/ugc/baikepic2/16106/20160922102904-253271503.jpg/300", "中国地质博物馆",4));
        list.add(new Museum(3,"http://pic.baike.soso.com/ugc/baikepic2/29545/cut-20190522135955-1265681517_jpg_823_659_106346.jpg/300", "中国人民革命军事博物馆",3.5));
        list.add(new Museum(4,"http://pic.baike.soso.com/ugc/baikepic2/11293/ori-20160723150840-1187288764.jpg/300", "北京鲁迅博物馆",4));
        list.add(new Museum(5,"http://pic.baike.soso.com/ugc/baikepic2/28926/20170823232346-1570228866_jpg_309_232_87080.jpg/0", "首都博物馆",5));
        list.add(new Museum(6,"http://ugc.qpic.cn/baikepic2/4253/20141024163041-877835016.jpg/0", "北京自然博物馆",4));
        list.add(new Museum(7,"http://pic.baike.soso.com/ugc/baikepic2/15710/cut-20150601142004-1848381712.jpg/0", "周口店猿人遗址博物馆",3));

        return list;

    }
}
