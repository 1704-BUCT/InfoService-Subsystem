package com.example.museum.API;

import android.widget.BaseAdapter;

/*
* 后台定义的接口
* */
public class API {
    private final static String BASE_URL = "http://39.97.241.101:8080/api/";
//    private final static String BASE_URL = "http://192.168.56.1:8080/";
//    private final static String BASE_URL = "http://192.168.56.1:8080/";

    public final static  String showAllMuseums=BASE_URL+"museum/findAll";
    public final static String showMuseumSortByAvgstar=BASE_URL+"/museum/findAll/sortByAvgstar";
    public final static String showMuseumSortByAvgenvironstar=BASE_URL+"/museum/findAll/sortByAvgenvironmentstar";
    public final static String showMuseumSortByAvgexhibitstar=BASE_URL+"/museum/findAll/sortByAvgexhibitionstar";
    public final static String showMuseumSortByAvgservicestar=BASE_URL+"/museum/findAll/sortByAvgservicestar";
    public final static String showMuseumSortByNum= BASE_URL+"/museum/findAll/sortByExhibitionNums";
    public final static String getPartMuseumByName=BASE_URL+"/museum/searchByName/";

    public final static  String showAllEducations=BASE_URL+"education/findAll";
    public final static  String museumEducations=BASE_URL+"education/findByMname/";
    public final static String findEducationsById=BASE_URL+"education/findByEid/";

    public final static  String showAllNews= BASE_URL+"news/findAll";
    public final static  String museumNews= BASE_URL+"news/getByMname/";

    public final static String showAllExhibitions=BASE_URL+"exhibition/findAll";
    public final static String museumExhibitions=BASE_URL+"exhibition/findByMname/";

    public final static String showAllColllections=BASE_URL+"collection/findAll";
    public final static String museumCollections=BASE_URL+"collection/findByMname/";

    public final static String signin=BASE_URL+"user/signin";
    public final static String signup=BASE_URL+"user/signup";
    public final static String uploadComment=BASE_URL+"/comment/upload";
    public final static String isAlreadyComment=BASE_URL+"/comment/already/";
}
