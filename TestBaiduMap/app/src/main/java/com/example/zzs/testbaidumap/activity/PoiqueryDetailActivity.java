package com.example.zzs.testbaidumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.zzs.testbaidumap.R;

public class PoiqueryDetailActivity extends AppCompatActivity {
    private WebView wvPoiqueryDetailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poiquery_detail);
        //初始化视图
        iniyView();
    }

    //初始化视图
    private void iniyView() {
        wvPoiqueryDetailActivity = (WebView) findViewById(R.id.wv_activity_poiqury_detail);
        String url = getIntent().getStringExtra("url");
        wvPoiqueryDetailActivity.loadUrl(url);
    }
}
