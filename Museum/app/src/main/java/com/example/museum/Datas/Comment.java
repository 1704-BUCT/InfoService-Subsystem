package com.example.museum.Datas;


/*
* 评价类的定义
* */
public class Comment {
    Integer uid;
    String uname;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    String avatarurl;
    double exhibitionstar;
    double environmentstar;
    double servicestar;
    double avgstar;
    String content;
    String time;

    public Comment(Integer uid, String uname, String avatarurl, double exhibitionstar, double environmentstar, double servicestar, String content, String time) {
        this.uid = uid;
        this.uname = uname;
        this.avatarurl = avatarurl;
        this.exhibitionstar = exhibitionstar;
        this.environmentstar = environmentstar;
        this.avgstar=(exhibitionstar+environmentstar+servicestar)/3;
        this.servicestar = servicestar;
        this.content = content;
        this.time = time;
    }



//    public Comment(Integer uid, String content, double avgstar, String time) {
//        this.uid = uid;
//        this.content = content;
//        this.avgstar = avgstar;
//        this.time = time;
//    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public double getExhibitionstar() {
        return exhibitionstar;
    }

    public void setExhibitionstar(double exhibitionstar) {
        this.exhibitionstar = exhibitionstar;
    }

    public double getEnvironmentstar() {
        return environmentstar;
    }

    public void setEnvironmentstar(double environmentstar) {
        this.environmentstar = environmentstar;
    }

    public double getServicestar() {
        return servicestar;
    }

    public void setServicestar(double servicestar) {
        this.servicestar = servicestar;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getAvgstar() {
        return avgstar;
    }

    public void setAvgstar(double avgstar) {
        this.avgstar = avgstar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
