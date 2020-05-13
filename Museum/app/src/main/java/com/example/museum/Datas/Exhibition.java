package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;


/*
    展览类的定义
* */
public class Exhibition {
    Integer eid;
    String name;
    String imgurl;
    String mname;
    String introduce;

    public Exhibition(Integer eid,  String name,String imgurl,String mname) {
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


    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }



    public static List<Exhibition> getTestDatas(){
        List<Exhibition> exhibitions=new ArrayList<>();
        exhibitions.add(new Exhibition(1,"红色记忆——馆藏革命军事艺术作品陈列","http://www.jb.mil.cn/zlcl/jbcl/./jsysc/images/P020170712398830470409.jpg","中国人民革命军事博物馆"));
        exhibitions.add(new Exhibition(2,"“人民总理周恩来”展览开幕","http://www.luxunmuseum.com.cn//data/attached/a0b923820dcc509a/image/20191011/15707731117862.jpg","北京鲁迅博物馆"));
        exhibitions.add(new Exhibition(3,"新中国国防和军队建设陈列（筹建中）","http://www.jb.mil.cn/zlcl/jbcl/./gfhjdjscl/images/P020170707464016725983.jpg","中国人民革命军事博物馆"));
        exhibitions.add(new Exhibition(4,"兵器陈列","http://www.jb.mil.cn/zlcl/jbcl/./bqcl/images/P020170712372640472802.jpg","中国人民革命军事博物馆"));
        return exhibitions;
    }
}
