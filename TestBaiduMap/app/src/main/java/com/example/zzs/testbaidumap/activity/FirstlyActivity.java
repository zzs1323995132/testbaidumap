package com.example.zzs.testbaidumap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zzs.testbaidumap.MainActivity;
import com.example.zzs.testbaidumap.R;

public class FirstlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstly);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_activity_welcome_init://初始化配置
                intent.setClass(this, MainActivity.class);
                break;
            case R.id.btn_activity_welcome_overlay://覆盖物
                intent.setClass(this, OverlayActivity.class);
                break;
            case R.id.btn_activity_welcome_route://路线
                intent.setClass(this, RouteActivity.class);
                break;
            case R.id.btn_activity_welcome_poiqury://Poi搜索
                intent.setClass(this, PoiqueryActivity.class);
                break;
            case R.id.btn_activity_welcome_busline://公交路线
                intent.setClass(this, BuselineActivity.class);
                break;
            case R.id.btn_activity_welcome_location://定位
                intent.setClass(this, LocationActivity.class);
                break;
            case R.id.btn_activity_welcome_routemap://路线查询
                intent.setClass(this, RoutemapActivity.class);
                break;
        }
        startActivity(intent);
    }
}
