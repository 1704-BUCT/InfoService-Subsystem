package com.example.museum.Datas;


/*
* 评价类的定义
* */
public class Comment {
    Integer uid;
    String content;
    double avgstar;
    String time;

    public Comment(Integer uid, String content, double avgstar, String time) {
        this.uid = uid;
        this.content = content;
        this.avgstar = avgstar;
        this.time = time;
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
