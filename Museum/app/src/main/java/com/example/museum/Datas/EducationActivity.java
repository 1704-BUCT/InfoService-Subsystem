package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;

/*
* 教育活动的定义
* */
public class EducationActivity {
    Integer id;
    String imgurl;
    String time;
    String introduction;
    String name;
    String mname;

    public EducationActivity() {
    }

    public EducationActivity(Integer id, String imgurl, String time, String name) {
        this.id = id;
        this.imgurl = imgurl;
        this.time = time;
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
    public static List<EducationActivity > getTestDatas(){
        List<EducationActivity > educationActivities=new ArrayList<>();
        educationActivities.add(new EducationActivity(39,"http://www.jb.mil.cn/cyhd/jzhd/./hdhg/201807/W020180714571053729545.jpg","2018-07-14 15:26:00","传播国旗文化，传承红色基因——全国学校升旗手交流展示活动在军博举办"));
        educationActivities.add(new EducationActivity(38,"http://www.jb.mil.cn/cyhd/jzhd/./hdhg/201906/W020190603630982945654.jpg","2019-06-03 17:22:00","新生力量再注军博志愿者团队"));
        educationActivities.add(new EducationActivity(37,"http://cstm.cdstm.cn//d/file/jyhd/kphdsys/jchg/2015-02-04/f4f64c4669c42b6da0663b14be8c46c3.jpg","2015-02-10","展教活动走出去科教经验引进来"));
        educationActivities.add(new EducationActivity(36,"http://cstm.cdstm.cn//d/file/jyhd/kphdsys/jchg/2015-02-04/8daa9a0fdeb6a9f193ebadbdd768e501.jpg","2015-02-10","动手做拉开帷幕实验室提前练兵——“欢乐动手做”活动在展厅顺利开展"));
        return educationActivities;
    }
}
