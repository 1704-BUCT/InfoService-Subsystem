package com.example.museum.Datas;

/*
api尚未出来时做测试用
* */
public class TrafficRule {
    String id;
    String question;
    String answer;
    String explains;
    String url;

    public TrafficRule(String id, String question, String answer, String explains, String url) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.explains = explains;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
