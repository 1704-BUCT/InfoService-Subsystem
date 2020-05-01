package com.example.museum.Datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* 存放轮播图显示需要的一些数据
* */
public class BannerData {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;
//
//    public DataBean(Integer imageRes, String title, int viewType) {
//        this.imageRes = imageRes;
//        this.title = title;
//        this.viewType = viewType;
//    }

    public BannerData(String imageUrl, String title, int viewType) {

        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<BannerData> getTestData() {
        List<BannerData> list = new ArrayList<>();
        list.add(new BannerData("https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg", "hello1", 1));
        list.add(new BannerData("http://39.98.107.127:8080/resource/news/image/1.jpeg", "hello2", 1));
        list.add(new BannerData("https://img.zcool.cn/community/013c7d5e27a174a80121651816e521.jpg", "hello3", 1));
//        list.add(new DataBean("https://img.zcool.cn/community/01b8ac5e27a173a80120a895be4d85.jpg", "hello4", 1));
//        list.add(new DataBean("https://img.zcool.cn/community/01a85d5e27a174a80120a895111b2c.jpg", "hello5", 1));
//        list.add(new DataBean("https://img.zcool.cn/community/01085d5e27a174a80120a8958791c4.jpg", "hello6", 1));
//        list.add(new DataBean("https://img.zcool.cn/community/01f8735e27a174a8012165188aa959.jpg", "hello7", 1));
        return list;
    }

    public static List<String> getColors(int size) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            list.add(getRandColor());
        }
        return list;
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    public static String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}