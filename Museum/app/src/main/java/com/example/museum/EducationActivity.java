package com.example.museum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.museum.API.API;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
* 教育活动页面
* */
public class EducationActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView educationName;
    private TextView educationTime;
    private TextView educationLocation;
    private TextView educationMuseum;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    com.example.museum.Datas.EducationActivity educationActivity;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                educationMuseum.setText(educationActivity.getMname());
                educationName.setText(educationActivity.getName());
                educationTime.setText(educationActivity.getTime());
                collapsingToolbarLayout.setTitle(educationActivity.getName());
                collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark1));
                Glide.with(getBaseContext()).load(educationActivity.getImgurl()).into(imageView);
                TextView educationIntroduction = (TextView) findViewById(R.id.expandable_text_education);
                educationIntroduction.setText(educationActivity.getIntroduction());

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        imageView = (ImageView)findViewById(R.id.Appbar_imageview_education);
        educationName = (TextView)findViewById(R.id.education_name);
        educationTime = (TextView)findViewById(R.id.education_time);
        educationMuseum=(TextView)findViewById(R.id.education_museum);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_bar_education);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_education);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        // 给ToolBar上的返回键添加销毁活动的动作
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        Intent intent=getIntent();
        Integer id=intent.getIntExtra("id",0);
        new Thread(()->{
            String str=HttpRequest.Get(API.findEducationsById+id.toString());
            try {
                JSONArray jsonArray = new JSONArray(str);
                educationActivity=new com.example.museum.Datas.EducationActivity();
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    educationActivity.setId(jsonObject.getInt("eid"));
                    educationActivity.setName(jsonObject.getString("name"));
                    educationActivity.setImgurl(jsonObject.getString("imgurl"));
                    educationActivity.setTime(jsonObject.getString("time"));
                    educationActivity.setMname(jsonObject.getString("mname"));
                    educationActivity.setIntroduction(jsonObject.getString("introduction"));
                }
                Message message= new Message();
                message.what=1;
                handler.sendMessage(message) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();


//        educationName.setText("传播国旗文化，传承红色基因——全国学校升旗手交流展示活动在军博举办");
//        educationTime.setText("2018-07-14 15:26:00");
//        educationLocation.setText("北京市东城区景山前街4号");
//        collapsingToolbarLayout.setTitle("传播国旗文化，传承红色基因——全国学校升旗手交流展示活动在军博举办");
//        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark1));
//        Glide.with(this).load("http://www.jb.mil.cn/cyhd/jzhd/./hdhg/201807/W020180714571053729545.jpg").into(imageView);
//        TextView educationIntroduction = (TextView) findViewById(R.id.expandable_text_education);
//        educationIntroduction.setText("　　7月11日下午，在宽敞明亮的中国人民革命军事博物馆展览大厅内，开展了一场别开生面、庄严神圣的以“青春旗帜——向祖国致敬”为主题的全国学校升旗手交流展示活动。来自广东的200名少年升旗手在鲜艳的国旗前，神情肃穆，目光坚定，充满了对国旗的敬意。　　活动开始前，第一次来到军事博物馆的少年升旗手们，在解说员的带领下兴致勃勃地参观了兵器陈列。在功勋喷气式歼击机、99式高级教练机、“功臣号”坦克、功勋鱼雷快艇等一件件富有传奇色彩的功勋武器前，小旗手们聚精会神地聆听着解说员的讲解，深切感悟着中国革命胜利的来之不易。　　　　升旗手们参观兵器陈列 　　参观结束后，身着统一服装的升旗手们在军事博物馆北迎宾大厅整齐列队。在灯光的映照下，显得格外的阳光、朝气。4名训练有素的小升旗手按照升国旗的动作要领，在队列前一一展示升国旗的标准仪式，并将国旗高举在队伍面前，200名升旗手注视着国旗，齐声高唱国歌，嘹亮的歌声回荡在大厅中。　　来自广州怡园小学的叶之慧，代表全体升旗手们说：“我们一定要从小做起，时刻关心祖国和民族的命运；少年智则国智，少年强则国强，要用我们的实际行动传播国旗文化，传承国旗精神，诠释对祖国和对人民的热爱，让鲜红的国旗永远飘扬在我们的心中。”　　最后，专程出席活动的中共井冈山红军人物研究会常务会长、朱德总司令外孙刘建将军深情地寄语少年升旗手们，一定要努力学习，牢记历史，领悟革命精神和光荣传统，要通过今天的活动，感受到国旗精神和国旗力量，传承国旗文化，激发起爱国热情，要把爱国旗、敬国旗转化为我们的自觉行动，为鲜艳的五星红旗增光添彩。今年暑假期间，全国学校升旗手交流展示活动将连续举办4次，有近千名学生参加。天安门广场国旗班原班长、全国学校升旗手总教官赵新风，军事博物馆展陈管理教育部副部长缪炳法，展陈管理教育部宣传教育室主任、解说队队长林燕燕等出席该活动。      刘建将军深情寄语活动合影");
    }
}
