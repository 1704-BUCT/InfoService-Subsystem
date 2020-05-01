package com.example.museum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.just.agentweb.AgentWeb;

/*
* 新闻具体内容页面，此处利用了Agentweb库解决了webview在Api29以上网页存在加载不出来的问题
* */
public class NewsActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // 隐藏标题栏
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null) {
//            actionbar.hide();
//        }
        Intent intent=getIntent();
        String URL=intent.getStringExtra("Url");
        LinearLayout view=findViewById(R.id.web_view);
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(URL);
    }
}
