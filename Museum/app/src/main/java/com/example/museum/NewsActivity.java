package com.example.museum;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.just.agentweb.AgentWeb;

public class NewsActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ActionBar actionbar = getSupportActionBar();
        // 隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
        Intent intent=getIntent();
        String URL=intent.getStringExtra("Url");
        LinearLayout view=findViewById(R.id.web_view);

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("https://baijiahao.baidu.com/s?id=1664479836390504132&wfr=spider&for=pc");
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://baijiahao.baidu.com/s?id=1664479836390504132&wfr=spider&for=pc");
    }
}
